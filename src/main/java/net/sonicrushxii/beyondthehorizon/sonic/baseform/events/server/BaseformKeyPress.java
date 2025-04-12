package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;
import net.sonicrushxii.beyondthehorizon.modded.ModSounds;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import org.joml.Vector3f;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class BaseformKeyPress
{
    public static void handleDoubleJump(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ServerLevel world = player.serverLevel();

        //Check if Condition is right to Jump
        if(!baseformProperties.state.getState(BaseformState.CONSUMED_DOUBLE_JUMP.ordinal()) && !player.onGround())
        {

            //Clear State
            baseformProperties.state.setState(BaseformState.CONSUMED_DOUBLE_JUMP.ordinal());

            //Thrust
            player.jumpFromGround();
            player.addDeltaMovement(new Vec3(0,0.135,0));
            player.connection.send(new ClientboundSetEntityMotionPacket(player));

            //Particle
            world.sendParticles(new DustParticleOptions(new Vector3f(0.000f,0.969f,1.000f), 1.5f),
                    player.getX(),player.getY(),player.getZ(),20,
                    0.15f,0.01f,0.15f,0.001);

            //PlaySound
            world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.DOUBLE_JUMP.value(), SoundSource.MASTER, 1.0f, 1.0f);

            //Sync Data
            PacketDistributor.sendToPlayer(player,new SyncSonicPacket(playerSonicData.serializeNBT(null)));
        }


    }
}
