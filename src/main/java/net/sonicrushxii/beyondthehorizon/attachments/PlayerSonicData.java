package net.sonicrushxii.beyondthehorizon.attachments;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerSonicData implements INBTSerializable<CompoundTag>
    {
        public AttachmentData properties;
    
        public static AttachmentData getFormDataInstance(String form)
        {
            return switch (form) {
                case "baseform" -> new BaseformAttachmentData();
                default -> new AttachmentData();
            };
        }
        public PlayerSonicData()
        {
            properties = new AttachmentData();
        }
    
        @Override
        public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag nbt = new CompoundTag();
            nbt.put("properties", properties.serialize());
    
            return nbt;
        }
    
        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
            CompoundTag nbtProperties = nbt.getCompound("properties");
    
            //If forms don't match, Use the appropriate Child Instance
            if(!properties.getForm().equals(nbt.getString("currentForm")))
                properties = PlayerSonicData.getFormDataInstance(
                        nbtProperties.getString("currentForm").substring(BeyondTheHorizon.MOD_ID.length()+1)
                );
    
            //Read the Data
            properties.deserialize(nbtProperties);
        }
    
        @Override
        public String toString() {
            return "PlayerSonicData{properties="+properties+"}";
        }
    
        public boolean isSonic()
        {
            return !properties.getForm().equals(BeyondTheHorizon.MOD_ID+":none");
        }
    
    }