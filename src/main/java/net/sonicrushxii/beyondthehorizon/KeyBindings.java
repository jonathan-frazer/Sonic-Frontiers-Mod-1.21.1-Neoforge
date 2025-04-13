package net.sonicrushxii.beyondthehorizon;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
public class KeyBindings
{
    private static final String CATEGORY = "key.categories."+BeyondTheHorizon.MOD_ID;

    public static final KeyMapping DOUBLE_JUMP = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".DoubleJump", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_SPACE, // Default key is Space
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping VIRTUAL_SLOT_USE = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_R, // Default key is R
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping PARRY = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".ParryKey", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_GRAVE_ACCENT, // Default key is `
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_1 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_1", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_Z, // Default key is Z
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_2 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_2", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_X, // Default key is X
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_3 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_3", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_C, // Default key is C
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_4 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_4", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_V, // Default key is V
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_5 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_5", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_G, // Default key is G
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ABILITY_6 = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_6", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_B, // Default key is B
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_SINGLE_ABILITY = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersSlot_Single", // Will be localized using this translation key
            InputConstants.Type.MOUSE, // Default mapping is on the keyboard
            GLFW.GLFW_MOUSE_BUTTON_RIGHT, // Default key is RMB
            CATEGORY // Mapping will be in the aforementioned category
    );

    public static final KeyMapping USE_ULTIMATE_ABILITY = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".FrontiersUltUse", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_U, // Default key is U
            CATEGORY // Mapping will be in the aforementioned category
    );
    public static final KeyMapping HELP_BUTTON = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID+".FrontiersHelp", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_H, // Default key is H
            CATEGORY // Mapping will be in the aforementioned category
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event)
    {
        event.register(VIRTUAL_SLOT_USE);
        event.register(USE_ABILITY_1);
        event.register(USE_ABILITY_2);
        event.register(USE_ABILITY_3);
        event.register(USE_ABILITY_4);
        event.register(USE_ABILITY_5);
        event.register(USE_ABILITY_6);
        event.register(PARRY);
        event.register(USE_SINGLE_ABILITY);
        event.register(USE_ULTIMATE_ABILITY);
        event.register(HELP_BUTTON);
    }



}
