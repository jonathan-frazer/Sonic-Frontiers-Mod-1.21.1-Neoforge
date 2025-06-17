package net.sonicrushxii.beyondthehorizon.sonic.baseform.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

public class BaseformAttributeModifiers {
    public static final AttributeModifier STEP_UP_SPRINT = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "step_up_sprint"), 1.50F, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier STEP_UP_BASE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "step_up_base"), 0.50F, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier COMBO_GRAVITY = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "combo_gravity"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    public static final AttributeModifier LIGHTSPEED_MODE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "lightspeed_mode"),1.0F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final AttributeModifier POWERBOOST_SPEED = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "powerboost_speed"),0.60F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final AttributeModifier POWERBOOST_ARMOR = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "powerboost_armor"),3.67F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final AttributeModifier SMASH_HIT = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "smash_hit"),-1.0F, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier PARRY_HOLD = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "parry_hold"),-1.5F, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier PARRY_SPEED = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "parry_speed"),0.60F, AttributeModifier.Operation.ADD_VALUE);
}
