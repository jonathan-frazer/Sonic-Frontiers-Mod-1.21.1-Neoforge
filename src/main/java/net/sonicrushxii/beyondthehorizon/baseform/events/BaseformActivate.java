package net.sonicrushxii.beyondthehorizon.baseform.events;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformAttributeModifiers;
import net.sonicrushxii.beyondthehorizon.baseform.data.BaseformItemData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.modded.ModItems;

import java.util.Objects;

public class BaseformActivate
{
    public static void onBaseformActivate(ServerPlayer player)
    {
        //Equip Armor
        //SET ARMOR NBT DATA(COMMON)
        {
            //Refresh Head
            player.setItemSlot(EquipmentSlot.HEAD, BaseformItemData.baseformSonicHead);

            if (player.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_BOOTS.get());
                itemToPlace.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
                player.setItemSlot(EquipmentSlot.FEET, itemToPlace);
            }
            if (player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_LEGGINGS.get());
                itemToPlace.set(DataComponents.CUSTOM_DATA, CustomData.of(BaseformItemData.baseformArmorNBTTag));
                player.setItemSlot(EquipmentSlot.LEGS, itemToPlace);
            }
            if (player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                ItemStack itemToPlace = new ItemStack(ModItems.BASEFORM_CHESTPLATE.get());
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

        //Initialize Data
        playerSonicData.isSonic = true;
        playerSonicData.properties = new BaseformAttachmentData();
    }
}
