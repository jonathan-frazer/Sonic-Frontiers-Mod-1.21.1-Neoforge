package net.sonicrushxii.beyondthehorizon.baseform.data;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.AttachmentData;

public class BaseformAttachmentData extends AttachmentData
{
    byte[] cooldowns;

    public BaseformAttachmentData()
    {
        cooldowns = new byte[10];
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("currentForm", BeyondTheHorizon.MOD_ID+":base_form");
        nbt.putByteArray("cooldowns",cooldowns);

        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        cooldowns = nbt.getByteArray("cooldowns");
    }
}
