package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Particlesregistries {
        public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
                DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MorayMobs.MODID);

        public static final Supplier<SimpleParticleType> BEAM_PARTICLES =
                PARTICLE_TYPES.register("beamparticle", () -> new SimpleParticleType(true));

        public static void register(IEventBus eventBus) {
            PARTICLE_TYPES.register(eventBus);
        }
    }

