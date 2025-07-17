package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;
import net.sonicrushxii.beyondthehorizon.packet.InitializeVirtualSlotPacket;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttributeModifiers;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformItemData;

import java.util.Objects;

public class BaseformActivate
{
    public static int NO_OF_AIR_BOOSTS = 3;
    private static final byte NO_OF_SLOTS = (byte)6;

    public static void onBaseformActivate(ServerPlayer player)
    {
        //Equip Armor
        //SET ARMOR NBT DATA(COMMON)
        {
            //Refresh Head
            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headItem.getItem() == Items.PLAYER_HEAD) {
                //Double Check for the Correct Head
                CompoundTag customData = Objects.requireNonNull(headItem.get(DataComponents.CUSTOM_DATA)).copyTag();
                if (customData.contains("BeyondTheHorizon") && customData.getByte("BeyondTheHorizon") == 2)
                {
                    //Update Profile
                    headItem.set(DataComponents.PROFILE, BaseformItemData.SONIC_BASEHEAD_PROFILE);
                    //Update Custom Name
                    headItem.set(DataComponents.CUSTOM_NAME, BaseformItemData.SONIC_BASEHEAD_NAME);
                    //Update Custom Lore
                    headItem.set(DataComponents.LORE, BaseformItemData.SONIC_BASEHEAD_LORE);

                }
            }

            RegistryAccess registryAccess = player.level().registryAccess();
            Registry<Enchantment> enchantmentRegistry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> binding_curse = enchantmentRegistry.getHolderOrThrow(Enchantments.BINDING_CURSE);


            if (player.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_BOOTS.get());
                itemToPlace.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
                itemToPlace.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
                itemToPlace.enchant(binding_curse,1);
                itemToPlace.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
                player.setItemSlot(EquipmentSlot.FEET, itemToPlace);
            }
            if (player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_LEGGINGS.get());
                itemToPlace.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
                itemToPlace.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
                itemToPlace.enchant(binding_curse,1);
                itemToPlace.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
                player.setItemSlot(EquipmentSlot.LEGS, itemToPlace);
            }
            if (player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_CHESTPLATE.get());
                itemToPlace.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
                itemToPlace.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
                itemToPlace.enchant(binding_curse,1);
                itemToPlace.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
                player.setItemSlot(EquipmentSlot.CHEST, itemToPlace);
            }
        }

        //Effects
        {
            //Speed
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.5);

            //Step Height
            if (!Objects.requireNonNull(player.getAttribute(Attributes.STEP_HEIGHT)).hasModifier(BaseformAttributeModifiers.STEP_UP_BASE.id()))
                Objects.requireNonNull(player.getAttribute(Attributes.STEP_HEIGHT)).addTransientModifier(BaseformAttributeModifiers.STEP_UP_BASE);

            //Jump
            if(!player.hasEffect(MobEffects.JUMP)) player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 2, false, false));
            else Objects.requireNonNull(player.getEffect(MobEffects.JUMP)).update(new MobEffectInstance(MobEffects.JUMP, -1, 2, false, false));

            //Jump
            if(!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 3, false, false));
            else Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_RESISTANCE)).update(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 3, false, false));

            //Strength
            if(!player.hasEffect(MobEffects.DAMAGE_BOOST)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));
            else Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).update(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));

            //Haste
            if(!player.hasEffect(MobEffects.DIG_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));
            else Objects.requireNonNull(player.getEffect(MobEffects.DIG_SPEED)).update(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));
        }

        //Extract Players Sonic Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        playerSonicData.properties = new BaseformAttachmentData();

        //Initialize the Virtual Slot Handler
        PacketDistributor.sendToPlayer(player,new InitializeVirtualSlotPacket(NO_OF_SLOTS));
        //Synchronize with Client
        PacketDistributor.sendToPlayer(player,new SyncSonicPacket(playerSonicData.serializeNBT(null)));
    }
}
