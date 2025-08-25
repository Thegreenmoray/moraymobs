package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.monster.Lesser_Tesseract;
import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.TesseractModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Tesseractrender extends GeoEntityRenderer<Lesser_Tesseract> {
    public Tesseractrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TesseractModel());
    }

    @Override
    protected float getDeathMaxRotation(Lesser_Tesseract entityLivingBaseIn) {
        return 0.0F;
    }


}
