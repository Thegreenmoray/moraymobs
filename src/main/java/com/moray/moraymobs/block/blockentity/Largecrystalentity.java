package com.moray.moraymobs.block.blockentity;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.registries.Mobregistries;
import com.moray.moraymobs.registries.Morayblockentities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class Largecrystalentity extends BlockEntity implements GeoBlockEntity {
    int timer=0;
    boolean isready;
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Largecrystalentity(BlockPos pos, BlockState blockState) {
        super(Morayblockentities.LARGE_CRYSTAL_ENTITY.get(), pos, blockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

   /* public static void tick(Level world, BlockPos pos, BlockState state, Largecrystalentity blockEntity) {
        Player playerEntity = world.getNearestPlayer((double) pos.getX() + 1.0D, (double) pos.getY() + 1.0D, (double) pos.getZ() + 1.0D, 6.0D, false);
if (playerEntity!=null&&!blockEntity.isready)//would put !player.iscreative, but this already accounts
{
    blockEntity.isready=true;

}
if (blockEntity.isready){
    blockEntity.timer+=1;
}
if (blockEntity.timer>35){
    world.removeBlockEntity(pos);
    world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 5.0F, 0.0F);

   Omnidens omnidens=new Omnidens(Mobregistries.OMNIDENS.get(),world);
    omnidens.setPos(Vec3.atLowerCornerOf(pos));
   world.addFreshEntity(omnidens);

}

    }*/


    private PlayState animations(AnimationState<Largecrystalentity> largecrystalentityAnimationState) {

        if (timer>0) {
            largecrystalentityAnimationState.getController().setAnimation(RawAnimation.begin().then("large_orb.animation", Animation.LoopType.HOLD_ON_LAST_FRAME));
       return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }





    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
