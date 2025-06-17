package net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.sonicrushxii.beyondthehorizon.modded.ModDamageTypes;
import net.sonicrushxii.beyondthehorizon.modded.ModEffects;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.BaseformAuxiliaryCounters;
import org.joml.Vector3f;

public class BaseformDamageHandler
{
    public static boolean takeDamage(LivingDamageEvent.Pre event, BaseformAttachmentData baseformProperties)
    {
        boolean syncPacket = false;
        ServerPlayer damageTaker = (ServerPlayer)event.getEntity();
        LivingEntity damageGiver = (LivingEntity) event.getSource().getEntity();

        //End, Check if Packet needs to be synced
        return syncPacket;
    }

    public static int DAMAGE_GRANULARITY = 100;
    public static boolean dealDamage(LivingDamageEvent.Pre event, BaseformAttachmentData baseformProperties)
    {
        boolean syncPacket = false;
        ServerPlayer damageGiver = (ServerPlayer)event.getSource().getEntity();
        ServerLevel world = damageGiver.serverLevel();
        LivingEntity damageTaker = event.getEntity();
        int[] auxiliaryCounters = baseformProperties.auxiliaryCounters;

        final int COMBO_TIME = 40;

        //HurtTime Check Type Damage
        try{
            if(damageTaker.hurtTime != 0)
                throw new NullPointerException("Mob is already being hurt");

            //Melee Attack
            if(event.getSource().is(DamageTypes.PLAYER_ATTACK))
            {
                //Increment Hit Count
                baseformProperties.auxiliaryCounters[BaseformAuxiliaryCounters.COMBO_HIT_COUNT.ordinal()] += DAMAGE_GRANULARITY;
                syncPacket = true;

                //If in the Air Sustain yourself
                if (!damageGiver.onGround())
                {
                    damageGiver.setDeltaMovement(new Vec3(damageGiver.getDeltaMovement().x,0.0,damageGiver.getDeltaMovement().z));
                    damageGiver.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 12, 10, false, false));
                    damageGiver.connection.send(new ClientboundSetEntityMotionPacket(damageGiver));
                }

                if (auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_HIT_COUNT.ordinal()] == 2)
                {
                    world.playSound(null, damageGiver.getX(), damageGiver.getY(), damageGiver.getZ(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.MASTER, 1.0f, 1.0f);
                    world.sendParticles(new DustParticleOptions(new Vector3f(0.000f, 0.0f, 1.000f), 1.5f),
                            damageGiver.getX(), damageGiver.getY()+damageGiver.getEyeHeight()/2, damageGiver.getZ(), 20,
                            0.15f, 0.01f, 0.15f, 0.001);
                }
                if (auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_HIT_COUNT.ordinal()] == 3)
                {
                    if (damageGiver.isShiftKeyDown()||damageGiver.getXRot() < 0) {
                        damageGiver.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 10, false, false));
                        damageTaker.addEffect(new MobEffectInstance(ModEffects.COMBO_EFFECT, 40, 0, false, false));
                        damageTaker.moveTo(damageTaker.getX(), damageTaker.getY() + 5.0, damageTaker.getZ());
                    }
                    else if(damageGiver.getXRot() > 0)
                    {
                        //Sonic Eagle
                        damageGiver.displayClientMessage(Component.translatable("Sonic Eagle").withStyle(Style.EMPTY.withColor(0xFFA500)),true);
                        world.sendParticles(ParticleTypes.FLAME,
                                damageTaker.getX(), damageTaker.getY()+damageTaker.getEyeHeight(), damageTaker.getZ(), 20,
                                0.15f, 0.01f, 0.15f, 0.001);
                        //Sound Effect
                        damageGiver.level().playSound(null,damageGiver.getX(),damageGiver.getY(),damageGiver.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.MASTER, 1.0f, 1.0f);

                        damageGiver.removeEffect(MobEffects.SLOW_FALLING);
                        damageTaker.removeEffect(ModEffects.COMBO_EFFECT);
                        damageTaker.hurt(ModDamageTypes.getDamageSource(damageGiver.level(),ModDamageTypes.SONIC_MELEE.getResourceKey(),damageGiver), 6.0f);
                        damageTaker.addDeltaMovement(new Vec3(0.0, -0.85, 0.0));
                        damageGiver.connection.send(new ClientboundSetEntityMotionPacket(damageTaker));
                    }
                }
                //Increase Count
                auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_HIT_COUNT.ordinal()] = (auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_HIT_COUNT.ordinal()] + 1) % 5;

                //Timer
                auxiliaryCounters[BaseformAuxiliaryCounters.MELEE_CLEAR_TIMER.ordinal()] = COMBO_TIME;
            }

        }catch(NullPointerException | ClassCastException ignored){}

        return syncPacket;
    }
}
