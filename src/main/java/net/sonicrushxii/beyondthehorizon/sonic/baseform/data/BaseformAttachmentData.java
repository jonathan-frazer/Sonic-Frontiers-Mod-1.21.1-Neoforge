package net.sonicrushxii.beyondthehorizon.sonic.baseform.data;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums.*;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.exceptions.AbilityDoubleSetException;

public class BaseformAttachmentData extends AttachmentData
{
    //Some Variables that seemed cooler to not have in Auxillary Counter
    public int helpScreenPageNo;
    public int comboPointDisplay;

    //Cooldowns
    public byte[] slotAbilityCooldowns;

    //Byte State holder is Basically a Boolean Array but uses byte[] instead of boolean[]. And makes use of the individual bits
    public ByteStateHolder state;

    //Main Timers for Abilities
    //Both Hold and Action abilities can't be active at the same time
    public BaseformActionAbility currentActionAbility;
    public BaseformHoldAbility currentHoldAbility;
    public int activeAtkTimer; //Becomes -ve for Holding Abilities, For Automatic Teardown
    public int[] tearDownTimers;


    //Extra Details
    //Auxiliary Counters/Timers - Comes in handy for a few things
    public int[] auxiliaryCounters;
    //Custom Flexibility Added - For Data that goes Beyond Simple Integers
    public CompoundTag extraProperties;

    public BaseformAttachmentData()
    {
        helpScreenPageNo = 0;
        comboPointDisplay = 0;

        //Initialize Cooldowns
        slotAbilityCooldowns = new byte[BaseformCooldowns.values().length];

        //Initialize States
        state = new ByteStateHolder(BaseformState.values().length);
        state.setState(BaseformState.HAS_DOUBLE_JUMP.ordinal());
        state.setState(BaseformState.DANGER_SENSE_ACTIVE.ordinal());
        state.clearState(BaseformState.LIGHT_SPEED_STATE.ordinal());
        state.clearState(BaseformState.POWER_BOOST_STATE.ordinal());

        //Timers
        activeAtkTimer = 0;
        currentActionAbility = BaseformActionAbility.NONE;
        currentHoldAbility = BaseformHoldAbility.NONE;
        tearDownTimers = new int[BaseformActionAbility.values().length];

        //Extra Properties/Data
        auxiliaryCounters = new int[BaseformAuxiliaryCounters.values().length];
        extraProperties = new CompoundTag();
    }

    @Override
    public CompoundTag serialize()
    {
        if(currentHoldAbility != BaseformHoldAbility.NONE && currentActionAbility != BaseformActionAbility.NONE)
            throw new AbilityDoubleSetException("A Hold and an Action Ability cannot Occur at the Same time");

        CompoundTag nbt = new CompoundTag();
        nbt.putString("currentForm",getForm());

        nbt.putInt("HelpScreenPage",helpScreenPageNo);
        nbt.putInt("ComboPoints",comboPointDisplay);

        nbt.putByteArray("Cooldowns",slotAbilityCooldowns);
        nbt.putByteArray("State",state.getByteArray());

        nbt.putIntArray("CurrentAbility",new int[]{currentActionAbility.ordinal(),currentHoldAbility.ordinal()});
        nbt.putInt("AbilityTimer",activeAtkTimer);
        nbt.putIntArray("Teardown",tearDownTimers);

        nbt.putIntArray("AuxiliaryCounter",auxiliaryCounters);
        nbt.put("CustomData",extraProperties);

        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt)
    {
        this.helpScreenPageNo = nbt.getInt("HelpScreenPage");
        this.comboPointDisplay = nbt.getInt("ComboPoints");

        this.slotAbilityCooldowns = nbt.getByteArray("Cooldowns");
        this.state = new ByteStateHolder(nbt.getByteArray("State"));

        int[] c = nbt.getIntArray("CurrentAbility");
        this.currentActionAbility = BaseformActionAbility.values()[c[0]];
        this.currentHoldAbility = BaseformHoldAbility.values()[c[1]];
        this.activeAtkTimer = nbt.getInt("AbilityTimer");
        this.tearDownTimers = nbt.getIntArray("Teardown");

        this.auxiliaryCounters = nbt.getIntArray("AuxiliaryCounter");
        this.extraProperties = nbt.getCompound("CustomData");

        if(currentHoldAbility != BaseformHoldAbility.NONE && currentActionAbility != BaseformActionAbility.NONE)
            throw new AbilityDoubleSetException("A Hold and an Action Ability cannot Occur at the Same time");

    }

    @Override
    public String toString() {
        return this.serialize().toString();
    }

    @Override
    public String getForm() {
        return BeyondTheHorizon.MOD_ID+":baseform";
    }
}
