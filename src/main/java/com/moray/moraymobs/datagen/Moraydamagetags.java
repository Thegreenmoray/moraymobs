package com.moray.moraymobs.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface Moraydamagetags {
    ResourceKey<DamageType> PAWPAWBOMB = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.withDefaultNamespace("in_fire"));

}
