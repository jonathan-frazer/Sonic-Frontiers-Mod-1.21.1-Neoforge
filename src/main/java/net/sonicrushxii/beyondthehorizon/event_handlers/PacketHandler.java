package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sonicrushxii.beyondthehorizon.attachments.KeyPressData;
import net.sonicrushxii.beyondthehorizon.attachments.SyncSonicData;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientDataPacketHandler;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerDataPacketHandler;

public class PacketHandler
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");

        //Sync Data
        registrar.playBidirectional(
                SyncSonicData.TYPE,
                SyncSonicData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientDataPacketHandler::handleSyncSonicData,
                        ServerDataPacketHandler::handleSyncSonicData
                )
        );

        //Key Press
        registrar.playToServer(
                KeyPressData.TYPE,
                KeyPressData.STREAM_CODEC,
                ServerDataPacketHandler::handleKeyPress
        );
    }
}
