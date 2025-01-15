package com.moray.moraymobs.rendersandmodels.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.moray.moraymobs.entity.living.animal.Basaltlisk;
import com.moray.moraymobs.rendersandmodels.model.Basaltliskmodel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Basaltliskrender extends GeoEntityRenderer<Basaltlisk> {
    public Basaltliskrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Basaltliskmodel());
    }



    public void render(Basaltlisk entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.isBaby()){
            poseStack.scale(0.5f,0.5f,0.5f);
        }



        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
