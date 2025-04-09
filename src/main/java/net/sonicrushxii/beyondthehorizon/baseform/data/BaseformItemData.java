package net.sonicrushxii.beyondthehorizon.baseform.data;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;

import java.util.List;
import java.util.UUID;

public class BaseformItemData
{
    public static CompoundTag baseformArmorNBTTag; static {
        baseformArmorNBTTag = new CompoundTag();
        ListTag enchantmentList = new ListTag();
        CompoundTag enchantment = new CompoundTag();
        enchantment.putString("id", "minecraft:binding_curse");
        enchantment.putShort("lvl", (short) 1);
        enchantmentList.add(enchantment);
        baseformArmorNBTTag.put("Enchantments", enchantmentList);
        baseformArmorNBTTag.putInt("HideFlags", 127);
        baseformArmorNBTTag.putByte("Unbreakable", (byte) 1);
        baseformArmorNBTTag.putByte("BeyondTheHorizon", (byte) 1);
    }

    //Baseform Components
    public static final ResolvableProfile SONIC_BASEHEAD_PROFILE;
    static{
        GameProfile gameProfile = new GameProfile(
                new UUID(512370214, -95272899L ^ -2003262887L ^ 1067375885L), // Generate a mostly consistent UUID
                "SonicFrontiers"
        );
        gameProfile.getProperties().put("textures", new Property(
                "textures",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTBjN2NlZWNjODliNTY0MjNhOWU4YWFiMTE3NjRkZTI5MDIyNjU4MzA5YTUyNjY2M2JmMzQyNGY0N2NhZDlmOCJ9fX0="
        ));
        SONIC_BASEHEAD_PROFILE = new ResolvableProfile(gameProfile);
    }
    public static final Component SONIC_BASEHEAD_NAME = Component.literal("Sonic Head").withStyle(Style.EMPTY.withColor(0x0000FF).withItalic(false));
    public static final ItemLore SONIC_BASEHEAD_LORE = new ItemLore(List.of(
            Component.literal("Adapted from Sonic Frontiers").withStyle(style -> style.withColor(0xFF55FF)) // light_purple
    ));

    //Assign Baseform Components to Head
    public static ItemStack baseformSonicHead; static {
        baseformSonicHead = new ItemStack(Items.PLAYER_HEAD);

        // 🟦 1. Set the custom "BeyondTheHorizon" byte using CustomData
        CompoundTag customTag = new CompoundTag();
        customTag.putByte("BeyondTheHorizon", (byte) 2);
        baseformSonicHead.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));

        // 🟦 2. Set the SkullOwner with texture
        baseformSonicHead.set(DataComponents.PROFILE, SONIC_BASEHEAD_PROFILE);

        // 🟦 3. Set the display name
        baseformSonicHead.set(DataComponents.CUSTOM_NAME, SONIC_BASEHEAD_NAME);

        // 🟦 4. Set the lore text
        baseformSonicHead.set(DataComponents.LORE, SONIC_BASEHEAD_LORE);
    }

    //Baseform LightSpeed Components
    public static final ResolvableProfile SONIC_BASEHEAD_LS_PROFILE;
    static{
        GameProfile profile = new GameProfile(
                new UUID(512370214, -95272899L ^ -2003262887L ^ 1067375885L), // Generate a mostly consistent UUID
                "SonicFrontiers"
        );
        profile.getProperties().put("textures", new Property(
                "textures",
                "ewogICJ0aW1lc3RhbXAiIDogMTcyNjkyNzYxNjIxNSwKICAicHJvZmlsZUlkIiA6ICI2OTBmOTAwMTczZmQ0MDA5OGE2ZDc3Nzc2MWUwY2U4YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTb25pY1J1c2hYMTIiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QyNzZmMGExMTBkMGEzNzhiNzdlNzk3OTBiZDc0ZjNiOWEzMmNhNzgyYWQ2MTQ2NjhhYWE1ZmM4MDg5MWIwMCIKICAgIH0KICB9Cn0="
        ));
        SONIC_BASEHEAD_LS_PROFILE = new ResolvableProfile(profile);
    }
    public static final Component SONIC_BASEHEAD_LS_NAME = Component.literal("Sonic Head").withStyle(Style.EMPTY.withColor(0x0000FF).withItalic(false));
    public static final ItemLore SONIC_BASEHEAD_LS_LORE = new ItemLore(List.of(
            Component.literal("Light Speed Mode").withStyle(style -> style.withColor(0x55FFFF)) // light_purple
    ));

    //Assign Baseform LightSpeed Components to Head
    public static ItemStack baseformLSSonicHead; static {
        baseformLSSonicHead = new ItemStack(Items.PLAYER_HEAD);

        // 🟦 1. Set the custom "BeyondTheHorizon" byte using CustomData
        CompoundTag customTag = new CompoundTag();
        customTag.putByte("BeyondTheHorizon", (byte) 2);
        baseformLSSonicHead.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));

        // 🟦 2. Set the SkullOwner with texture

        baseformLSSonicHead.set(DataComponents.PROFILE, SONIC_BASEHEAD_LS_PROFILE);

        // 🟦 3. Set the display name
        baseformLSSonicHead.set(DataComponents.CUSTOM_NAME, SONIC_BASEHEAD_LS_NAME);

        // 🟦 4. Set the lore text
        baseformLSSonicHead.set(DataComponents.LORE, SONIC_BASEHEAD_LS_LORE);
    }


    //Baseform PowerBoost Components
    public static final ResolvableProfile SONIC_BASEHEAD_PB_PROFILE;
    static{
        GameProfile profile = new GameProfile(
                new UUID(512370214, -95272899L ^ -2003262887L ^ 1067375885L), // Generate a mostly consistent UUID
                "SonicFrontiers"
        );
        profile.getProperties().put("textures", new Property(
                "textures",
                "ewogICJ0aW1lc3RhbXAiIDogMTcyNzg5MTc2MDg1NywKICAicHJvZmlsZUlkIiA6ICI2OTBmOTAwMTczZmQ0MDA5OGE2ZDc3Nzc2MWUwY2U4YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTb25pY1J1c2hYMTIiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdlMDM2OWQxYzQ3ZWYwZmFjMmVjNGE2MmI5NzgxZDZjOGE0NTRlOGNiYjBkOTg5ODgxMWVkNjlhZjhhOWJiZCIKICAgIH0KICB9Cn0="
        ));
        SONIC_BASEHEAD_PB_PROFILE = new ResolvableProfile(profile);
    }
    public static final Component SONIC_BASEHEAD_PB_NAME = Component.literal("Sonic Head").withStyle(Style.EMPTY.withColor(0x0000FF).withItalic(false));
    public static final ItemLore SONIC_BASEHEAD_PB_LORE = new ItemLore(List.of(
            Component.literal("Power Boost").withStyle(style -> style.withColor(0x5555FF)) // blue
    ));

    //Assign Baseform PowerBoost Components to Head
    public static ItemStack baseformPBSonicHead; static {
        baseformPBSonicHead = new ItemStack(Items.PLAYER_HEAD);

        // 🟦 1. Set the custom "BeyondTheHorizon" byte using CustomData
        CompoundTag customTag = new CompoundTag();
        customTag.putByte("BeyondTheHorizon", (byte) 2);
        baseformPBSonicHead.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));

        // 🟦 2. Set the SkullOwner with texture

        baseformPBSonicHead.set(DataComponents.PROFILE, SONIC_BASEHEAD_PB_PROFILE);

        // 🟦 3. Set the display name
        baseformPBSonicHead.set(DataComponents.CUSTOM_NAME, SONIC_BASEHEAD_PB_NAME);

        // 🟦 4. Set the lore text
        baseformPBSonicHead.set(DataComponents.LORE, SONIC_BASEHEAD_PB_LORE);
    }
}
