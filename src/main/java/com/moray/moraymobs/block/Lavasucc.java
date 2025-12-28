package com.moray.moraymobs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class Lavasucc extends Block {
    public Lavasucc(Properties properties) {
        super(properties);
    }


    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            this.tryAbsorblava(level, pos);
        }

    }

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        this.tryAbsorblava(level, pos);
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    protected void tryAbsorblava(Level level, BlockPos pos) {
        this.removelavaBreadthFirstSearch(level, pos);
    }


    private boolean removelavaBreadthFirstSearch(Level level, BlockPos pos) {
        return BlockPos.breadthFirstTraversal(pos, 15, 150, (p_277519_, p_277492_) -> {
            for(Direction direction : Direction.values()) {
                p_277492_.accept(p_277519_.relative(direction));
            }

        }, (p_294069_) -> {
            if (p_294069_.equals(pos)) {
                return true;
            } else {
                BlockState blockstate = level.getBlockState(p_294069_);
                FluidState fluidstate = level.getFluidState(p_294069_);
                if (!fluidstate.is(FluidTags.LAVA)) {
                    return false;
                } else {

                    if (blockstate.getBlock() instanceof LiquidBlock) {
                        level.setBlock(p_294069_, Blocks.AIR.defaultBlockState(), 3);
                    } else {

                        BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(p_294069_) : null;
                        dropResources(blockstate, level, p_294069_, blockentity);
                        level.setBlock(p_294069_, Blocks.AIR.defaultBlockState(), 3);
                    }

                    return true;
                }
            }
        }) > 1;
    }







}
