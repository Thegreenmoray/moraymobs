package com.moray.moraymobs.block;

import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class Shulkerberrycrop extends CropBlock {
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE= BlockStateProperties.AGE_5;;


    public Shulkerberrycrop(Properties pProperties) {
        super(pProperties);
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    protected ItemLike getBaseSeedId() {
        return Itemregististeries.END_SEED.get();
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
