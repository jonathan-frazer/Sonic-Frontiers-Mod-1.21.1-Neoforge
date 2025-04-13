package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.KeyBindings;
import net.sonicrushxii.beyondthehorizon.client.VirtualSlotHandler;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
public class InputSlotHandler
{
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event)
    {
        boolean isScrollingUp = (event.getScrollDeltaY() >= 0);

        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;

        //If Player doesn't exist
        if(player == null || !player.level().isClientSide()) return;
        //If Player doesn't have a sonic form
        if(player.getData(SONIC_DATA).properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1).equals("none"))
            return;
        //If Button Isn't pressed
        if (!KeyBindings.VIRTUAL_SLOT_USE.isDown()) return;

        //Cancel Event
        event.setCanceled(true);

        //Update Scroll
        if (isScrollingUp) VirtualSlotHandler.scrollUpByOne();
        else VirtualSlotHandler.scrollDownByOne();

        while (KeyBindings.USE_ABILITY_1.consumeClick()) ;
        while (KeyBindings.USE_ABILITY_2.consumeClick()) ;
        while (KeyBindings.USE_ABILITY_3.consumeClick()) ;
        while (KeyBindings.USE_ABILITY_4.consumeClick()) ;
        while (KeyBindings.USE_ABILITY_5.consumeClick()) ;
        while (KeyBindings.USE_ABILITY_6.consumeClick()) ;
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event)
    {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;

        //If Player doesn't exist
        if(player == null || !player.level().isClientSide()) return;
        //If Player doesn't have a sonic form
        if(player.getData(SONIC_DATA).properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1).equals("none"))
            return;
        //If Button Isn't pressed
        if (!KeyBindings.VIRTUAL_SLOT_USE.isDown()) return;

        //Jump to Slot
        int key = event.getKey();
        if (key >= InputConstants.KEY_1 && key <= InputConstants.KEY_9)
        {
            VirtualSlotHandler.setSlot((byte)(key-InputConstants.KEY_1));

            while (KeyBindings.USE_ABILITY_1.consumeClick()) ;
            while (KeyBindings.USE_ABILITY_2.consumeClick()) ;
            while (KeyBindings.USE_ABILITY_3.consumeClick()) ;
            while (KeyBindings.USE_ABILITY_4.consumeClick()) ;
            while (KeyBindings.USE_ABILITY_5.consumeClick()) ;
            while (KeyBindings.USE_ABILITY_6.consumeClick()) ;
        }

    }
}