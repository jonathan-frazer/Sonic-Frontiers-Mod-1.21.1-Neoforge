package net.sonicrushxii.beyondthehorizon.modded;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BeyondTheHorizon.MOD_ID);

    // All vanilla sounds use variable range events.
    public static final Holder<SoundEvent> AIR_BOOST =
            registerSoundEvent("air_boost");
    public static final Holder<SoundEvent> BLITZ =
            registerSoundEvent("blitz");
    public static final Holder<SoundEvent> CROSS_SLASH =
            registerSoundEvent("cross_slash");
    public static final Holder<SoundEvent> CYLOOP_RINGS =
            registerSoundEvent("cyloop_rings");
    public static final Holder<SoundEvent> CYLOOP =
            registerSoundEvent("cyloop");
    public static final Holder<SoundEvent> CYLOOP_SUCCESS =
            registerSoundEvent("cyloop_success");
    public static final Holder<SoundEvent> DANGER_SENSE =
            registerSoundEvent("danger_sense");
    public static final Holder<SoundEvent> DEPOWER_BOOST =
            registerSoundEvent("depower_boost");
    public static final Holder<SoundEvent> DOUBLE_JUMP =
            registerSoundEvent("double_jump");
    public static final Holder<SoundEvent> GRAND_SLAM =
            registerSoundEvent("grand_slam");
    public static final Holder<SoundEvent> HOMING_ATTACK =
            registerSoundEvent("homing_attack");
    public static final Holder<SoundEvent> HOMING_SHOT =
            registerSoundEvent("homing_shot");
    public static final Holder<SoundEvent> HUMMING_TOP =
            registerSoundEvent("humming_top");
    public static final Holder<SoundEvent> LIGHT_SPEED_CHARGE =
            registerSoundEvent("light_speed_charge");
    public static final Holder<SoundEvent> LIGHT_SPEED_IDLE =
            registerSoundEvent("light_speed_idle");
    public static final Holder<SoundEvent> LOOP_KICK =
            registerSoundEvent("loop_kick");
    public static final Holder<SoundEvent> MAX_BOOST =
            registerSoundEvent("max_boost");
    public static final Holder<SoundEvent> MIRAGE =
            registerSoundEvent("mirage");
    public static final Holder<SoundEvent> PARRY =
            registerSoundEvent("parry");
    public static final Holder<SoundEvent> POWER_BOOST_IDLE =
            registerSoundEvent("power_boost_idle");
    public static final Holder<SoundEvent> POWER_BOOST =
            registerSoundEvent("power_boost");
    public static final Holder<SoundEvent> SMASH_CHARGE =
            registerSoundEvent("smash_charge");
    public static final Holder<SoundEvent> SMASH_HIT =
            registerSoundEvent("smash_hit");
    public static final Holder<SoundEvent> SONIC_BOOM =
            registerSoundEvent("sonic_boom");
    public static final Holder<SoundEvent> SONIC_WIND_SHOOT =
            registerSoundEvent("sonic_wind_shoot");
    public static final Holder<SoundEvent> SONIC_WIND_STUN =
            registerSoundEvent("sonic_wind_stun");
    public static final Holder<SoundEvent> SPIN_SLASH =
            registerSoundEvent("spin_slash");
    public static final Holder<SoundEvent> SPINDASH_CHARGE =
            registerSoundEvent("spindash_charge");
    public static final Holder<SoundEvent> SPINDASH_RELEASE =
            registerSoundEvent("spindash_release");
    public static final Holder<SoundEvent> STOMP =
            registerSoundEvent("stomp");
    public static final Holder<SoundEvent> TORNADO =
            registerSoundEvent("tornado");
    public static final Holder<SoundEvent> ULTIMATE_MUSIC =
            registerSoundEvent("ultimate_music");


    private static Holder<SoundEvent> registerSoundEvent(String soundName){
        return SOUND_EVENTS.register(
                soundName,
                SoundEvent::createVariableRangeEvent
        );
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}