package com.moray.moraymobs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class Voidmoss extends WaterlilyBlock {
    protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        FluidState $$3 = pLevel.getFluidState(pPos);
        FluidState $$4 = pLevel.getFluidState(pPos.above());
        return ($$3.getType() == Fluids.WATER || pState.getBlock() instanceof IceBlock) && $$4.getType() == Fluids.EMPTY;
    }




    public Voidmoss(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

}
