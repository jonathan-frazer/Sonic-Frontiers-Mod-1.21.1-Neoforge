package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.KeyBindings;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.packet.KeyPressPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;

import java.util.Arrays;

public class BaseformClientTick
{
    public static void handleTick(AbstractClientPlayer player)
    {
        Minecraft mc = Minecraft.getInstance();

        //Extract Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.state;

        //Key Booleans
        final boolean isCtrlDown = (InputConstants.isKeyDown(mc.getWindow().getWindow(), InputConstants.KEY_RCONTROL)
                || InputConstants.isKeyDown(mc.getWindow().getWindow(), InputConstants.KEY_LCONTROL));
        final boolean isShiftDown = (InputConstants.isKeyDown(mc.getWindow().getWindow(), InputConstants.KEY_RSHIFT)
                || InputConstants.isKeyDown(mc.getWindow().getWindow(), InputConstants.KEY_LSHIFT));
        final boolean holdingLeftClick = mc.options.keyAttack.isDown();

        //Passives
        {
            //Double Jump
            {
                //Key Press
                while(KeyBindings.DOUBLE_JUMP.consumeClick())
                {
                    //Send a KeyPress Packet to the Server
                    PacketDistributor.sendToServer(new KeyPressPacket(KeyBindings.DOUBLE_JUMP.getKey().getValue()));
                }
            }

            //Danger Sense
            {
                //Key Press
                while(KeyBindings.TOGGLE_DANGER_SENSE.consumeClick())
                {
                    if(!isCtrlDown || !isShiftDown) break; //Must be holding both ctrl and shift to activate danger sense

                    //Danger Sense Message
                    player.displayClientMessage(
                            Component.nullToEmpty(
                                    (baseformState.getState(BaseformState.DANGER_SENSE_ACTIVE.ordinal()))
                                            ?"Danger Sense Inhibited":"Danger Sense Activated"
                            ),true);

                    PacketDistributor.sendToServer(new KeyPressPacket(KeyBindings.TOGGLE_DANGER_SENSE.getKey().getValue()));
                }
            }
        }

    }

    public static void handleSecond(AbstractClientPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;

        System.err.println(Arrays.toString(baseformProperties.auxiliaryCounters));
    }
}
