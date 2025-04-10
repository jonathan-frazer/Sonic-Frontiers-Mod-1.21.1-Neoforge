package net.sonicrushxii.beyondthehorizon.baseform.events.server;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.attachments.SyncSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.baseform.data.enums.BaseformState;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;

public class BaseformServerTick {

    public static void handleTick(ServerPlayer player)
    {
        //Checks if Packet should be synced this tick.
        boolean syncPacket = false;
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.state;

        //Passives
        {
            //Double Jump
            {
                //Clear Flag
                if (baseformState.getState(BaseformState.CONSUMED_DOUBLE_JUMP.ordinal()) && player.onGround()) {
                    baseformState.clearState(BaseformState.CONSUMED_DOUBLE_JUMP.ordinal());
                    syncPacket = true;
                }
            }
        }

        //End, Check if Packet needs to be synced
        if(syncPacket) PacketDistributor.sendToPlayer(player,new SyncSonicData(playerSonicData.serializeNBT(null)));
    }

    public static void handleSecond(ServerPlayer player)
    {
    }
}
