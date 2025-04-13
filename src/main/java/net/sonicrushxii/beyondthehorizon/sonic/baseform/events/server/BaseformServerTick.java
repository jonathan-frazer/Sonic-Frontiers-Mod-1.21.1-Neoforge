package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModAttachments;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseParticlePacket;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseSoundPacket;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformAuxiliaryCounters;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;

import java.util.List;
import java.util.Objects;

public class BaseformServerTick {

    public static void handleTick(ServerPlayer player)
    {
        //Checks if Packet should be synced this tick.
        boolean syncPacket = false;

        //Extract Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        int[] auxiliaryCounters = baseformProperties.auxiliaryCounters;
        ByteStateHolder baseformState = baseformProperties.state;

        //Passives
        {
            //Double Jump
            {
                //Clear Flag
                if (!baseformState.getState(BaseformState.HAS_DOUBLE_JUMP.ordinal()) && player.onGround()) {
                    baseformState.setState(BaseformState.HAS_DOUBLE_JUMP.ordinal());
                    syncPacket = true;
                }
            }

            //Fall Damage Handler
            {}

            //Sonic Stamina
            if(player.getFoodData().getFoodLevel() < 8)
            {
                //Saturation
                if(!player.hasEffect(MobEffects.SATURATION)) player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 1, 0, false, false));
                else Objects.requireNonNull(player.getEffect(MobEffects.SATURATION)).update(new MobEffectInstance(MobEffects.SATURATION, 1, 0, false, false));
            }

            //Danger Sense
            if(baseformState.getState(BaseformState.DANGER_SENSE_ACTIVE.ordinal()))
            {
                Level world = player.level();
                List<Mob> mobList = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(10.0), (entity) -> {
                    try {
                        return Objects.requireNonNull(entity.getTarget()).getUUID().equals(player.getUUID());
                    }catch (NullPointerException ignored)
                    {
                        return false;
                    }
                });

                //Increment Timer
                if(auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] > 0) {
                    ++auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()];
                    syncPacket = true;
                }

                //If a Mob is found, Play sound
                if(!mobList.isEmpty() &&
                        auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] == 0)
                {
                    auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] = 1;
                    syncPacket = true;
                    //Send Client Sound
                    PacketDistributor.sendToPlayer(player, new DangerSenseSoundPacket(true));
                }

                //If a Mob is not found, and Sound is Playing. Cancel Sound
                if(mobList.isEmpty() && auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] > 0)
                {
                    auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] = 0;
                    syncPacket = true;
                    //Stop Client Sound
                    PacketDistributor.sendToPlayer(player, new DangerSenseSoundPacket(false));
                }

                //If Timer is run out Cancel Sound.
                if(auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] > 175)
                {
                    auxiliaryCounters[BaseformAuxiliaryCounters.DANGER_SENSE_SOUND_TIMER.ordinal()] = 0;
                    syncPacket = true;
                    //Stop Client Sound
                    PacketDistributor.sendToPlayer(player, new DangerSenseSoundPacket(false));
                }
            }
        }

        //End, Check if Packet needs to be synced
        if(syncPacket) PacketDistributor.sendToPlayer(player,new SyncSonicPacket(playerSonicData.serializeNBT(null)));
    }

    public static void handleSecond(ServerPlayer player)
    {
        //Extract Data
        PlayerSonicData playerSonicData = player.getData(ModAttachments.SONIC_DATA);
        BaseformAttachmentData baseformProperties = (BaseformAttachmentData) playerSonicData.properties;
        int[] auxiliaryCounters = baseformProperties.auxiliaryCounters;
        ByteStateHolder baseformState = baseformProperties.state;

        //Cooldowns
        {
            byte[] slotAbilityCooldowns = baseformProperties.slotAbilityCooldowns;
            for (int i = 0; i < slotAbilityCooldowns.length; i++) {
                if (slotAbilityCooldowns[i] > 0)
                    --slotAbilityCooldowns[i];
            }
        }

        //Passives
        {
            //Double Jump
            {}

            //Effects
            {
                //Speed
                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.5);

                //Jump
                if(!player.hasEffect(MobEffects.JUMP)) player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 2, false, false));
                else Objects.requireNonNull(player.getEffect(MobEffects.JUMP)).update(new MobEffectInstance(MobEffects.JUMP, -1, 2, false, false));

                //Resistance
                if(!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 3, false, false));
                else Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_RESISTANCE)).update(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 3, false, false));

                //Strength
                if(!player.hasEffect(MobEffects.DAMAGE_BOOST)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));
                else Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).update(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));

                //Haste
                if(!player.hasEffect(MobEffects.DIG_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));
                else Objects.requireNonNull(player.getEffect(MobEffects.DIG_SPEED)).update(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));

                //Immunities: Slowdown
                if(player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);

                //Immunities: Mining Fatigue
                if(player.hasEffect(MobEffects.DIG_SLOWDOWN))      player.removeEffect(MobEffects.DIG_SPEED);
            }

            //Sonic Stamina
            {}

            //Danger Sense
            if(baseformState.getState(BaseformState.DANGER_SENSE_ACTIVE.ordinal()))
            {
                Level world = player.level();
                List<Mob> mobList = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(10.0), (entity) -> {
                    try {
                        return Objects.requireNonNull(entity.getTarget()).getUUID().equals(player.getUUID());
                    }catch (NullPointerException ignored)
                    {
                        return false;
                    }
                });

                //Draw Particles for every threat
                for(Mob threat : mobList)
                {
                    // Define the vector and position
                    Vec3 vector = player.getLookAngle();
                    Vec3 position = threat.position().subtract(player.position());

                    vector.subtract(0, vector.y(), 0);
                    position.subtract(0, position.y(), 0);

                    // Calculate the vector to the position
                    Vec3 vectorToPosition = position.subtract(vector);
                    Vec3 crossProduct = vectorToPosition.cross(position);

                    Vec3 playerPos = (crossProduct.y() > 0) //If Mob is on the Right
                            ? player.getLookAngle() //Gets where player is looking
                            .cross(new Vec3(0, 1, 0)).scale(0.15) //It shifts it to the right
                            .add(player.position().add(player.getLookAngle().scale(0.85))) //Adds Players current position, offset ^ ^ ^0.5
                            .add(0, 1.5, 0) //Offsets that by Up By a Bit
                            : player.getLookAngle() //Gets where player is looking
                            .cross(new Vec3(0, 1, 0)).scale(-0.15) //It shifts it to the left
                            .add(player.position().add(player.getLookAngle().scale(0.85))) //Adds Players current position, offset ^ ^ ^0.5
                            .add(0, 1.5, 0); //Offsets that by Up By a Bit

                    //Send Particle Packet
                    PacketDistributor.sendToPlayer(player,new DangerSenseParticlePacket(playerPos.toVector3f(), threat.position().toVector3f()));
                }
            }
        }
    }
}
