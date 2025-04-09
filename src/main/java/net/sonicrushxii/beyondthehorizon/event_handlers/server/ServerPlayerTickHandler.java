package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.beyondthehorizon.baseform.events.server.BaseformServerTick;

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

        //Run every second
        BaseformServerTick.handleTick(player);

        //Every Second
        if (++tickCounter >= TICKS_PER_SECOND) {
            tickCounter = 0;
            handleSecond(player);
        }
    }

    private static void handleSecond(ServerPlayer player)
    {
        //Run every second
        BaseformServerTick.handleSecond(player);

        //Server Data
        System.err.println("Server Data: "+player.getData(SONIC_DATA));
    }
}
