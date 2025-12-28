package com.moray.moraymobs.rendersandmodels.model.entities.animals;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animalornpc.Opossum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Opossummodel extends GeoModel<Opossum> {
    @Override
    public ResourceLocation getModelResource(Opossum opossum) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/opussum.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Opossum opossum) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/opossum.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Opossum opossum) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/opposum.animation.json");
    }

    public void setCustomAnimations(Opossum animatable, long instanceId, AnimationState<Opossum> animationState) {

        GeoBone head= getAnimationProcessor().getBone("head");

   if (head != null&&!animatable.isfainted()){
       EntityModelData entityModelData=animationState.getData(DataTickets.ENTITY_MODEL_DATA);

      if (entityModelData !=null){
       head.setRotX(entityModelData.headPitch()* Mth.DEG_TO_RAD);
        head.setRotZ(entityModelData.headPitch()* Mth.DEG_TO_RAD);}

   }

    }



}
