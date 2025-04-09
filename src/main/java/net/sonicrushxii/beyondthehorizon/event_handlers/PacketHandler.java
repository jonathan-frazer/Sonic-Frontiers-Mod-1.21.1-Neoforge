package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sonicrushxii.beyondthehorizon.attachments.SyncSonicData;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientDataPacketHandler;

public class PacketHandler
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SyncSonicData.TYPE,
                SyncSonicData.STREAM_CODEC,
                ClientDataPacketHandler::handleDataOnMain
        );
    }
}
