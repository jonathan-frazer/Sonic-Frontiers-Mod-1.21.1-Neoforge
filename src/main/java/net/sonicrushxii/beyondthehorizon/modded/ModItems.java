package net.sonicrushxii.beyondthehorizon.modded;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.modded.armor.ModArmorMaterials;

public class ModItems
{
    public static final DeferredRegister.Items ITEMS =  DeferredRegister.createItems(BeyondTheHorizon.MOD_ID);

    public static final DeferredItem<ArmorItem> BASEFORM_CHESTPLATE = ITEMS.register("baseform_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_CHESTPLATE_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(Integer.MAX_VALUE)));
    public static final DeferredItem<ArmorItem> BASEFORM_POWERBOOST_CHESTPLATE = ITEMS.register("baseform_powerboost_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_PB_CHESTPLATE_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(Integer.MAX_VALUE)));
    public static final DeferredItem<ArmorItem> BASEFORM_LEGGINGS = ITEMS.register("baseform_leggings",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_SONIC_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(Integer.MAX_VALUE)));
    public static final DeferredItem<ArmorItem> BASEFORM_BOOTS = ITEMS.register("baseform_boots",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_SONIC_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(Integer.MAX_VALUE)));

    public static final DeferredItem<ArmorItem> BASEFORM_LIGHTSPEED_CHESTPLATE = ITEMS.register("baseform_lightspeed_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_LS_CHESTPLATE_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(Integer.MAX_VALUE)));
    public static final DeferredItem<ArmorItem> BASEFORM_LIGHTSPEED_LEGGINGS = ITEMS.register("baseform_lightspeed_leggings",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_LIGHTSPEED_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(Integer.MAX_VALUE)));
    public static final DeferredItem<ArmorItem> BASEFORM_LIGHTSPEED_BOOTS = ITEMS.register("baseform_lightspeed_boots",
            () -> new ArmorItem(ModArmorMaterials.BASEFORM_LIGHTSPEED_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(Integer.MAX_VALUE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
