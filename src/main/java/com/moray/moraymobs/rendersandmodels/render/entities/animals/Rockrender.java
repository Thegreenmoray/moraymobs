package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.moray.moraymobs.entity.living.animalornpc.Opossum;
import com.moray.moraymobs.entity.living.animalornpc.Rockpup;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Rockpupmodel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Rockrender extends GeoEntityRenderer<Rockpup> {
    public Rockrender(EntityRendererProvider.Context context) {
        super(context, new Rockpupmodel());
    }


    public void render(Rockpup entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.isBaby()){
            poseStack.scale(0.3f,0.3f,0.3f);
        }



        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }


}
