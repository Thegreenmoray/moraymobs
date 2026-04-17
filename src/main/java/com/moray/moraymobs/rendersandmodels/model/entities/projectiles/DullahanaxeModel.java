package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.DullanhanAxe;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DullahanaxeModel extends GeoModel<DullanhanAxe> {
    @Override
    public ResourceLocation getModelResource(DullanhanAxe animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/axes.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DullanhanAxe animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/dullahan_axe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DullanhanAxe animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/axes.animation.json");
    }
}
