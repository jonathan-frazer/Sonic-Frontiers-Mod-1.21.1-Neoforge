package net.sonicrushxii.beyondthehorizon.baseform.data;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.ByteStateHolder;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;

public class BaseformAttachmentData extends AttachmentData
{
    public byte[] cooldowns;
    public ByteStateHolder concurrentState;

    public BaseformAttachmentData() {
        cooldowns = new byte[10];
        concurrentState = new ByteStateHolder(10);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("currentForm",getForm());
        nbt.putByteArray("cooldowns",cooldowns);
        nbt.putByteArray("concurrentState",concurrentState.getByteArray());

        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        cooldowns = nbt.getByteArray("cooldowns");
        concurrentState = new ByteStateHolder(nbt.getByteArray("concurrentState"));
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
