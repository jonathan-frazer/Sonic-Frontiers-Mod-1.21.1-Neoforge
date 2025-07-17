package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.sonicrushxii.beyondthehorizon.modded.ModSounds;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseParticlePacket;
import net.sonicrushxii.beyondthehorizon.packet.DangerSenseSoundPacket;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformAuxiliaryCounters;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformState;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;

public class BaseformServerTick {

    public static void handleTick(ServerPlayer player)
    {
        //Checks if Packet should be synced this tick.
        boolean syncPacket = false;
        ServerLevel world = player.serverLevel();

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

            //Step Down
            if(!player.onGround() && player.isSprinting()
                    && baseformState.getState(BaseformState.GROUND_TRACTION.ordinal())
                    && !baseformState.getState(BaseformState.WATER_BOOSTING.ordinal()))
            {
                baseformState.clearState(BaseformState.GROUND_TRACTION.ordinal());
                if (player.getDeltaMovement().y > -0.38 && player.getDeltaMovement().y < 0.05)
                {
                    player.setDeltaMovement(player.getDeltaMovement().x,-0.40,player.getDeltaMovement().z);
                    player.connection.send(new ClientboundSetEntityMotionPacket(player));
                }
                syncPacket = true;
            }
            if(player.onGround()) {
                baseformState.setState(BaseformState.GROUND_TRACTION.ordinal());
                syncPacket = true;
            }

            //Combo Meter
            {
                //Attach Combo meter
                if(auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_HIT_COUNT.ordinal()] > 0 && player.onGround()) {
                    baseformProperties.comboPointDisplay = auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_HIT_COUNT.ordinal()];
                    auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_CLEAR_TIMER.ordinal()] = 100;
                    auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_HIT_COUNT.ordinal()] = 0;
                    syncPacket = true;
                }

                //Clear Combo from Side of screen
                if(auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_CLEAR_TIMER.ordinal()] > 0)
                {
                    --auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_CLEAR_TIMER.ordinal()];
                    if(auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_CLEAR_TIMER.ordinal()] == 0)
                        baseformProperties.comboPointDisplay = 0;
                    syncPacket = true;
                }
            }

            //Melee Strikes
            {
                //Clear if not attacking for a while
                if(auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_CLEAR_TIMER.ordinal()] > 0)
                {
                    --auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_CLEAR_TIMER.ordinal()];
                    if(auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_CLEAR_TIMER.ordinal()] == 0) {
                        auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_HIT_COUNT.ordinal()] = 0;
                        player.removeEffect(MobEffects.SLOW_FALLING);
                    }
                    syncPacket = true;
                }
            }
        }

        //Active Abilities
        {
            int boostLvl = auxiliaryCounters[BaseformAuxiliaryCounters.BOOST_LV_COUNTER.ordinal()];
            //Boost
            {
                //Air Boost
                {
                    //Handle Counter
                    int airBoostCount = auxiliaryCounters[BaseformAuxiliaryCounters.AIR_BOOST_COUNTER.ordinal()];
                    if(player.onGround())
                    {
                        //Reset Counter
                        if(airBoostCount < BaseformActivate.NO_OF_AIR_BOOSTS) {
                            airBoostCount = BaseformActivate.NO_OF_AIR_BOOSTS;
                            syncPacket = true;
                        }
                        //Reset Ballform
                        if(baseformState.getState(BaseformState.BALL_FORM_AERIAL.ordinal())) {
                            baseformState.clearState(BaseformState.BALL_FORM_AERIAL.ordinal());
                            syncPacket = true;
                        }
                    }

                    auxiliaryCounters[BaseformAuxiliaryCounters.AIR_BOOST_COUNTER.ordinal()] = airBoostCount;
                }

                //Land Boost
                {
                    //Start Sprinting
                    if(player.isSprinting() && !baseformState.getState(BaseformState.SPRINTING.ordinal()))
                    {
                        switch (boostLvl) {
                            case 0:
                                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                                break;
                            case 1:
                                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.75);
                                break;
                            case 2:
                                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.00);
                                break;
                            case 3:
                                if (!baseformState.getState(BaseformState.BOOST_START_EFFECT.ordinal())) {
                                    world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.MAX_BOOST.value(), SoundSource.MASTER, 1.0f, 1.0f);
                                    world.sendParticles(ParticleTypes.FLASH,
                                            player.getX(), player.getY() + 1.0, player.getZ(), 1,
                                            0.01f, 0.01f, 0.01f, 0.001);
                                    world.sendParticles(ParticleTypes.SONIC_BOOM,
                                            player.getX(), player.getY() + 1.0, player.getZ(), 1,
                                            0.01f, 0.01f, 0.01f, 0.001);
                                    baseformState.setState(BaseformState.BOOST_START_EFFECT.ordinal());
                                    syncPacket = true;
                                }
                                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.25);
                                break;
                        }
                        baseformState.setState(BaseformState.SPRINTING.ordinal());
                        syncPacket = true;
                    }
                    //While Sprinting
                    if(baseformState.getState(BaseformState.SPRINTING.ordinal()))
                    {
                        //Particle Effects
                        switch (boostLvl)
                        {
                            case 1: world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                    player.getX(), player.getY()+0.05, player.getZ(), 1,
                                    0.00f, 0.00f, 0.00f, 0.001);
                                break;
                            case 2:
                                world.sendParticles(new DustParticleOptions(new Vector3f(1.0f,0.0f,0.0f), 2f),
                                        player.getX(), player.getY()+0.35, player.getZ(), 12,
                                        0.05f, 0.05f, 0.05f, 0.001);
                                break;
                            case 3:
                                world.sendParticles(new DustParticleOptions(new Vector3f(0.0f, 0.89f, 1.00f), 1),
                                        player.getX(), player.getY()+1.0, player.getZ(), 12,
                                        0.05f, 0.20f, 0.05f, 0.001);
                                break;
                            default:
                        }
                    }
                    //Stop Sprinting
                    if(baseformState.getState(BaseformState.SPRINTING.ordinal()) && !player.isSprinting())
                    {
                        baseformState.clearState(BaseformState.SPRINTING.ordinal());
                        syncPacket = true;
                        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                    }
                }

                //Water Boost - I hate this ability, it's the WORST ever
                {

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
                if(Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue() < 0.5)
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
