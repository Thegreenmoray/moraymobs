package com.moray.moraymobs.rendersandmodels.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.moray.moraymobs.entity.living.animal.Opossum;
import com.moray.moraymobs.rendersandmodels.model.Opossummodel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Opossumrenderer extends GeoEntityRenderer<Opossum> {
    public Opossumrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Opossummodel());
    }



    public void render(Opossum entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.isBaby()){
            poseStack.scale(0.3f,0.3f,0.3f);
        }



        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }

}
