package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Blockregistrires;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MorayBlockTagGenerator extends BlockTagsProvider {
    public MorayBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MorayMobs.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
this.tag(BlockTags.NEEDS_STONE_TOOL).add(Blockregistrires.BLOCK_OF_SCALES.get());
    }
}