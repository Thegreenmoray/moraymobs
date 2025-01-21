package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Blockregistrires;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class MorayItemModelProvider extends ItemModelProvider {
    public MorayItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MorayMobs.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
withExistingParent(Itemregististeries.OPPOSUM_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.BODYSNATCHER_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.BASALTlISK_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.VOLCANOBACK_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.MORAY_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.PADDLE_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.SOULCATCHER_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.BOWFIN_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.PRONGHORN_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
withExistingParent(Itemregististeries.THRESHERSHARK_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        basicItem(Itemregististeries.BRAIN.get());
     basicItem(Itemregististeries.JAW.get());
     basicItem(Itemregististeries.BEETLE_SCALE.get());
     basicItem(Itemregististeries.BEETLE_HELMET.get());
        basicItem(Itemregististeries.BEETLE_CHESTPLATE.get());
        basicItem(Itemregististeries.BEETLE_LEGGINGS.get());
        basicItem(Itemregististeries.BEETLE_BOOTS.get());
        basicItem(Itemregististeries.BASALT_CRYSTAL.get());
   basicItem(Itemregististeries.BUCKETED_PADDLEFISH.get());
   basicItem(Itemregististeries.BUCKETED_BOWFIN.get());
   basicItem(Itemregististeries.PADDLEFISH_FOOD.get());
    basicItem(Itemregististeries.SOULJEWEL.get());
    basicItem(Itemregististeries.SOULBEADRING.get());
basicItem(Itemregististeries.END_SEED.get());
basicItem(Itemregististeries.SHULKERBERRY.get());
basicItem(Itemregististeries.RAW_BOWFIN.get());
basicItem(Itemregististeries.COOKED_BOWFIN.get());
basicItem(Itemregististeries.RAW_PRONGHORN.get());
basicItem(Itemregististeries.COOKED_PRONGHORN.get());
basicItem(Itemregististeries.ANTLER.get());
basicItem(Itemregististeries.MOSS.get());
basicItem(Itemregististeries.BOTTLE_OF_SHOCK.get());
//simpleItemblock(Blockregistrires.END_CELSOSIA);
//simpleItemblock(Blockregistrires.END_GRASS);
    }





  // private ItemModelBuilder simpleItemblock(Supplier<Block> item) {
 //   return withExistingParent(item.get().getDescriptionId(),
 //             ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"item/generated")).texture("layer0",
 //           ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"block/" + item.get().getDescriptionId()));
// }
}
