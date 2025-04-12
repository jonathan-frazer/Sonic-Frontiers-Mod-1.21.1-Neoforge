package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformItemData;
import net.sonicrushxii.beyondthehorizon.event_handlers.CraftHandlers;

import java.util.Objects;

public class ServerWorldHandler
{
    private static int tickCounter = 0;
    private static final int TICKS_PER_SECOND = 20;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event)
    {
        // Get the server instance
        MinecraftServer server = event.getServer(); // Get the level (world) you want to execute the command in

        //Every Second
        if(++tickCounter >= TICKS_PER_SECOND) {
            onServerSecond(server);
            tickCounter=0;
        }
    }

    public static void onServerSecond(MinecraftServer server)
    {
        //For All Levels
        for(ServerLevel level : server.getAllLevels())
        {
            assert !level.isClientSide;

            //Floor Crafting - Sonic Head
            {
                //Use the 15 Blue ice as the base
                for(ItemEntity blueIceEntity : level.getEntities(EntityTypeTest.forClass(ItemEntity.class), item -> {
                    ItemStack itemStack = item.getItem();
                    return itemStack.getItem() == Items.BLUE_ICE && itemStack.getCount() == CraftHandlers.BLUE_ICE_COUNT;
                }))
                {
                    //If Item is crafted then consume the blue ice
                    if(CraftHandlers.tryCraftSonicHead(level,blueIceEntity.getPosition(0)))
                    {
                        //Spawn Head
                        blueIceEntity.setItem(BaseformItemData.baseformSonicHead);
                        blueIceEntity.setDeltaMovement(0,0.1,0);

                        //Spawn Fireworks
                        {
                            //Commands
                            String command = String.format("summon firework_rocket %.2f %.2f %.2f {Life:0,LifeTime:0,FireworksItem:{id:\"minecraft:firework_rocket\",count:1,components:{\"minecraft:fireworks\":{explosions:[{shape:\"burst\",has_twinkle:true,colors:[I;65501,16711918,1966335],fade_colors:[I;1966335,65501]}]}}}}",blueIceEntity.getX(),blueIceEntity.getY(),blueIceEntity.getZ());
                            CommandSourceStack commandSourceStack = server.createCommandSourceStack().withPermission(4).withSuppressedOutput();
                            server.
                                    getCommands().
                                    performPrefixedCommand(commandSourceStack,command);
                        }

                        //Playsound
                        level.playSound(null,blueIceEntity.getX(),blueIceEntity.getY(),blueIceEntity.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.MASTER, 1.0f, 1.0f);
                    }
                }
            }

            //Item cleanup
            for(ItemEntity taggedItemEntity : level.getEntities(EntityTypeTest.forClass(ItemEntity.class), item -> {
                try {
                    ItemStack itemStack = item.getItem();
                    return Objects.requireNonNull(itemStack.get(DataComponents.CUSTOM_DATA)).copyTag().getByte("BeyondTheHorizon") == (byte)1;
                }
                catch (NullPointerException ignore)
                {
                    return false;
                }
            }))
            {
                taggedItemEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
