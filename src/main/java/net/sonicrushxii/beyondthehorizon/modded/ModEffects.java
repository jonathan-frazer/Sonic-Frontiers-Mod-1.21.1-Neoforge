package net.sonicrushxii.beyondthehorizon.modded;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.modded.potion_effects.ComboEffect;

public class ModEffects
{
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(Registries.MOB_EFFECT, BeyondTheHorizon.MOD_ID);

    public static final Holder<MobEffect> COMBO_EFFECT = MOB_EFFECTS.register("baseform/combo_effect",
            ()-> new ComboEffect(MobEffectCategory.HARMFUL,0x00FF11)
                    .addAttributeModifier(Attributes.GRAVITY,
                            ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID,"baseform/combo_effect_gravity"),
                            -0.95f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
