package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MorayItemTagGenerator extends ItemTagsProvider {
    public MorayItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                                 CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, MorayMobs.MODID, existingFileHelper);
    }

    public static final TagKey<Item> GOLDEN = create(ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"is_golden"));


    private static TagKey<Item> create(ResourceLocation rl) {
        return TagKey.create(Registries.ITEM, rl);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
tag(ItemTags.SWORDS).add(Itemregististeries.OMNIDENS_SWORD.get())
        .add(Itemregististeries.EEL_WHIP.get());
tag(ItemTags.SHOVELS).add(Itemregististeries.OMNIDENS_SHOVEL.get());
tag(ItemTags.HEAD_ARMOR).add(Itemregististeries.BEETLE_HELMET.get())
        .add(Itemregististeries.OMNIDENS_HELMET.get());
tag(ItemTags.CHEST_ARMOR).add(Itemregististeries.BEETLE_CHESTPLATE.get())
        .add(Itemregististeries.OMNIDENS_CHESTPLATE.get());
tag(ItemTags.LEG_ARMOR).add(Itemregististeries.BEETLE_LEGGINGS.get())
        .add(Itemregististeries.OMNIDENS_LEGGINGS.get());
tag(ItemTags.FOOT_ARMOR).add(Itemregististeries.BEETLE_BOOTS.get())
        .add(Itemregististeries.OMNIDENS_BOOTS.get());
tag(ItemTags.DURABILITY_ENCHANTABLE).add(Itemregististeries.BUCCANEER.get())
        .add(Itemregististeries.STUNGUN.get());
tag(ItemTags.MEAT).add(Itemregististeries.RAW_BOWFIN.get())
        .add(Itemregististeries.RAW_PRONGHORN.get()).add(Itemregististeries.RAW_LAMPREY.get())
        .add(Itemregististeries.COOKED_BOWFIN.get()).add(Itemregististeries.COOKED_PRONGHORN.get())
        .add(Itemregististeries.COOKED_LAMPREY.get()).add(Itemregististeries.LAMPREY_PIE.get());
tag(GOLDEN).add(Items.GOLDEN_SWORD,Items.GOLDEN_AXE,Items.GOLDEN_HOE,Items.GOLDEN_PICKAXE,Items.GOLDEN_SHOVEL
,Items.GOLDEN_BOOTS,Items.GOLDEN_LEGGINGS,Items.GOLDEN_HELMET,Items.GOLDEN_CHESTPLATE);


    }
}
