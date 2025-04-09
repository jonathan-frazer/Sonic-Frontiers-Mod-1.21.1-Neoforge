package net.sonicrushxii.beyondthehorizon.baseform.events.server;

import net.minecraft.server.level.ServerPlayer;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformAttachmentData;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class BaseformServerTick {

    public static int bitTrip = 0;

    public static void handleTick(ServerPlayer player) {

    }

    public static void handleSecond(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.concurrentState;

        //Test Byte State Holder
        /*{
            //Set the Appropriate Bit Pos
            baseformState.setBitPos(bitTrip++);

            //Print if 10th bit is set, [0-9]
            System.out.println(baseformState.getBitPos(9));

            //Update on Client Side
            PacketDistributor.sendToPlayer(player,new SyncSonicData(playerSonicData.serializeNBT(null)));
        }*/
    }
}
