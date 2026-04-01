package com.moray.moraymobs.mixins;


import com.moray.moraymobs.registries.Effectregisteries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHelper.class)
public class Enchantmenthelpermixin {

 @Inject(method= "runIterationOnItem*",at = @At("HEAD"),
         cancellable = true)
    public void runIterationOnItemdisenchment(LivingEntity entity, CallbackInfo ci){

     if (entity.hasEffect(Effectregisteries.DISENCHANTMENT)){
          ci.cancel();
     }

 }
}
