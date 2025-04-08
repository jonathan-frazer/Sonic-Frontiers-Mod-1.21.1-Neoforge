package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class PlayerEventHandler {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal().hasData(SONIC_DATA))
        {
            //If Keep Inventory False, (default) Delete the data if the player is killed
            if(!event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
               PlayerSonicData playerSonicData = event.getEntity().getData(SONIC_DATA);
               playerSonicData = new PlayerSonicData();
            }
            //If Keep inventory True, Copy Data directly
            else {
                event.getEntity().getData(SONIC_DATA).isSonic = event.getOriginal().getData(SONIC_DATA).isSonic;
                event.getEntity().getData(SONIC_DATA).properties = event.getOriginal().getData(SONIC_DATA).properties;
            }
        }
    }
}
