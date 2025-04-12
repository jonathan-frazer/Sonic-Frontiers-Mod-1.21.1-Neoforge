package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.events.client.BaseformRenderer;
import net.sonicrushxii.beyondthehorizon.client.ClientOnlyDetails;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
public class EntityRenderHandler
{
    @SubscribeEvent
    public static void onRenderPre(RenderLivingEvent.Pre<?,?> event) // <T extends LivingEntity, M extends LivingEntityModel<T>>)
    {
        //Manage Player Models
        {
            try {
                LivingEntity entity = event.getEntity();
                AttachmentData properties = entity.getData(SONIC_DATA).properties;
                switch(properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
                {
                    case "baseform" -> BaseformRenderer.onRenderPlayerModelPre(event, (Player) entity, (BaseformAttachmentData) properties);
                    //case "superform" -> SuperformRenderer.onRenderPlayerModelPre(event, (Player) entity, (SuperformAttachmentData) properties);
                    //case "starfallform" -> StarfallformRenderer.onRenderPlayerModelPre(event, (Player) entity, (StarfallformAttachmentData) properties);
                    //case "hyperform" -> HyperformRenderer.onRenderPlayerModelPre(event, (Player) entity, (HyperformAttachmentData) properties);
                }

            } catch (NullPointerException | NoSuchMethodError | ClassCastException ignored) {}
        }

        //Manage Render to Everyone
        {
            BaseformRenderer.onRenderToEveryonePre(event, event.getEntity());
            // SuperformRenderer.onRenderToEveryonePre(event, event.getEntity());
            // StarfallformRenderer.onRenderToEveryonePre(event, event.getEntity());
            // HyperformRenderer.onRenderToEveryonePre(event, event.getEntity());
        }

        //Manage Self Rendering - Triggered only for you, Other players can't see
        if(ClientOnlyDetails.selfRender)
        {
            LivingEntity entity = event.getEntity();
            AttachmentData properties = entity.getData(SONIC_DATA).properties;
            switch(properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
            {
                case "baseform" -> BaseformRenderer.onRenderToSelfPre(event, entity, (BaseformAttachmentData) properties);
                //case "superform" -> SuperformRenderer.onRenderToSelfPre(event, entity, (SuperformAttachmentData) properties);
                //case "starfallform" -> StarfallformRenderer.onRenderToSelfPre(event,  entity, (StarfallformAttachmentData) properties);
                //case "hyperform" -> HyperformRenderer.onRenderToSelfPre(event,  entity, (HyperformAttachmentData) properties);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderPost(RenderLivingEvent.Post<?,?> event) // <T extends LivingEntity, M extends LivingEntityModel<T>>)
    {
        //Manage Player Models
        {
            try {
                LivingEntity entity = event.getEntity();
                AttachmentData properties = entity.getData(SONIC_DATA).properties;
                switch(properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
                {
                    case "baseform" -> BaseformRenderer.onRenderPlayerModelPost(event, (Player) entity, (BaseformAttachmentData) properties);
                    //case "superform" -> SuperformRenderer.onRenderPlayerModelPost(event, (Player) entity, (SuperformAttachmentData) properties);
                    //case "starfallform" -> StarfallformRenderer.onRenderPlayerModelPost(event, (Player) entity, (StarfallformAttachmentData) properties);
                    //case "hyperform" -> HyperformRenderer.onRenderPlayerModelPost(event, (Player) entity, (HyperformAttachmentData) properties);
                }

            } catch (NullPointerException | NoSuchMethodError | ClassCastException ignored) {}
        }

        //Manage Render to Everyone
        {
            BaseformRenderer.onRenderToEveryonePost(event, event.getEntity());
            // SuperformRenderer.onRenderToEveryonePost(event, event.getEntity());
            // StarfallformRenderer.onRenderToEveryonePost(event, event.getEntity());
            // HyperformRenderer.onRenderToEveryonePost(event, event.getEntity());
        }

        //Manage Self Rendering - Triggered only for you, Other players can't see
        if(ClientOnlyDetails.selfRender)
        {
            LivingEntity entity = event.getEntity();
            AttachmentData properties = entity.getData(SONIC_DATA).properties;
            switch(properties.getForm().substring(BeyondTheHorizon.MOD_ID.length()+1))
            {
                case "baseform" -> BaseformRenderer.onRenderToSelfPost(event, entity, (BaseformAttachmentData) properties);
                //case "superform" -> SuperformRenderer.onRenderToSelfPost(event, entity, (SuperformAttachmentData) properties);
                //case "starfallform" -> StarfallformRenderer.onRenderToSelfPost(event,  entity, (StarfallformAttachmentData) properties);
                //case "hyperform" -> HyperformRenderer.onRenderToSelfPost(event,  entity, (HyperformAttachmentData) properties);
            }
        }
    }
}
