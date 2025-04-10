package net.sonicrushxii.beyondthehorizon.baseform.events.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.KeyBindings;
import net.sonicrushxii.beyondthehorizon.attachments.KeyPressData;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;

public class BaseformClientTick
{
    public static void handleTick(AbstractClientPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.state;

        //Passives
        {
            //Double Jump
            {
                //Handle Key Press
                while(KeyBindings.DOUBLE_JUMP_MAPPING.consumeClick())
                {
                    //Send a KeyPress Packet to the Server
                    PacketDistributor.sendToServer(new KeyPressData(KeyBindings.DOUBLE_JUMP_MAPPING.getKey().getValue()));
                }
            }
        }

    }

    public static void handleSecond(AbstractClientPlayer player)
    {
    }
}
