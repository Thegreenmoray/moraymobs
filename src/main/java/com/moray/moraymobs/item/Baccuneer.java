package com.moray.moraymobs.item;

import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Bouncy_ball_Friend;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Friendly_Boomerrang;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.List;
import java.util.function.Predicate;

public class Baccuneer extends ProjectileWeaponItem {
    public Baccuneer(Properties properties) {
        super(properties);
    }


    public boolean isValidRepairItem(ItemStack shovel, ItemStack stack) {
        return stack.is(Items.NAUTILUS_SHELL);
    }


    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }


    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float v, float v1, float v2, @Nullable LivingEntity livingEntity1) {

        projectile.shootFromRotation(projectile, projectile.getXRot(), projectile.getYRot() + v2, 0.0F, v, v1);

    }

    public static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            pPlayer.startUsingItem(pHand);
        }

        return super.use(pLevel, pPlayer, pHand);
    }


    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {

                int i = this.getUseDuration(stack, entityLiving) - timeLeft;

                if (i < 0) {
                    return;
                }

                float f = getPowerForTime(i);
                if (!((double)f < 1.0)) {

                    if (level instanceof ServerLevel) {
                        int random_projectile=level.random.nextInt(2);
                        switch (random_projectile){
                         case 0 ->{
                            Friendly_Boomerrang friendlyBoomerrang=new Friendly_Boomerrang(level);
                             Vec3 vec= player.getViewVector(1);
                             friendlyBoomerrang.setPos(player.getX() + vec.x * 2.0, player.getY(0.33333) + 0.5, player.getZ() + vec.z * 2.0);


                             friendlyBoomerrang.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.1f * 3.0F, 1.0F);
                             friendlyBoomerrang.setDeltaMovement(vec.normalize());

                             stack.hurtAndBreak(1,entityLiving,player.getEquipmentSlotForItem(stack));

                             level.addFreshEntity(friendlyBoomerrang);

                         }
                         case 1 ->{
                             Bouncy_ball_Friend friendlybounce=new Bouncy_ball_Friend(level);
                             Vec3 vec= player.getViewVector(1);
                             friendlybounce.setPos(player.getX() + vec.x * 2.0, player.getY(0.33333) + 0.5, player.getZ() + vec.z * 2.0);


                             friendlybounce.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.1f * 3.0F, 1.0F);
                             friendlybounce.setDeltaMovement(vec.normalize());

                             stack.hurtAndBreak(1,entityLiving,player.getEquipmentSlotForItem(stack));

                             level.addFreshEntity(friendlybounce);

                         }
                        }


                    }

                  //  level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                  //  player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.moraymobs.buccaneer"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



    }

