package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server.BaseformServerTick;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class ServerPlayerTickHandler
{
    public static int tickCounter = 0;
    private static final int TICKS_PER_SECOND = 20;

    public static void handleTick(Player pPlayer)
    {
        //Extract Server Player
        if(!(pPlayer instanceof ServerPlayer player)) return;
        //If Player is Dead then remove
        if (!player.isAlive())
            return;

        //Map to the other forms Handler's
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        switch(playerSonicData.properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
        {
            case "baseform":BaseformServerTick.handleTick(player);
        }

        //Every Second
        if (++tickCounter >= TICKS_PER_SECOND) {
            tickCounter = 0;
            handleSecond(player);
        }
    }

    private static void handleSecond(ServerPlayer player)
    {
        //Map to the other forms Handler's
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        switch(playerSonicData.properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
        {
            case "baseform":BaseformServerTick.handleSecond(player);
        }
    }
}
