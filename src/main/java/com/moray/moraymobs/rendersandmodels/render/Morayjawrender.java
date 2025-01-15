package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.monster.Morayjaw;
import com.moray.moraymobs.rendersandmodels.model.Morayjawmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Morayjawrender extends GeoEntityRenderer<Morayjaw> {
    public Morayjawrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Morayjawmodel());
    }







}
