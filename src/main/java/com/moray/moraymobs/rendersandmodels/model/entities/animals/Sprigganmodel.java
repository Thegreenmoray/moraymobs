package com.moray.moraymobs.rendersandmodels.model.entities.animals;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Sprigganmodel extends GeoModel<Spriggan> {
    @Override
    public ResourceLocation getModelResource(Spriggan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/spriggan.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Spriggan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/spriggan.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Spriggan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/spriggan.animation.json");

    }

    @Override
    public void setCustomAnimations(Spriggan animatable, long instanceId, AnimationState<Spriggan> animationState) {
       super.setCustomAnimations(animatable,instanceId,animationState);
        GeoBone head= getAnimationProcessor().getBone("head");

        if (head != null){
            EntityModelData entityModelData=animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            assert entityModelData != null;
            head.setRotX(entityModelData.headPitch()* Mth.DEG_TO_RAD);
            head.setRotY(entityModelData.headPitch()* Mth.DEG_TO_RAD);
            head.setRotZ(entityModelData.headPitch()* Mth.DEG_TO_RAD);

        }

    }



}
