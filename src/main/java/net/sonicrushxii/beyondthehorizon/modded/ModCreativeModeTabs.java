package net.sonicrushxii.beyondthehorizon.modded;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformItemData;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondTheHorizon.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SONIC_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable(BeyondTheHorizon.MOD_ID+".creative_mode_tab")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BaseformItemData.baseformSonicHead)
            .displayItems((parameters, output) -> {
                output.accept(BaseformItemData.baseformSonicHead);
                output.accept(BaseformItemData.baseformLSSonicHead);
                output.accept(BaseformItemData.baseformPBSonicHead);

                output.accept(new ItemStack(ModItems.BASEFORM_CHESTPLATE.get()));
                output.accept(new ItemStack(ModItems.BASEFORM_LEGGINGS.get()));
                output.accept(new ItemStack(ModItems.BASEFORM_BOOTS.get()));

                output.accept(new ItemStack(ModItems.BASEFORM_LIGHTSPEED_CHESTPLATE.get()));
                output.accept(new ItemStack(ModItems.BASEFORM_LIGHTSPEED_LEGGINGS.get()));
                output.accept(new ItemStack(ModItems.BASEFORM_LIGHTSPEED_BOOTS.get()));
            }).build());

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
