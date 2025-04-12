package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;
import net.sonicrushxii.beyondthehorizon.modded.armor.client.model.SonicArmorModel;
import net.sonicrushxii.beyondthehorizon.modded.armor.client.renderer.ArmorRenderer;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BeyondTheHorizon.MOD_ID, value = Dist.CLIENT)
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
