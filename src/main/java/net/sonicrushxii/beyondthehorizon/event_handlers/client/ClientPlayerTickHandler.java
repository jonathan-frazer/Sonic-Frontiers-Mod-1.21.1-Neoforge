package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class ClientPlayerTickHandler
{
    public static int tickCounter = 0;
    private static final int TICKS_PER_SECOND = 20;

    public static void handleTick(Player pPlayer)
    {
        //Extract Server Placer
        if(!(pPlayer instanceof LocalPlayer player)) return;

        //If Player is Dead then remove
        if (!player.isAlive())
            return;

        //Every Second
        if (++tickCounter >= TICKS_PER_SECOND) {
            tickCounter = 0;
            handleSecond(player);
        }
    }

    private static void handleSecond(LocalPlayer player)
    {

    }
}
