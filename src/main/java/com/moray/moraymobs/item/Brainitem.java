package com.moray.moraymobs.item;


import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Brainitem extends Item {
    public Brainitem(Properties pProperties) {
        super(pProperties);
    }


    public InteractionResult useOn(UseOnContext pContext) {
        Level level=  pContext.getLevel();
     Player player = pContext.getPlayer();

     ItemStack stack =player.getMainHandItem();
     if (!level.isClientSide){
       player.giveExperiencePoints(5);


         if (!player.getAbilities().instabuild) {
             stack.shrink(1);
         }



        return InteractionResult.PASS;}


        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

       tooltipComponents.add(Component.translatable("tooltip.moraymobs.brain"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
