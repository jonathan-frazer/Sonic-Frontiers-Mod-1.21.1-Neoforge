package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.baseform.events.server.BaseformActivate;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class LoginHandler
{
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(!(event.getEntity() instanceof ServerPlayer player))
            return;

        //ReActivate Sonic Form on relogin
        if(player.getData(SONIC_DATA).isSonic())
        {
            switch(player.getData(SONIC_DATA).properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1)) {
                case "baseform": BaseformActivate.onBaseformActivate(player);
                                 break;
            }
        }
    }
}
