package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class FallDamageHandler
{
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if(!(event.getEntity() instanceof ServerPlayer player)) return;

        switch(player.getData(SONIC_DATA).properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
        {
            case "baseform", "starfallform", "superform", "hyperform" -> event.setDistance(0.0f);
            default -> {}
        }
    }
}
