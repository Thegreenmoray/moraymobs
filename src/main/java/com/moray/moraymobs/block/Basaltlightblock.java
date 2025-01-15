package com.moray.moraymobs.block;

import com.moray.moraymobs.tags.MorayKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class Basaltlightblock extends Block {
    public Basaltlightblock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.moraymobs.basaltlamp"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



    @SuppressWarnings("")
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

    if (!pLevel.isClientSide()) {


                List<Entity> light = pPlayer.level().getEntities(pPlayer, pPlayer.getBoundingBox().inflate(50), e -> e.getType().is(MorayKeys.IS_SPOTTABLE));

           for (Entity entity:light){
              LivingEntity entity1 =(LivingEntity)entity;
           entity1.addEffect(new MobEffectInstance(MobEffects.GLOWING,1222,0));
           }
                return InteractionResult.PASS;
            }



    return InteractionResult.FAIL;
}




}
//will work on later, light/brightness is given in the builder
