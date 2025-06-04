package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.worldgen.MorayBiomeModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MorayDatapackprovider extends DatapackBuiltinEntriesProvider {

        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
               .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MorayBiomeModifiers::bootstrap);


    public MorayDatapackprovider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(MorayMobs.MODID));
    }


}
