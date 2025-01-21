package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.block.Shulkerberrycrop;
import com.moray.moraymobs.registries.Blockregistrires;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class MorayBlockStateProvider extends BlockStateProvider {
    public MorayBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MorayMobs.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
blockWithItem(Blockregistrires.BASALTLAMP);
blockWithItem(Blockregistrires.BLOCK_OF_SCALES);
simpleBlockWithItem(Blockregistrires.END_CELSOSIA.get(), models().cross(blockTexture(Blockregistrires.END_CELSOSIA.get()).getPath(),
                blockTexture(Blockregistrires.END_CELSOSIA.get())).renderType("cutout"));
      //  simpleBlockWithItem(Blockregistrires.END_CELSOSIA_POTTED.get(), models().singleTexture("potted_endercelosia", ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"flower_pot_cross"), "plant",
            //    blockTexture(Blockregistrires.END_CELSOSIA.get())).renderType("cutout"));
        simpleBlockWithItem(Blockregistrires.END_GRASS.get(),
                models().cross(blockTexture(Blockregistrires.END_GRASS.get()).getPath(), blockTexture(Blockregistrires.END_GRASS.get())).renderType("cutout"));
        makeshulkerberryCrop((CropBlock) Blockregistrires.SHULKERFRUIT_CROP.get(), "shulkerberrycrop_stage", "shulkerberrycrop_stage");

simpleBlockWithItem(Blockregistrires.PADDED_MOSS.get(), models().carpet(blockTexture(Blockregistrires.PADDED_MOSS.get()).getPath(),blockTexture(Blockregistrires.PADDED_MOSS.get())).renderType("translucent"));


    }


    public void makeshulkerberryCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> shulkerberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }





    private ConfiguredModel[] shulkerberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((Shulkerberrycrop) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID, "block/" + textureName + state.getValue(((Shulkerberrycrop) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }


    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

}
