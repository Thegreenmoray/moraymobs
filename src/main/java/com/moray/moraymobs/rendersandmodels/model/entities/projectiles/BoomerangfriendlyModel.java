package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Friendly_Boomerrang;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BoomerangfriendlyModel extends GeoModel<Friendly_Boomerrang> {
    @Override
    public ResourceLocation getModelResource(Friendly_Boomerrang animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/omniprojectile.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Friendly_Boomerrang animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/boomerang.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Friendly_Boomerrang animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/boomerang.spin.json");

    }
}
