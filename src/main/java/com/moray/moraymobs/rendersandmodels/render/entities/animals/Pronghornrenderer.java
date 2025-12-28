package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.moray.moraymobs.entity.living.animalornpc.Pronghorn;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Pronghornmodel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Pronghornrenderer extends GeoEntityRenderer<Pronghorn> {
    public Pronghornrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Pronghornmodel());
    }


    public void render(Pronghorn entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.isBaby()){
            poseStack.scale(0.5f,0.5f,0.5f);
        }



        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }

}
