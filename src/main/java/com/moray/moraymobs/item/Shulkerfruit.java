package com.moray.moraymobs.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Shulkerfruit extends Item {
    public Shulkerfruit(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide&&pEntityLiving instanceof ServerPlayer serverplayer) {
            serverplayer.removeEffect(MobEffects.LEVITATION);
        }

        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (pEntityLiving instanceof Player && !((Player)pEntityLiving).getAbilities().instabuild) {
            pStack.shrink(1);
        }

        return pStack;
    }

    public int getUseDuration(ItemStack pStack) {
        return 36;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.moraymobs.shulkerfruit"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



}
