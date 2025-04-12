package net.sonicrushxii.beyondthehorizon.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;

public record KeyPressPacket(int keyCode) implements CustomPacketPayload
{

    public static final Type<KeyPressPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "key_press"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, KeyPressPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            KeyPressPacket::keyCode,
            KeyPressPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
