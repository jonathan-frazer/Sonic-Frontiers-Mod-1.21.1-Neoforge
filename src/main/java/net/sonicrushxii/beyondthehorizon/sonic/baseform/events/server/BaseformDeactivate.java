package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.packet.InitializeVirtualSlotPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttributeModifiers;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformItemData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;

import java.util.Iterator;
import java.util.Objects;

public class BaseformDeactivate
{
    public static void onBaseformDeactivate(ServerPlayer player)
    {
        //Remove Armor
        {
            //Binding Curse
            RegistryAccess registryAccess = player.level().registryAccess();
            Registry<Enchantment> enchantmentRegistry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> binding_curse = enchantmentRegistry.getHolderOrThrow(Enchantments.BINDING_CURSE);

            //Get Armor Items
            Iterator<ItemStack> armorItems = player.getArmorSlots().iterator();

            //Delete Boots
            ItemStack itemToRemove = new ItemStack(ModItems.BASEFORM_BOOTS.get());
            itemToRemove.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
            itemToRemove.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
            itemToRemove.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
            itemToRemove.enchant(binding_curse,1);
            if(ItemStack.isSameItemSameComponents(armorItems.next(),itemToRemove))
                player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);

            //Delete Leggings
            itemToRemove = new ItemStack(ModItems.BASEFORM_LEGGINGS.get());
            itemToRemove.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
            itemToRemove.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
            itemToRemove.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
            itemToRemove.enchant(binding_curse,1);
            if(ItemStack.isSameItemSameComponents(armorItems.next(),itemToRemove))
                player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);

            //Delete Chestplate
            itemToRemove = new ItemStack(ModItems.BASEFORM_CHESTPLATE.get());
            itemToRemove.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
            itemToRemove.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
            itemToRemove.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
            itemToRemove.enchant(binding_curse,1);
            if(ItemStack.isSameItemSameComponents(armorItems.next(),itemToRemove))
                player.setItemSlot(EquipmentSlot.CHEST,ItemStack.EMPTY);
        }

        //Remove Effects
        if (Objects.requireNonNull(player.getAttribute(Attributes.STEP_HEIGHT)).hasModifier(BaseformAttributeModifiers.STEP_UP_BASE.id()))
            Objects.requireNonNull(player.getAttribute(Attributes.STEP_HEIGHT)).removeModifier(BaseformAttributeModifiers.STEP_UP_BASE.id());
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.10000000149011612);
        player.removeEffect(MobEffects.JUMP);
        player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        player.removeEffect(MobEffects.DAMAGE_BOOST);
        player.removeEffect(MobEffects.DIG_SPEED);
        player.removeEffect(MobEffects.ABSORPTION);

        //Extract Players Sonic Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        playerSonicData.properties = new AttachmentData();

        //Scrap Virtual Slot Handler
        PacketDistributor.sendToPlayer(player,new InitializeVirtualSlotPacket((byte)0));
        //Synchronize with Client
        PacketDistributor.sendToPlayer(player,new SyncSonicPacket(playerSonicData.serializeNBT(null)));
    }
}
