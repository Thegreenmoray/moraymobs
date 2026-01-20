package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class DamageTagGenerator extends DamageTypeTagsProvider{
    public static final TagKey<DamageType> ACIDBOMB = create(ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"assimilable"));


    public DamageTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    private static TagKey<DamageType> create(ResourceLocation rl) {
        return TagKey.create(Registries.DAMAGE_TYPE, rl);
    }


    protected void addTags(HolderLookup.Provider provider) {
        super.addTags(provider);
        this.tag(ACIDBOMB).add(Moraydamagetags.PAWPAWBOMB);


    }


}
