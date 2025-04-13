package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientDataPacketHandler;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerDataPacketHandler;
import net.sonicrushxii.beyondthehorizon.packet.*;

public class PacketHandler
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");

        //Sync Sonic Data
        registrar.playBidirectional(
                SyncSonicPacket.TYPE,
                SyncSonicPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientDataPacketHandler::handleSyncSonicData,
                        ServerDataPacketHandler::handleSyncSonicData
                )
        );

        //Key Press Packet
        registrar.playToServer(
                KeyPressPacket.TYPE,
                KeyPressPacket.STREAM_CODEC,
                ServerDataPacketHandler::handleKeyPress
        );

        //Initialize Virtual Slot Packet
        registrar.playToClient(
                InitializeVirtualSlotPacket.TYPE,
                InitializeVirtualSlotPacket.STREAM_CODEC,
                ClientDataPacketHandler::handleVirtualSlotInitialization
        );

        //Danger Sense Sounds
        registrar.playToClient(
                DangerSenseSoundPacket.TYPE,
                DangerSenseSoundPacket.STREAM_CODEC,
                ClientDataPacketHandler::handleDangerSenseSound
        );

        //Danger Sense Particle
        registrar.playToClient(
                DangerSenseParticlePacket.TYPE,
                DangerSenseParticlePacket.STREAM_CODEC,
                ClientDataPacketHandler::handleDangerSenseParticle
        );
    }
}
