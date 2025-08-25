package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Lesser_Tesseract;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TesseractModel extends GeoModel<Lesser_Tesseract> {
    @Override
    public ResourceLocation getModelResource(Lesser_Tesseract tesseract) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/lesser_teserract.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Lesser_Tesseract tesseract) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/lesser_teserract.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Lesser_Tesseract tesseract) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/lesser_teserract.animation.json");
    }
}
