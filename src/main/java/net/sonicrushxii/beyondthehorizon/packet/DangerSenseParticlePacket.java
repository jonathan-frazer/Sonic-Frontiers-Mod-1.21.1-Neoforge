package net.sonicrushxii.beyondthehorizon.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import org.joml.Vector3f;

public record DangerSenseParticlePacket(Vector3f initialPos, Vector3f destinationPos) implements CustomPacketPayload
{

    public static final Type<DangerSenseParticlePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BeyondTheHorizon.MOD_ID, "danger_sense_particle"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, DangerSenseParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            DangerSenseParticlePacket::initialPos,
            ByteBufCodecs.VECTOR3F,
            DangerSenseParticlePacket::destinationPos,
            DangerSenseParticlePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
