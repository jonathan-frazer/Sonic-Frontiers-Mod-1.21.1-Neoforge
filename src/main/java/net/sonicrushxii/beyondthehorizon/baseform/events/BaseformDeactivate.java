package net.sonicrushxii.beyondthehorizon.baseform.events;

import net.minecraft.server.level.ServerPlayer;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;

public class BaseformDeactivate
{
    public static void onBaseformDeactivate(ServerPlayer player)
    {
        //Extract Players Sonic Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);

        playerSonicData.isSonic = false;
        playerSonicData.properties = new AttachmentData();
    }
}
