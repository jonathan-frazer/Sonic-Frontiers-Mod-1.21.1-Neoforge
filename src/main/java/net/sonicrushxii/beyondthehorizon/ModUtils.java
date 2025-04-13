package net.sonicrushxii.beyondthehorizon;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class ModUtils
{
    public static Random random = new Random();

    public static void displayParticle(Level world, ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speedX, double speedY, double speedZ,
                                       int count, boolean force)
    {
        for (int i = 0; i < count; i++)
        {
            double x = random.nextGaussian();
            double y = random.nextGaussian();
            double z = random.nextGaussian();

            double nf = Math.sqrt(x*x + y*y + z*z);

            x = (x/nf)*radiusX;
            y = (y/nf)*radiusY;
            z = (z/nf)*radiusZ;

            // Spawn the particle effect
            world.addParticle(particleType, force,
                    x + (absX),
                    y + (absY),
                    z + (absZ),
                    speedX, speedY, speedZ);
        }
    }

    public static void displayParticle(Level world , ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speed,
                                       int count, boolean force)
    {
        for (int i = 0; i < count; i++) {

            double x = random.nextGaussian();
            double y = random.nextGaussian();
            double z = random.nextGaussian();

            double nf = Math.sqrt(x*x + y*y + z*z);

            x = (x/nf)*radiusX;
            y = (y/nf)*radiusY;
            z = (z/nf)*radiusZ;

            // Calculate the particle's initial position
            double particleX = x + (absX);
            double particleY = y + (absY);
            double particleZ = z + (absZ);

            // Calculate the direction vector from the origin to the particle
            double dirX = particleX - x;
            double dirY = particleY - y;
            double dirZ = particleZ - z;

            // Normalize the direction vector
            double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            if (length != 0) {
                dirX /= length;
                dirY /= length;
                dirZ /= length;
            }

            // Scale the direction vector by the desired speed
            double speedX = dirX * speed;
            double speedY = dirY * speed;
            double speedZ = dirZ * speed;

            // Spawn the particle effect
            world.addParticle(particleType, force,
                    particleX, particleY, particleZ,
                    speedX, speedY, speedZ);
        }
    }

    public static void displayParticle(Player player , ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speedX, double speedY, double speedZ,
                                       int count, boolean force) {
        Level world = player.level();
        displayParticle(world,particleType,absX,absY,absZ,
                radiusX,radiusY,radiusZ,
                speedX,speedY,speedZ,
                count,force);

    }

    public static void displayParticle(Player player , ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speed,
                                       int count, boolean force) {
        Level world = player.level();
        displayParticle(world,particleType,
                absX,absY,absZ,
                radiusX,radiusY,radiusZ,
                speed,count,force);

    }

    public static void particleRaycast(Level world, ParticleOptions particleType,
                                       Vec3 pos1, Vec3 pos2)
    {
        // Calculate the vector from pos1 to pos2
        Vec3 direction = pos2.subtract(pos1);

        double distance = direction.length();
        Vec3 directionNormalized = direction.normalize();

        for (int i = 0; i <= (int) distance*2; i++) {
            Vec3 point = pos1.add(directionNormalized.scale((i+1)/2.0));
            world.addParticle(particleType,
                    point.x, point.y, point.z,
                    0, 0, 0);
        }
    }

    public static boolean isMoving(Vec3 playerDeltaMovement, double threshold)
    {
        boolean x = Math.abs(playerDeltaMovement.x()) < threshold;
        boolean y = Math.abs(playerDeltaMovement.y()) < threshold;
        boolean z = Math.abs(playerDeltaMovement.z()) < threshold;

        return !(x && y && z);
    }

    //Assumes Small Lists
    public static boolean playerHasAllItems(Player player, List<String> itemStrings, String modid)
    {
        int itemsNeeded = itemStrings.size();
        int itemsFound = 0;

        // Convert item names into an array for fast lookup
        Item[] requiredItems = new Item[itemsNeeded];

        for (int i = 0; i < itemsNeeded; i++) {
            requiredItems[i] = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(modid, itemStrings.get(i)));
        }

        // Iterate through inventory and count matches
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty()) {
                for (Item item : requiredItems) {
                    if (item != null && stack.getItem() == item) {
                        itemsFound++;
                        if (itemsFound == itemsNeeded) return true; // Early exit
                    }
                }
            }
        }

        // Check offhand separately (optional)
        ItemStack offhandStack = player.getOffhandItem();
        if (!offhandStack.isEmpty()) {
            for (Item item : requiredItems) {
                if (item != null && offhandStack.getItem() == item) {
                    itemsFound++;
                    if (itemsFound == itemsNeeded) return true; // Early exit
                }
            }
        }

        return false; // Not all items were found
    }
}
