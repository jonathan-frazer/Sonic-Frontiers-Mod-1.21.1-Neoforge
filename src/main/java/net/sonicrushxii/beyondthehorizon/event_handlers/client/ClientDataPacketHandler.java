package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sonicrushxii.beyondthehorizon.attachments.SyncSonicData;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class ClientDataPacketHandler
{
    public static void handleSyncSonicData(final SyncSonicData data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if (context.flow().isServerbound()) return;

        //Get Client Player
        AbstractClientPlayer clientPlayer = Minecraft.getInstance().player;

        //Inject new Data into Client
        assert clientPlayer != null;
        clientPlayer.getData(SONIC_DATA).deserializeNBT(null,data.formDetails());
    }
}
