package com.moray.moraymobs.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;


public class Soulbeamweapon extends Item {

    public Soulbeamweapon(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {

                int j = this.getUseDuration(stack, entityLiving) - timeLeft;
                if (j>=20) {

                    Vec3 look = player.getLookAngle();
                    Vec3 vec3d = player.position().add(0.0D, 1.600000023841858D, 0.0D);
                    Vec3 vec3d2 = player.getEyePosition().subtract(vec3d);
                    Vec3 vec3d3 = look.normalize();

                    for (int i = 1; i <  Mth.floor(vec3d2.length()) + 10; ++i) {
                        Vec3 vec3d4 = vec3d.add(vec3d3.normalize().scale( i));
                        List<Entity> entities=player.level().getEntities(player,player.getBoundingBox().inflate(5));
                        player.level().addParticle(ParticleTypes.SOUL,vec3d4.x(),vec3d4.y()-1,vec3d4.z(), 0.0D, 0.0D, 0.0D);

                        for(Entity entity:entities) {
                            if (entity.position().distanceTo(vec3d4) < 2) {
                                entity.hurt(player.damageSources().sonicBoom(player), 10);
                            }
                        }
                    }
                    if (!player.isCreative()){
                        stack.hurtAndBreak(1,player, Objects.requireNonNull(this.getEquipmentSlot(stack)));}


            }
        }
    }



    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();
       if (!pPlayer.getAbilities().instabuild && flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }
}
