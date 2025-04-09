package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.beyondthehorizon.baseform.events.client.BaseformClientTick;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class ClientPlayerTickHandler
{
    public static int tickCounter = 0;
    private static final int TICKS_PER_SECOND = 20;

    public static void handleTick(Player pPlayer)
    {
        //Extract Local Player
        if(!(pPlayer instanceof AbstractClientPlayer player)) return;

        //If Player is Dead then remove
        if (!player.isAlive())
            return;

        //Handle Tick
        BaseformClientTick.handleTick(player);

        //Every Second
        if (++tickCounter >= TICKS_PER_SECOND) {
            tickCounter = 0;
            handleSecond(player);
        }
    }

    private static void handleSecond(AbstractClientPlayer player)
    {
        //Run every second
        BaseformClientTick.handleSecond(player);

        //Client Data
        System.out.println("Client Data: "+player.getData(SONIC_DATA));
    }
}
