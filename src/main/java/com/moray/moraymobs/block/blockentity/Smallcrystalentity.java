package com.moray.moraymobs.block.blockentity;

import com.moray.moraymobs.registries.Blockregistrires;
import com.moray.moraymobs.registries.Morayblockentities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class Smallcrystalentity extends BlockEntity implements GeoBlockEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Smallcrystalentity( BlockPos pos, BlockState blockState) {
        super(Morayblockentities.SMALL_CRYSTAL_ENTITY.get(), pos, blockState);
    }


    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
