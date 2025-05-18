package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Omnidensmodel extends GeoModel<Omnidens> {
    @Override
    public ResourceLocation getModelResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/omnidens.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/omnidens.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/omnidens.animation.json");
    }


    public void setCustomAnimations(Omnidens animatable, long instanceId, AnimationState<Omnidens> animationState) {

        GeoBone head = getAnimationProcessor().getBone("jaw");

        if (head != null ) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            if (entityModelData != null&&!animatable.getleap()) {
                head.setRotX(entityModelData.headPitch() * Mth.DEG_TO_RAD);
                head.setRotZ(entityModelData.headPitch() * Mth.DEG_TO_RAD);
            }

        }


    }}
