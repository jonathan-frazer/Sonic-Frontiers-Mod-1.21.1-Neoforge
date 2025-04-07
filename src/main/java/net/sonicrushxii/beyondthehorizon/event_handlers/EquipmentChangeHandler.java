package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerPlayerEquipmentHandler;

public class EquipmentChangeHandler
{
    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event)
    {
        //Player
        if(event.getEntity() instanceof Player player)
        {
            if(player.level().isClientSide()) return;
            ServerPlayerEquipmentHandler.onEquipmentChange(player, event);
        }
    }
}
