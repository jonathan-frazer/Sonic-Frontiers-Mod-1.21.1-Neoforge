package net.sonicrushxii.beyondthehorizon;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings
{
    private static final String CATEGORY = "key.categories."+BeyondTheHorizon.MOD_ID;

    public static final KeyMapping DOUBLE_JUMP_MAPPING = new KeyMapping(
            "key."+ BeyondTheHorizon.MOD_ID +".DoubleJump", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_SPACE, // Default key is Space
            CATEGORY // Mapping will be in the aforementioned category
    );
}
