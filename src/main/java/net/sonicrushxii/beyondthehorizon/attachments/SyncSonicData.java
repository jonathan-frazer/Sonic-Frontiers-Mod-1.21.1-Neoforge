package net.sonicrushxii.beyondthehorizon.attachments;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

public record SyncSonicData(CompoundTag formDetails) implements CustomPacketPayload
{

    public static final CustomPacketPayload.Type<SyncSonicData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "sonic_form_data"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, SyncSonicData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SyncSonicData::formDetails,
            SyncSonicData::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
