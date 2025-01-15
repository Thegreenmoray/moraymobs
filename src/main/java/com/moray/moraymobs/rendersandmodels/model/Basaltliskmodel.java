package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.Basaltlisk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Basaltliskmodel extends GeoModel<Basaltlisk> {
    @Override
    public ResourceLocation getModelResource(Basaltlisk basaltlisk) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/basaltlizard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Basaltlisk basaltlisk) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/basaltlisk.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Basaltlisk basaltlisk) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/basaltlisk.animation.json");

    }

    public void setCustomAnimations(Basaltlisk animatable, long instanceId, AnimationState<Basaltlisk> animationState) {

        GeoBone head= getAnimationProcessor().getBone("head");

        if (head != null){
            EntityModelData entityModelData=animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityModelData.headPitch()* Mth.DEG_TO_RAD);
            head.setRotZ(entityModelData.headPitch()* Mth.DEG_TO_RAD);

        }

    }







}
