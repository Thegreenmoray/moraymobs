package com.moray.moraymobs.block;

import com.mojang.serialization.MapCodec;
import com.moray.moraymobs.registries.Blockregistrires;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import static com.moray.moraymobs.block.Keytopblock.KEYINSERT1;

public class Keydownblock extends Block {
    public static final MapCodec<Keydownblock> CODEC = simpleCodec(Keydownblock::new);
    public static final BooleanProperty KEYINSERT2= BooleanProperty.create("keyinsertedtwo");


    public Keydownblock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(KEYINSERT2, true));
    }

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {


        if (level.getBlockState(pos).getValue(KEYINSERT2)){
            return;
        }

        if (level.getBlockState(pos.above()).is(Blockregistrires.KEYTOPBLOCK)&&!level.getBlockState(pos.above()).getValue(KEYINSERT1)){
            level.removeBlock(pos,false);
            removeDarkcrackedbrickedBreadthFirstSearch(level,pos);
        }

    }


    private boolean removeDarkcrackedbrickedBreadthFirstSearch(Level level, BlockPos pos) {
        return BlockPos.breadthFirstTraversal(pos, 8, 69,(p_277519_, p_277492_) -> {
            for(Direction direction : Direction.values()) {
                p_277492_.accept(p_277519_.relative(direction));
            }

        }, (p_294069_) -> {
            if (!p_294069_.equals(pos)) {
                BlockState blockstate = level.getBlockState(p_294069_);
                {
                    if (blockstate.getBlock() == Blockregistrires.CRACKEDDARKPRISMANE.get()||blockstate.getBlock() == Blockregistrires.KEYDOWNBLOCK.get()||blockstate.getBlock() == Blockregistrires.KEYTOPBLOCK.get()) {
                        level.setBlock(p_294069_, Blocks.WATER.defaultBlockState(), 3);
                    }
                }

            }else{level.setBlock(p_294069_, Blocks.WATER.defaultBlockState(), 1);}
            return true;
        })>1;
    }



    public MapCodec<Keydownblock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (!level.isClientSide){
            boolean getstate=state.getValue(KEYINSERT2);

//trial_key is placeholder
            if (player.getItemInHand(hand).is(Itemregististeries.BOTTOM_KEY)&&getstate){
                if (!player.isCreative()){
                stack.shrink(1);}
                level.setBlockAndUpdate(pos,state.setValue(KEYINSERT2,false));
                return ItemInteractionResult.SUCCESS;
            }}
        return ItemInteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KEYINSERT2);
    }


}
