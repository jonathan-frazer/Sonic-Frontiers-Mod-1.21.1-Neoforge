package net.sonicrushxii.beyondthehorizon.attachments;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerSonicData implements INBTSerializable<CompoundTag>
{
    public boolean isSonic;
    public AttachmentData properties;

    public PlayerSonicData()
    {
        isSonic = false;
        properties = new AttachmentData();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("isSonic", isSonic);
        nbt.put("properties", properties.serialize());

        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        isSonic = nbt.getBoolean("isSonic");
        properties.deserialize((CompoundTag) nbt.get("properties"));
    }
}