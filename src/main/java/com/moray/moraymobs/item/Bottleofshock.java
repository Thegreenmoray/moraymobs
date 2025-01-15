package com.moray.moraymobs.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bottleofshock extends Item {
    public Bottleofshock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
       tooltipComponents.add(Component.translatable("tooltip.moraymobs.glassofstunwave"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }




}
