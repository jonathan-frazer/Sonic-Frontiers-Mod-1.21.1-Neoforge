package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.events.BaseformActivate;
import net.sonicrushxii.beyondthehorizon.baseform.events.BaseformDeactivate;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;

import java.util.Objects;

public class ServerPlayerEquipmentHandler {
    public static void onEquipmentChange(Player pPlayer, LivingEquipmentChangeEvent event)
    {
        //Extract Server Player
        if(!(pPlayer instanceof ServerPlayer player)) return;

        //Extract Player Sonic Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);

        //Check Sonic Head
        if(event.getSlot() == EquipmentSlot.HEAD)
        {

            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);

            //Transform
            try {
                if (headItem.getItem() == Items.PLAYER_HEAD) {

                    //Custom Data
                    CompoundTag nbt = Objects.requireNonNull(headItem.get(DataComponents.CUSTOM_DATA)).copyTag();

                    if(!nbt.contains("BeyondTheHorizon")) throw new NullPointerException();
                    if (nbt.getByte("BeyondTheHorizon") == (byte) 2 && !playerSonicData.isSonic) {
                        System.out.println("Transform");
                        BaseformActivate.onBaseformActivate(player);
                    }
                }
            }catch(NullPointerException ignored){}


            //Un-Transform
            try {
                CompoundTag nbt = Objects.requireNonNull(headItem.get(DataComponents.CUSTOM_DATA)).copyTag();
                if (nbt.getByte("BeyondTheHorizon") != (byte) 2) throw new NullPointerException();
            }
            catch(NullPointerException e) {
                if(playerSonicData.isSonic) {
                    System.out.println("Un-Transform");
                    BaseformDeactivate.onBaseformDeactivate(player);
                }
            }
        }
    }
}
