package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;

public class BaseformRenderer {

    /**
     * If you want to turn off Player Rendering set event.setCanceled(true)
     * Generally turn off player rendering within an if Condition. Else player will always be invisible
     */
    public static void onRenderPlayerModelPre(RenderLivingEvent.Pre<?, ?> event, Player player, BaseformAttachmentData baseformProperties) {
        PoseStack poseStack = event.getPoseStack();

        assert Minecraft.getInstance().gameMode != null;
        if (Minecraft.getInstance().gameMode.getPlayerMode() == GameType.SPECTATOR) return;

    }

    public static void onRenderPlayerModelPost(RenderLivingEvent.Post<?, ?> event, Player player, BaseformAttachmentData baseformProperties) {
        PoseStack poseStack = event.getPoseStack();


    }

    public static void onRenderToSelfPre(RenderLivingEvent.Pre<?, ?> event, LivingEntity target, BaseformAttachmentData baseformProperties)
    {

    }

    public static void onRenderToSelfPost(RenderLivingEvent.Post<?, ?> event, LivingEntity target, BaseformAttachmentData baseformProperties)
    {

    }

    public static void onRenderToEveryonePre(RenderLivingEvent.Pre<?,?> event, LivingEntity target)
    {
    }

    public static void onRenderToEveryonePost(RenderLivingEvent.Post<?,?> event, LivingEntity target)
    {
    }
}
