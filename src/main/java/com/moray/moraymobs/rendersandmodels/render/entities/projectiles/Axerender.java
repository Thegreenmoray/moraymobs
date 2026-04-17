package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.DullanhanAxe;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.DullahanaxeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Axerender extends GeoEntityRenderer<DullanhanAxe> {
    public Axerender(EntityRendererProvider.Context context) {
        super(context, new DullahanaxeModel());
    }
}
