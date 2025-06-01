package com.moray.moraymobs.block;

import com.mojang.serialization.MapCodec;
import com.moray.moraymobs.block.blockentity.Largecrystalentity;
import com.moray.moraymobs.block.blockentity.Smallcrystalentity;
import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Smallcrystal extends BaseEntityBlock {
    public static final MapCodec<Smallcrystal> CODEC = simpleCodec(Smallcrystal::new);



    public Smallcrystal(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {


        level.removeBlock(pos,false);
        level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 5.0F, 0.0F);


        for (int i = 0; i < 3; i++) {
            Walliserops walliserops=new Walliserops(Mobregistries.WALLISEROPS.get(),level);
            walliserops.setPos(Vec3.atLowerCornerOf(pos));
            level.addFreshEntity(walliserops);
            Schinderhannes schinderhannes=new Schinderhannes(Mobregistries.SCHINDERHANNES.get(),level);
            schinderhannes.setPos(Vec3.atLowerCornerOf(pos));
            level.addFreshEntity(schinderhannes);
            Microdictyon microdictyon=new Microdictyon(Mobregistries.MICRODICTYON.get(),level);
            microdictyon.setPos(Vec3.atLowerCornerOf(pos));
            level.addFreshEntity(microdictyon);
        }

        return InteractionResult.SUCCESS_NO_ITEM_USED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new Smallcrystalentity(blockPos,blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
