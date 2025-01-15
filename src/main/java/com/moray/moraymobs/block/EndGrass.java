package com.moray.moraymobs.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EndGrass extends BushBlock {

    public static final MapCodec<EndGrass> CODEC = simpleCodec(EndGrass::new);

    public EndGrass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }


    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return  pState.is(Blocks.END_STONE);}


}
