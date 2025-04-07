package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CraftHandlers
{
    //Crafting for the Head
    private static final byte BEACON_COUNT = 1;
    private static final byte SPEED_II_POTION_COUNT = 1;
    private static final byte AMETHYST_SHARD_COUNT = 7;
    public static final byte BLUE_ICE_COUNT = 15;
    private static final byte LAPIS_BLOCK_COUNT = 30;

    public static boolean tryCraftSonicHead(Level world, Vec3 pos)
    {
        AABB boundingBox = new AABB(pos.x - 0.75, pos.y - 0.75, pos.z - 0.75,
                                    pos.x + 0.75, pos.y + 0.75, pos.z + 0.75);

        //Fields for the items
        ItemEntity beaconEntity = null;
        ItemEntity speedIIPotionEntity = null;
        ItemEntity amethystShardEntity = null;
        ItemEntity lapisBlockEntity = null;

        //Loop through neighbouring items
        for(ItemEntity itemEntity : world.getEntitiesOfClass(ItemEntity.class, boundingBox))
        {
            ItemStack itemStack = itemEntity.getItem();

            // Check if beacon is there
            if(beaconEntity == null && itemStack.getItem() == Items.BEACON &&
                    itemStack.getCount() == BEACON_COUNT) {
                beaconEntity = itemEntity;
            }

            // Check if amethyst shard is there
            else if(amethystShardEntity == null && itemStack.getItem() == Items.AMETHYST_SHARD &&
                    itemStack.getCount() == AMETHYST_SHARD_COUNT) {
                amethystShardEntity = itemEntity;
            }

            // Check if lapis block is there
            else if(lapisBlockEntity == null && itemStack.getItem() == Items.LAPIS_BLOCK &&
                    itemStack.getCount() == LAPIS_BLOCK_COUNT) {
                lapisBlockEntity = itemEntity;
            }

            //Check if Speed Potion is there
            else if(speedIIPotionEntity == null && (itemStack.getItem() == Items.POTION || itemStack.getItem() == Items.SPLASH_POTION || itemStack.getItem() == Items.LINGERING_POTION) &&
                itemStack.getCount() == SPEED_II_POTION_COUNT)
            {
                try {
                    if(itemStack.get(DataComponents.POTION_CONTENTS) == null) throw new NullPointerException();
                    if(itemStack.get(DataComponents.POTION_CONTENTS).is(Potions.STRONG_SWIFTNESS))
                        speedIIPotionEntity = itemEntity;
                }catch (NullPointerException ignore){}
            }

            //If all items are found break out the loop
            if(beaconEntity != null && speedIIPotionEntity != null && amethystShardEntity != null && lapisBlockEntity != null) break;
        }


        //If Items are not found Return false
        if(beaconEntity == null || speedIIPotionEntity == null || amethystShardEntity == null || lapisBlockEntity == null)
            return false;

        //Consume Items
        beaconEntity.remove(RemovalReason.DISCARDED);
        speedIIPotionEntity.remove(RemovalReason.DISCARDED);
        amethystShardEntity.remove(RemovalReason.DISCARDED);
        lapisBlockEntity.remove(RemovalReason.DISCARDED);

        //Claim that the Crafting was successful
        return true;
    }
}
