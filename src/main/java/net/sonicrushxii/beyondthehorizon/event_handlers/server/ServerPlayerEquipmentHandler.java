package net.sonicrushxii.beyondthehorizon.event_handlers.server;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.baseform.events.BaseformActivate;
import net.sonicrushxii.beyondthehorizon.baseform.events.BaseformDeactivate;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;

import java.util.Objects;

public class ServerPlayerEquipmentHandler {
    public static void onEquipmentChange(Player pPlayer, LivingEquipmentChangeEvent event) {
        // Only run on server
        if (!(pPlayer instanceof ServerPlayer player)) return;

        // Only react to helmet slot
        if (event.getSlot() != EquipmentSlot.HEAD) return;

        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);

        tryBaseformTransform(player, playerSonicData);
    }

    private static void tryBaseformTransform(ServerPlayer player, PlayerSonicData playerSonicData) {
        System.out.println("[" + System.currentTimeMillis() + "] ");

        ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);

        // === Check if we're wearing the correct Sonic head ===
        if (!headItem.isEmpty() && headItem.getItem() == Items.PLAYER_HEAD) {
            CompoundTag customData = Objects.requireNonNull(headItem.get(DataComponents.CUSTOM_DATA)).copyTag();

            if (customData.contains("BeyondTheHorizon") && customData.getByte("BeyondTheHorizon") == 2) {
                if (!playerSonicData.isSonic) {
                    System.out.println("Transform");
                    BaseformActivate.onBaseformActivate(player);
                }
                return; // If it's valid Sonic head, don't untransform
            }
        }

        // === If we get here, the player isn't wearing the correct head ===
        if (playerSonicData.isSonic) {
            System.out.println("Un-Transform");
            BaseformDeactivate.onBaseformDeactivate(player);
        }
    }

}
