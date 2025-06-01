package com.moray.moraymobs.worldgen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class MorayBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_OPPOSSUM = registerKey("spawn_opossum");

    public static final ResourceKey<BiomeModifier> SPAWN_VOLCANOBACK= registerKey("spawn_volcanoback");

    public static final ResourceKey<BiomeModifier> SPAWN_MORAY= registerKey("spawn_moray");

    public static final ResourceKey<BiomeModifier> SPAWN_PADDLEFISH= registerKey("spawn_paddlefish");

    public static final ResourceKey<BiomeModifier> SPAWN_SOULCATCHER= registerKey("spawn_soulcatcher");

    public static final ResourceKey<BiomeModifier> SPAWN_PRONGHORN= registerKey("spawn_pronghorn");

    public static final ResourceKey<BiomeModifier> SPAWN_THRESHER= registerKey("spawn_thresher");


    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID, name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_OPPOSSUM, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.FOREST)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.OPOSSUM.get(), 40, 2, 4))));

        context.register(SPAWN_VOLCANOBACK, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.NETHER_WASTES)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.VOLCANOBACK.get(), 5, 1, 1))));

        context.register(SPAWN_MORAY, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.DEEP_OCEAN)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.MORAY.get(), 30, 1, 3))));

        context.register(SPAWN_PADDLEFISH, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES),
                        biomes.getOrThrow(Biomes.BASALT_DELTAS),
                        biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY),
                        biomes.getOrThrow(Biomes.WARPED_FOREST),
                        biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.PADDLEFISH.get(), 25, 2, 5))));

        context.register(SPAWN_SOULCATCHER, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.SOULCATCHER.get(), 20, 1, 3))));

        context.register(SPAWN_PRONGHORN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.PRONGHORN.get(), 25, 1, 3))));

        context.register(SPAWN_THRESHER, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.WARM_OCEAN)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.THRESHER.get(), 30, 1, 2))));

    }


}
