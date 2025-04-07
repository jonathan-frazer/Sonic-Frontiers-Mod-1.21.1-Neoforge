package net.sonicrushxii.beyondthehorizon.modded;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;

import java.util.function.Supplier;

import static net.sonicrushxii.beyondthehorizon.BeyondTheHorizon.MOD_ID;

public class ModAttachments
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    // Serialization via INBTSerializable
    public static final Supplier<AttachmentType<PlayerSonicData>> SONIC_DATA = ATTACHMENT_TYPES.register(
            "sonic_frontiers_data", () -> AttachmentType.serializable(PlayerSonicData::new).build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
