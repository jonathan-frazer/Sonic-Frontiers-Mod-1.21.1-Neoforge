package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;
import net.sonicrushxii.beyondthehorizon.client.VirtualSlotOverlay;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
public class OverlayRenderHandler
{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Pre event)
    {
        //Get Player
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;
        if(player == null) return;

        //Get Data
        AttachmentData playerProperties = player.getData(SONIC_DATA).properties;

        //Get Graphics
        GuiGraphics guiGraphics = event.getGuiGraphics();

        //Render Slots
        switch(playerProperties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
        {
            case "baseform" -> VirtualSlotOverlay.renderBaseFormSlots(mc.player, guiGraphics,guiGraphics.guiWidth(),guiGraphics.guiHeight());
            case "superform" -> {}
            case "starfallform" -> {}
            case "hyperform" -> {}
        }
    }
}
