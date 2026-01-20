package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Treebeam;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Beammodel extends GeoModel<Treebeam> {

    @Override
    public ResourceLocation getModelResource(Treebeam animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/beam.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Treebeam animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/beam.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Treebeam animatable) {
        return null;
    }
}
