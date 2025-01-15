package com.moray.moraymobs.item;

import com.moray.moraymobs.modevents.Modifyplayer;
import com.moray.moraymobs.rendersandmodels.render.Eelwhiprender;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;


public class Eelwhip extends SwordItem implements GeoItem, Modifyplayer {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);




    public Eelwhip(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private Eelwhiprender renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new Eelwhiprender();
                }

                return this.renderer;
            }
        });
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }




}
