package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sonicrushxii.beyondthehorizon.KeyBindings;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server.BaseformKeyPress;
import net.sonicrushxii.beyondthehorizon.packet.KeyPressPacket;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class ServerDataPacketHandler
{

    public static void handleSyncSonicData(final SyncSonicPacket data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if (context.flow().isClientbound()) return;

        //Get Server Player
        ServerPlayer serverPlayer = (ServerPlayer) context.player();
        CompoundTag updatedSonicNbt = data.formDetails();

        //Inject new Data into Client
        serverPlayer.getData(SONIC_DATA).deserializeNBT(null,data.formDetails());
    }

    public static void handleKeyPress(final KeyPressPacket data, final IPayloadContext context)
    {
        // Do something with the data, on the main thread
        if (context.flow().isClientbound()) return;

        int keyCode = data.keyCode();

        ServerPlayer serverPlayer = (ServerPlayer) context.player();

        if(keyCode == KeyBindings.DOUBLE_JUMP.getKey().getValue())
            BaseformKeyPress.handleDoubleJump(serverPlayer);
        //else if ...
    }
}
