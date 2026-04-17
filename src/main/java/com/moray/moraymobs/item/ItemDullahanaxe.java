package com.moray.moraymobs.item;

import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class ItemDullahanaxe extends AxeItem {


    public ItemDullahanaxe(Properties p_40524_) {
        super(Tiers.NETHERITE, p_40524_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        FoodProperties foodproperties = itemstack.getFoodProperties(player);
        if (foodproperties != null) {
            if (player.canEat(foodproperties.canAlwaysEat())) {
                player.startUsingItem(usedHand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }
    }



}
