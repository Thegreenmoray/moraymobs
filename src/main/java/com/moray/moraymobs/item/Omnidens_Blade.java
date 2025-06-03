package com.moray.moraymobs.item;

import com.moray.moraymobs.modevents.Clientevents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class Omnidens_Blade extends SwordItem {
    public Omnidens_Blade(Tier tier, Properties properties) {
        super(tier, properties);
    }





    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (!player.getCooldowns().isOnCooldown(this)) {
            Vec3 vec3 = player.getLookAngle();
            Vec3 vec = new Vec3(vec3.x()*20, 0, vec3.z()*20);

            player.addDeltaMovement(vec.add(vec3.x() * 10, vec3.y() * 10.1, vec3.z() * 10).normalize());
        }

        if (!level.isClientSide()&&!player.getCooldowns().isOnCooldown(this)) {
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 60);
            }




        }

        return InteractionResultHolder.success(heldStack);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.moraymobs.cambrian_blade"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



    public boolean isValidRepairItem(ItemStack sword, ItemStack stack) {
        return stack.is(Items.NAUTILUS_SHELL);
    }





}
