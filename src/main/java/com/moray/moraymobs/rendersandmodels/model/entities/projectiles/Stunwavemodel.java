package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Stunwave;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Stunwavemodel extends GeoModel<Stunwave> {
    @Override
    public ResourceLocation getModelResource(Stunwave stunwave) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/stunwave.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Stunwave stunwave) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/shock.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Stunwave stunwave) {
        return null;
    }
}
