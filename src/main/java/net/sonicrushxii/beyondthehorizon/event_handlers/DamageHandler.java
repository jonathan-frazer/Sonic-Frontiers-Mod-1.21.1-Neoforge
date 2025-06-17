package net.sonicrushxii.beyondthehorizon.event_handlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sonicrushxii.beyondthehorizon.BeyondTheHorizon;
import net.sonicrushxii.beyondthehorizon.attachments.PlayerSonicData;
import net.sonicrushxii.beyondthehorizon.modded.ModDamageTypes;
import net.sonicrushxii.beyondthehorizon.packet.SyncSonicPacket;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.data.BaseformAttachmentData;
import net.sonicrushxii.beyondthehorizon.sonic.baseform.events.server.BaseformDamageHandler;

import static net.sonicrushxii.beyondthehorizon.modded.ModAttachments.SONIC_DATA;

public class DamageHandler
{
    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent.Pre event)
    {
        boolean sync = false;
        /**Player Attacked*/
        if(event.getEntity() instanceof ServerPlayer player) {
            PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
            sync = switch (playerSonicData.properties.getForm().substring(BeyondTheHorizon.MOD_ID.length() + 1)) {
                case "baseform" -> BaseformDamageHandler.takeDamage(event, (BaseformAttachmentData) playerSonicData.properties);
                default -> false;
            };
            if(sync) PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
        }

        sync = false;
        /** Player: Attacker*/
        try{
            if(event.getSource().getEntity() instanceof ServerPlayer player)
            {
                PlayerSonicData playerSonicData = player.getData(SONIC_DATA);
                sync = switch (playerSonicData.properties.getForm().substring(BeyondTheHorizon.MOD_ID.length() + 1)) {
                    case "baseform" -> BaseformDamageHandler.dealDamage(event, (BaseformAttachmentData) playerSonicData.properties);
                    default -> false;
                };
                if(sync) PacketDistributor.sendToPlayer(player, new SyncSonicPacket(playerSonicData.serializeNBT(null)));
            }
        }catch(NullPointerException ignored){}
    }

    public static boolean isDamageSourceModded(DamageSource damageSource)
    {
        //Checks all the ModDamageTypes
        for(ModDamageTypes modDamageType : ModDamageTypes.values())
            return damageSource.is(modDamageType.getResourceKey());

        return false;
    }
}
