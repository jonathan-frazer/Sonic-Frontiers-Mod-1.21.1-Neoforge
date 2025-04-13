package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sonicrushxii.beyondthehorizon.ModUtils;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientPlayerTickHandler;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerPlayerTickHandler;

import java.util.Arrays;

public class PlayerTickHandler {

    public static boolean hasAllChaosEmeralds(Player player)
    {
        return ModUtils.playerHasAllItems
                (
                        player,
                        Arrays.asList(
                                "chaos_emerald/aqua_emerald",
                                "chaos_emerald/blue_emerald",
                                "chaos_emerald/green_emerald",
                                "chaos_emerald/grey_emerald",
                                "chaos_emerald/purple_emerald",
                                "chaos_emerald/red_emerald",
                                "chaos_emerald/yellow_emerald"
                        ), "chaos_emerald"
                );
    }

    public static boolean hasAllSuperEmeralds(Player player)
    {
        return ModUtils.playerHasAllItems
                (
                        player,
                        Arrays.asList(
                                "super_emerald/aqua_super_emerald",
                                "super_emerald/blue_super_emerald",
                                "super_emerald/green_super_emerald",
                                "super_emerald/grey_super_emerald",
                                "super_emerald/purple_super_emerald",
                                "super_emerald/red_super_emerald",
                                "super_emerald/yellow_super_emerald"
                        ), "chaos_emerald"
                );
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();

        if(player.level().isClientSide && FMLEnvironment.dist == Dist.CLIENT)
            ClientPlayerTickHandler.handleTick(player);
        else
            ServerPlayerTickHandler.handleTick(player);
    }
}
