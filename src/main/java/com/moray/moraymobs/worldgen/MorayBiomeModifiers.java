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

    public static final ResourceKey<BiomeModifier> SPAWN_MORAY= registerKey("spawn_bony_moray");

    public static final ResourceKey<BiomeModifier> SPAWN_PADDLEFISH= registerKey("spawn_paddlefish");

    public static final ResourceKey<BiomeModifier> SPAWN_SOULCATCHER= registerKey("spawn_soulcatcher");

    public static final ResourceKey<BiomeModifier> SPAWN_PRONGHORN= registerKey("spawn_pronghorn");

    public static final ResourceKey<BiomeModifier> SPAWN_THRESHER= registerKey("spawn_threshershark");

    public static final ResourceKey<BiomeModifier> SPAWN_AMBERGOLEM= registerKey("spawn_ambergolem");

    public static final ResourceKey<BiomeModifier> SPAWN_LESSER_TESSERACT= registerKey("spawn_lesser_tesseract");


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
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH),biomes.getOrThrow(Biomes.DEEP_OCEAN),
                        biomes.getOrThrow(Biomes.FROZEN_OCEAN),
                        biomes.getOrThrow(Biomes.COLD_OCEAN),
                biomes.getOrThrow(Biomes.DESERT)),
                List.of(new MobSpawnSettings.
                SpawnerData(Mobregistries.MORAY.get(), 1000, 4, 9))));




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
                HolderSet.direct(biomes.getOrThrow(Biomes.WARM_OCEAN),
                        biomes.getOrThrow(Biomes.LUKEWARM_OCEAN)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.THRESHER.get(), 30, 1, 2))));


        context.register(SPAWN_AMBERGOLEM,new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.SWAMP)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.AMBERGOLEM.get(), 10, 1, 1))));

        context.register(SPAWN_LESSER_TESSERACT,new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct( biomes.getOrThrow(Biomes.END_BARRENS)),
                List.of(new MobSpawnSettings.SpawnerData(Mobregistries.LESSER_TESSERACT.get(), 20, 1, 1))));

    }


}
