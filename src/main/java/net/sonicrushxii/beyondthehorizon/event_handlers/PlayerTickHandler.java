package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientPlayerTickHandler;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerPlayerTickHandler;

public class PlayerTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();
        //Next
        if(player.level().isClientSide && FMLEnvironment.dist == Dist.CLIENT)
            ClientPlayerTickHandler.handleTick(player);
        else
            ServerPlayerTickHandler.handleTick(player);
    }
}
