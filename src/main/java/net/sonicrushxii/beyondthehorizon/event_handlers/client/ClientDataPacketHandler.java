package net.sonicrushxii.beyondthehorizon.event_handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sonicrushxii.beyondthehorizon.ModUtils;
import net.sonicrushxii.beyondthehorizon.client.VirtualSlotHandler;
import net.sonicrushxii.beyondthehorizon.modded.ModSounds;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseParticlePacket;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseSoundPacket;
import net.sonicrushxii.beyondthehorizon.packet.InitializeVirtualSlotPacket;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class ClientDataPacketHandler
{
    public static void handleSyncSonicData(final SyncSonicPacket data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if (context.flow().isServerbound()) return;

        //Get Client Player
        AbstractClientPlayer clientPlayer = Minecraft.getInstance().player;

        //Inject new Data into Client
        assert clientPlayer != null;
        clientPlayer.getData(SONIC_DATA).deserializeNBT(null,data.formDetails());
    }

    public static void handleVirtualSlotInitialization(final InitializeVirtualSlotPacket data, final IPayloadContext context) {
        if (context.flow().isServerbound()) return;

        VirtualSlotHandler.initialize(data.noOfSlots());
    }

    public static void handleDangerSenseSound(final DangerSenseSoundPacket data, final IPayloadContext context)
    {
        if (context.flow().isServerbound()) return;

        // This code is run on the client side
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        AbstractClientPlayer player = mc.player;

        if(player == null || world == null) return;

        //Emit Sound
        if(data.activate()) {
            world.playLocalSound(player.getX(), player.getY(), player.getZ(),
                    ModSounds.DANGER_SENSE.value(), SoundSource.HOSTILE, 1.0f, 1.0f, true);
        }
        //Inhibit Sound
        else {
            mc.getSoundManager().stop(ModSounds.DANGER_SENSE.value().getLocation(), SoundSource.HOSTILE);
        }
    }

    public static void handleDangerSenseParticle(final DangerSenseParticlePacket data, final IPayloadContext context)
    {
        if (context.flow().isServerbound()) return;

        Vec3 initialPos = new Vec3(data.initialPos().x, data.initialPos().y, data.initialPos().z);
        Vec3 destinationPos = new Vec3(data.destinationPos().x, data.destinationPos().y+1.0, data.destinationPos().z);
        ModUtils.particleRaycast(Minecraft.getInstance().level, ParticleTypes.ELECTRIC_SPARK, initialPos,destinationPos);
    }
}
