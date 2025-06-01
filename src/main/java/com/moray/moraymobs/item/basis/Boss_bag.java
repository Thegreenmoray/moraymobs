package com.moray.moraymobs.item.basis;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class Boss_bag extends Item {
    public Boss_bag(Properties properties) {
        super(properties);

    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        return null;
    }



}

