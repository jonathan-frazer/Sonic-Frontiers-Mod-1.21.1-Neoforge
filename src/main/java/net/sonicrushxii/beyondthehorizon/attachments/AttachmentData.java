package net.sonicrushxii.beyondthehorizon.attachments;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

public class AttachmentData
{
    public CompoundTag serialize()
    {
        CompoundTag properties = new CompoundTag();
        properties.putString("currentForm", getForm());
        return properties;
    }

    public void deserialize(CompoundTag nbt)
    {}

    @Override
    public String toString() {
        return this.serialize().toString();
    }

    //Make sure to Override
    public String getForm() {
        return BeyondTheHorizon.MOD_ID+":none";
    }
}
