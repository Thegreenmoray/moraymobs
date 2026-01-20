package com.moray.moraymobs.item;

import com.moray.moraymobs.entity.projectiles.Pawpawbomb;
import com.moray.moraymobs.entity.projectiles.Stunwave;
import com.moray.moraymobs.rendersandmodels.render.Stungunrender;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class Stungun extends Item implements GeoItem {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Stungun(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
       if (pLevel instanceof ServerLevel serverLevel) {
            pPlayer.startUsingItem(pHand);
            this.triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), serverLevel),
                    "Controller", "stunguncharge");
        }

        return super.use(pLevel, pPlayer, pHand);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {

                int j = this.getUseDuration(stack, entityLiving) - timeLeft;

                if (j>=20) {
                    if (!level.isClientSide) {
                        Stunwave stunwave=new Stunwave(level);
                        Vec3 vec= player.getViewVector(1);
                        stunwave.setPos(player.getX() + vec.x * 2.0, player.getY(0.33333) + 0.5, player.getZ() + vec.z * 2.0);


                        stunwave.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.1f * 3.0F, 1.0F);
                        stunwave.setDeltaMovement(vec.normalize());

                        stack.hurtAndBreak(1,entityLiving,player.getEquipmentSlotForItem(stack));


                        this.triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(player.getUsedItemHand()), (ServerLevel) level),
                                "BagController", "stunblast");


                        level.addFreshEntity(stunwave);


        }
    }}}


    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private Stungunrender renderer;

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new Stungunrender();
                }

                return this.renderer;
            }
        });
    }

        @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",state -> PlayState.STOP).triggerableAnim("stunguncharge", RawAnimation.begin().then("animation.blaster.charge", Animation.LoopType.HOLD_ON_LAST_FRAME))
        );
        controllerRegistrar.add(new AnimationController<>(this,
                "BagController",state -> PlayState.STOP).triggerableAnim("stunblast",RawAnimation.begin().then("animation.blaster.blast", Animation.LoopType.PLAY_ONCE))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}