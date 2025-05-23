package com.moray.moraymobs.modevents;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.util.MorayKeyBinding;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;

public class Clientevents {


    @EventBusSubscriber(modid = MorayMobs.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void register(final RegisterPayloadHandlersEvent event) {
            // Sets the current network version
            final PayloadRegistrar registrar = event.registrar("1.0");
            registrar.playToServer(ISROARING.IS_ROAR_KEY_PRESSED_TYPE,
                    ISROARING.IS_ROAR_KEY_PRESSED_STREAM_CODEC, (isroaring, iPayloadContext) -> { //new IPayloadHandler<ISROARING>()
                        Player player = iPayloadContext.player();

                        List<Entity> ents=player.level().getEntities(player,player.getBoundingBox().inflate(5));

                  if (ents.isEmpty()){
                      return;
                  }
                   for (Entity entity:ents){
                       if (entity instanceof LivingEntity){
                           ((LivingEntity) entity).addEffect(player.getHealth()/player.getMaxHealth()>=0.5?new MobEffectInstance(MobEffects.WEAKNESS,200,1):new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,1));
                       }
                   }


                    });



        }

        public record ISROARING(boolean isroar) implements CustomPacketPayload {

            public static final CustomPacketPayload.Type<ISROARING> IS_ROAR_KEY_PRESSED_TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("moraymobs", "isroaring"));

            public static final StreamCodec<ByteBuf, ISROARING> IS_ROAR_KEY_PRESSED_STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    ISROARING::isroar,
                    ISROARING::new
            );

            @Override
            public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
                return IS_ROAR_KEY_PRESSED_TYPE;
            }}
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(MorayKeyBinding.ROARING_KEY);
        }
    }

}
