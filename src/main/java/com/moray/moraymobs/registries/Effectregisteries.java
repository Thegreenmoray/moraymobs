package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.effect.Stun;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Effectregisteries {

    public static final DeferredRegister<MobEffect> MOB_EFFECT =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MorayMobs.MODID);

    final public static DeferredHolder<MobEffect, Stun> STUN=MOB_EFFECT.register("stun",()->
            new Stun(MobEffectCategory.HARMFUL,0xD6D84F));




    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
