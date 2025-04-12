package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
public class OverlayRenderHandler
{
    // This event is called during the HUD rendering phase
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Pre event) {
        // Check for the correct overlay phase (AFTER the main hotbar renders)
        {
            Minecraft mc = Minecraft.getInstance();
            // Don't render in debug screen or if there's no player
            if (mc.player != null)
            {
                GuiGraphics guiGraphics = event.getGuiGraphics();
            }
        }
    }
}
