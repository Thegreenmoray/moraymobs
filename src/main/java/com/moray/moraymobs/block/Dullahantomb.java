package com.moray.moraymobs.block;

import com.mojang.serialization.MapCodec;
import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Dullahantomb extends HorizontalDirectionalBlock {

    public static final MapCodec<Dullahantomb> CODEC = simpleCodec(Dullahantomb::new);
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 26.0, 13.0);

    public Dullahantomb(Properties properties) {
        super(properties);
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

      if(stack.is(Blocks.WITHER_ROSE.asItem())) {
        if(!player.isCreative()) {
         stack.shrink(1);}
          Dullahan dullahan=new Dullahan(Mobregistries.DULLAHAN.get(),level);
          dullahan.setPos(Vec3.atLowerCornerOf(pos));
          level.addFreshEntity(dullahan);
          return ItemInteractionResult.SUCCESS;
      }

        return ItemInteractionResult.FAIL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
