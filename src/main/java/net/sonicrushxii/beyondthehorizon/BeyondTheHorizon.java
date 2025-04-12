package net.sonicrushxii.beyondthehorizon;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.models.ParryModelPre;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.models.Spindash;
import net.sonicrushxii.beyondthehorizon.event_handlers.*;
import net.sonicrushxii.beyondthehorizon.event_handlers.client.EntityRenderHandler;
import net.sonicrushxii.beyondthehorizon.event_handlers.server.ServerWorldHandler;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.modded.ModCreativeModeTabs;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;
import net.sonicrushxii.beyondthehorizon.modded.ModSounds;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BeyondTheHorizon.MOD_ID)
public class BeyondTheHorizon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "beyondthehorizon";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public BeyondTheHorizon(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(PacketHandler::register);

        //Register Modded Components
        ModAttachments.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModSounds.register(modEventBus);

        ModItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(PlayerEventHandler.class);
        NeoForge.EVENT_BUS.register(LoginHandler.class);
        NeoForge.EVENT_BUS.register(EntityRenderHandler.class);
        NeoForge.EVENT_BUS.register(PlayerTickHandler.class);
        NeoForge.EVENT_BUS.register(EquipmentChangeHandler.class);
        NeoForge.EVENT_BUS.register(ServerWorldHandler.class);
        NeoForge.EVENT_BUS.register(FallDamageHandler.class);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerModelLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            //Spin Dash
            event.registerLayerDefinition(Spindash.LAYER_LOCATION,Spindash::createBodyLayer);
            //Model Registration
            event.registerLayerDefinition(ParryModelPre.LAYER_LOCATION,ParryModelPre::createBodyLayer);
        }
    }
}
