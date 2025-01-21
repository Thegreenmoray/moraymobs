package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;



public class EntityTagGenerator extends EntityTypeTagsProvider {

    public static final TagKey<EntityType<?>> ASSIMILABLE = create(ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"assimilable"));
    public static final TagKey<EntityType<?>> SPOTTED = create(ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"canbespotted"));


    private static TagKey<EntityType<?>> create(ResourceLocation rl) {
        return TagKey.create(Registries.ENTITY_TYPE, rl);
    }

    protected void addTags(HolderLookup.Provider provider) {
        super.addTags(provider);
        this.tag(ASSIMILABLE).add(EntityType.ZOMBIE, EntityType.HUSK,EntityType.DROWNED,EntityType.PLAYER,EntityType.VILLAGER,EntityType.ENDERMAN, EntityType.WITCH,EntityType.PILLAGER,EntityType.VINDICATOR,EntityType.EVOKER);
        this.tag(SPOTTED).add(EntityType.EVOKER,EntityType.WITCH,EntityType.RAVAGER,EntityType.ENDERMAN,EntityType.WITHER, EntityType.WITHER_SKELETON,EntityType.BLAZE,EntityType.ELDER_GUARDIAN);
        this.tag(EntityTypeTags.ARTHROPOD).add(Mobregistries.VOLCANOBACK.get());
    this.tag(EntityTypeTags.UNDEAD).add(Mobregistries.MORAY.get());
    }


    public EntityTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }



}
