package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.ClientPlayerTickHandler;
import net.sonicrushxii.beyondthehorizon.modded.ModModelRenderer;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformAuxiliaryCounters;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.models.SonicBoostModel;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.models.SonicPeeloutModel;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.models.Spindash;

public class BaseformRenderer {

    /**
     * If you want to turn off Player Rendering set event.setCanceled(true)
     * Generally turn off player rendering within an if Condition. Else player will always be invisible
     */

    //
    public static void onRenderPlayerModelPre(RenderLivingEvent.Pre<?, ?> event, Player player, BaseformAttachmentData baseformProperties) {
        PoseStack poseStack = event.getPoseStack();

        //Do not Render if in Spectator Mode
        assert Minecraft.getInstance().gameMode != null;
        if (Minecraft.getInstance().gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        //Get Data
        ByteStateHolder baseformState = baseformProperties.state;
        int[] auxillaryCounters = baseformProperties.auxiliaryCounters;
        int boostLvl = auxillaryCounters[BaseformAuxiliaryCounters.BOOST_LV_COUNTER.ordinal()];

        //Peelout
        if(boostLvl == 2 && player.isSprinting())
        {
            poseStack.pushPose();

            //Scale
            poseStack.scale(1.0f, 1.0f, 1.0f);

            //Apply Rotation & Translation
            poseStack.mulPose(Axis.YP.rotationDegrees(-player.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

            poseStack.translate(0D,-1.5D,0D);

            //Render The Custom Model
            ModModelRenderer.renderSonicPeelout(SonicPeeloutModel.class,event,poseStack,baseformProperties,(modelPart)-> modelPart.getChild("Head").xRot = (float)(player.getXRot()*Math.PI/180));
            poseStack.popPose();
            event.setCanceled(true);
        }
        //Boost
        else if(boostLvl == 3 && player.isSprinting())
        {
            poseStack.pushPose();

            //Scale
            poseStack.scale(1.0f, 1.0f, 1.0f);

            //Apply Rotation & Translation
            poseStack.mulPose(Axis.YP.rotationDegrees(-player.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

            poseStack.translate(0D,-1.5D,0D);

            //Render The Custom Model
            ModModelRenderer.renderPlayerModel(SonicBoostModel.class,event,poseStack,baseformProperties,(modelPart)->{
                modelPart.getChild("Head").xRot = (float)(player.getXRot()*Math.PI/180);
                float rotation = (float) (90.0F * Math.sin((Math.PI / 2) * ClientPlayerTickHandler.tickCounter%4) * (Math.PI / 180));
                modelPart.getChild("RightLeg").xRot = rotation;
                modelPart.getChild("LeftLeg").xRot = -rotation;
            });
            poseStack.popPose();
            event.setCanceled(true);
        }
        //Ballform - Grounded
        else if(baseformState.getState(BaseformState.BALL_FORM_GROUND.ordinal()))
        {
            poseStack.pushPose();
            //Translate
            poseStack.translate(0.0D, -0.65D, 0.0D);

            // Scale
            poseStack.scale(1.15f, 1.15f, 1.15f);

            //Apply Rotation
            float playerYaw = (player.getYRot() > 180.0) ? player.getYRot() - 180.0f : player.getYRot() + 180.0f;
            poseStack.mulPose(Axis.YP.rotationDegrees(-playerYaw));

            //Render The Custom Model
            ModModelRenderer.renderModel(Spindash.class, event, poseStack,null);
            poseStack.popPose();

            event.setCanceled(true);
        }
        //Ballform - Aerial
        else if(baseformState.getState(BaseformState.BALL_FORM_AERIAL.ordinal()))
        {
            poseStack.pushPose();
            //Translate
            poseStack.translate(0.0D, -0.65D, 0.0D);

            // Scale
            poseStack.scale(1.15f, 1.15f, 1.15f);

            //Apply Rotation
            float playerYaw = (player.getYRot() > 180.0) ? player.getYRot() - 180.0f : player.getYRot() + 180.0f;
            poseStack.mulPose(Axis.YP.rotationDegrees(-playerYaw));

            //Render The Custom Model
            ModModelRenderer.renderModel(Spindash.class, event, poseStack,null);
            poseStack.popPose();

            event.setCanceled(true);
        }

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
