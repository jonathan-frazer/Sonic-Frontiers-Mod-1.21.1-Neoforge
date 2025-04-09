package net.sonicrushxii.beyondthehorizon.baseform.data;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;

public class BaseformAttachmentData extends AttachmentData
{
    byte[] cooldowns;

    public BaseformAttachmentData() {
        cooldowns = new byte[10];
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("currentForm",getForm());
        nbt.putByteArray("cooldowns",cooldowns);

        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        cooldowns = nbt.getByteArray("cooldowns");
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
