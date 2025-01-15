package com.moray.moraymobs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class EndFlowerBlock extends FlowerBlock {
    public EndFlowerBlock(Holder<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
    }



    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return  pState.is(Blocks.END_STONE);}


}
