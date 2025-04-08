package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;
import net.sonicrushxii.beyondthehorizon.modded.armor.client.model.SonicArmorModel;
import net.sonicrushxii.beyondthehorizon.modded.armor.client.renderer.ArmorRenderer;
import org.jetbrains.annotations.NotNull;

public class ClientArmorHandler {
    @SubscribeEvent
    public static void clientItemInitialize(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
                               @Override
                               public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                                   HumanoidModel<?> armorModel = new HumanoidModel<>(new ArmorRenderer<>(SonicArmorModel::createBodyLayer, SonicArmorModel::new).makeArmorParts(slot));
                                   armorModel.crouching = living.isShiftKeyDown();
                                   armorModel.riding = original.riding;
                                   armorModel.young = living.isBaby();
                                   return armorModel;
                               }
                           },
                ModItems.BASEFORM_CHESTPLATE,
                ModItems.BASEFORM_LIGHTSPEED_CHESTPLATE
        );
    }
}
