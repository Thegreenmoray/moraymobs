package com.moray.moraymobs.item;

import com.google.common.collect.ImmutableMap;
import com.moray.moraymobs.modevents.Clientevents;
import com.moray.moraymobs.registries.Itemregististeries;
import com.moray.moraymobs.rendersandmodels.render.BeetleArmorRenderer;
import com.moray.moraymobs.rendersandmodels.render.OmnidensArmorRenderer;
import com.moray.moraymobs.util.MorayKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Omnidensarmor extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(Morayarmormaterials.ModArmorMaterials.OMNIDENS_ARMOR_MATERIAL,
                            List.of(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,25,1,false,false))) .build();

    public Omnidensarmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_) {
        return p_41135_.is(Items.NAUTILUS_SHELL);
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if (!(pEntity instanceof Player player)){
            return;
        }


        if(!pLevel.isClientSide()) {
            if(hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);

            }



        }



            if (pEntity instanceof Player living&&hasFullSuitOfArmorOn(player)&&armorisincooldown(living)) {

 if(Minecraft.getInstance().player==living && MorayKeyBinding.ROARING_KEY.isDown() &&hasCorrectArmorOn(getMaterial(),living)){
               if (pLevel.isClientSide){

                   PacketDistributor.sendToServer(new Clientevents.ClientModBusEvents.ISROARING(true));

for(ItemStack itemStack:player.getArmorSlots()){
           player.getCooldowns().addCooldown(itemStack.getItem(),200);}
            }
            }
        }



        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();


            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial,new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,25,1) );
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, Holder<ArmorMaterial> mapArmorMaterial,
                                            MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect&&(player.getHealth()/player.getMaxHealth())<=0.5f) {
            player.addEffect(new MobEffectInstance(mapStatusEffect));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private boolean armorisincooldown( Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if(player.getCooldowns().isOnCooldown(armorStack.getItem())){
                return false;
            }
        }

        return true;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private OmnidensArmorRenderer renderer;

            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new OmnidensArmorRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
