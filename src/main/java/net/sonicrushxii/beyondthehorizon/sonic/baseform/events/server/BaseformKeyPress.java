package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.KeyBindings;
import net.sonicrushxii.beyondthehorizon.ModUtils;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.modded.ModSounds;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformAuxiliaryCounters;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;
import org.joml.Vector3f;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class BaseformKeyPress
{

    public static void baseformKeyPressHandler(ServerPlayer player, int keyCode, int virtualSlotPos, String override)
    {
        //Flag to Handle Special Situations(Very very rare)
        switch(override)
        {
            //Wall boost has no specific Key to Trigger but does need a Packet
            case "wallBoost": handleWallBoost(player); return;
            default: break;
        }

        //Extract Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        int[] auxiliaryCounters = baseformProperties.auxiliaryCounters;
        ByteStateHolder baseformState = baseformProperties.state;

        //Double Jump
        if(keyCode == KeyBindings.DOUBLE_JUMP.getKey().getValue())
            handleDoubleJump(player);

        //Danger Sense Toggle
        else if(keyCode == KeyBindings.TOGGLE_DANGER_SENSE.getKey().getValue())
            handleToggleDangerSense(player);

        //Boost Slot
        else if(keyCode == KeyBindings.USE_ABILITY_1.getKey().getValue() && virtualSlotPos == 0)
        {
            //Land Boost
            if(player.onGround()) handleBoostLevel(player);
            //Air Boost
            else handleAirBoost(player);
        }
    }

    public static void handleDoubleJump(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ServerLevel world = player.serverLevel();

        //Check if Condition is right to Jump
        if(baseformProperties.state.getState(BaseformState.HAS_DOUBLE_JUMP.ordinal()) && !player.onGround())
        {
            //Clear State
            baseformProperties.state.clearState(BaseformState.HAS_DOUBLE_JUMP.ordinal());
            //Aerial State
            baseformProperties.state.setState(BaseformState.BALL_FORM_AERIAL.ordinal());

            //Thrust
            player.jumpFromGround();
            player.addDeltaMovement(new Vec3(0, 0.135, 0));
            player.connection.send(new ClientboundSetEntityMotionPacket(player));

            //Particle
            world.sendParticles(new DustParticleOptions(new Vector3f(0.000f, 0.969f, 1.000f), 1.5f),
                    player.getX(), player.getY(), player.getZ(), 20,
                    0.15f, 0.01f, 0.15f, 0.001);

            //PlaySound
            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.DOUBLE_JUMP.value(), SoundSource.MASTER, 1.0f, 1.0f);

            //Sync Data
            PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
        }
    }

    public static void handleToggleDangerSense(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.state;

        //Toggle
        if(baseformState.getState(BaseformState.DANGER_SENSE_ACTIVE.ordinal()))
            baseformState.clearState(BaseformState.DANGER_SENSE_ACTIVE.ordinal());
        else
            baseformState.setState(BaseformState.DANGER_SENSE_ACTIVE.ordinal());


        //Reset Timer
        baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] = 0;

        //Sync Data
        PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
    }


    public static void handleBoostLevel(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        int boostLvl = baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.BOOST_LV_COUNTER.ordinal()];

        //Increment Values
        if(!player.isShiftKeyDown()) boostLvl = (byte)((boostLvl+1) % 4);
        else                        boostLvl = (byte)((boostLvl==0)?3:boostLvl-1);

        //Boost Level 3
        if(boostLvl == 3 && !player.isSprinting()) baseformProperties.state.clearState(BaseformState.BOOST_START_EFFECT.ordinal());

        if(player.isSprinting())
            switch(boostLvl)
            {
                case 0 : player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                    break;
                case 1 : player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.75);
                    break;
                case 2 : player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.00);
                    break;
                case 3 : ServerLevel world = player.serverLevel();
                        world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.MAX_BOOST.value(), SoundSource.MASTER, 1.0f, 1.0f);
                        world.sendParticles(ParticleTypes.FLASH,
                                player.getX(), player.getY()+1.0, player.getZ(), 1,
                                0.01f, 0.01f, 0.01f, 0.001);
                        world.sendParticles(ParticleTypes.SONIC_BOOM,
                                player.getX(), player.getY()+1.0, player.getZ(), 1,
                                0.01f, 0.01f, 0.01f, 0.001);
                        baseformProperties.state.setState(BaseformState.BOOST_START_EFFECT.ordinal());
                        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.25);
                        break;
            }

        //Sync Data
        baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.BOOST_LV_COUNTER.ordinal()] = boostLvl;
        PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
    }

    public static void handleAirBoost(ServerPlayer player)
    {
        PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        ByteStateHolder baseformState = baseformProperties.state;
        int airBoostsRemaining = baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.AIR_BOOST_COUNTER.ordinal()];
        boolean syncPacket = false;

        if(airBoostsRemaining > 0)
        {
            syncPacket=true;
            airBoostsRemaining -= 1;

            //PlaySound
            ServerLevel world = player.serverLevel();
            world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.AIR_BOOST.value(), SoundSource.MASTER, 1.0f, 1.0f);

            //Particle
            Vec3 playerPosition = player.getPosition(0).add(new Vec3(0,0.75,0));
            ModUtils.particleRaycast(world,
                    new DustParticleOptions(new Vector3f(0.000f,0.000f,1.000f), 2.0f),
                    playerPosition,
                    playerPosition.add(player.getLookAngle().scale(7*player.getAttribute(Attributes.MOVEMENT_SPEED).getValue()))
            );
            world.sendParticles(ParticleTypes.SONIC_BOOM,
                    player.getX(), player.getY()+1.0, player.getZ(), 1,
                    0.0f, 0.0f, 0.01f, 0.0);

            //Ball Form
            baseformState.setState(BaseformState.BALL_FORM_AERIAL.ordinal());

            //Add Trajectory
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
            player.addDeltaMovement(player.getLookAngle().scale(2*player.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }

        //Sync Data
        if(syncPacket){
            baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.AIR_BOOST_COUNTER.ordinal()] = airBoostsRemaining;
            PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
        }
    }

    public static void handleWallBoost(ServerPlayer player)
    {
        //Get Server Player
        ServerLevel world = player.serverLevel();

        //Extract Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        int[] auxiliaryCounters = baseformProperties.auxiliaryCounters;
        ByteStateHolder baseformState = baseformProperties.state;
        int boostLvl = auxiliaryCounters[BaseformAuxiliaryCounters.BOOST_LV_COUNTER.ordinal()];

        //Check surroundings and perform Wall boost
        Vec3 playerDirCentre = ModUtils.calculateViewVector(0.0f, player.getViewYRot(0)).scale(0.75);
        BlockPos centrePos = player.blockPosition().offset(
                (int) Math.round(playerDirCentre.x),
                (Math.round(player.getY()) > player.getY()) ? 1 : 0,
                (int) Math.round(playerDirCentre.z)
        );

        //Perform Wall Boost
        if(!ModUtils.passableBlocks.contains(world.getBlockState(centrePos.offset(0, 1, 0)).getBlock().asItem().toString()))
        {
            //Particle
            if (player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR)
                switch (boostLvl) {
                    case 1:
                        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                player.getX(), player.getY() + 0.05, player.getZ(), 1,
                                0.00f, 0.00f, 0.00f, 0.001);
                        break;
                    case 2:
                        world.sendParticles(new DustParticleOptions(new Vector3f(1.0f,0.0f,0.0f), 2f),
                                player.getX(), player.getY() + 0.35, player.getZ(), 12,
                                0.05f, 0.05f, 0.05f, 0.001);
                        break;
                    case 3:
                        world.sendParticles(new DustParticleOptions(new Vector3f(0.0f, 0.89f, 1.00f), 1),
                                player.getX(), player.getY() + 1.0, player.getZ(), 12,
                                0.05f, 0.20f, 0.05f, 0.001);
                        break;
                    default:
                }

            //Motion
            player.setDeltaMovement(new Vec3(0, player.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 2.5, 0));
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }
}
