package com.moray.moraymobs.rendersandmodels.model;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Soulpiece;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Soulballmodel extends GeoModel<Soulpiece> {


    @Override
    public ResourceLocation getModelResource(Soulpiece soulballmodel) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/soulball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Soulpiece soulballmodel) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/soulprojectile.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Soulpiece soulballmodel) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/soulspin.json");

    }


}
