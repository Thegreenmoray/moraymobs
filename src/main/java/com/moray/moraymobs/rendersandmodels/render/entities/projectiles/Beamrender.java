package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Treebeam;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Beammodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Beamrender extends GeoEntityRenderer<Treebeam> {
    public Beamrender(EntityRendererProvider.Context context) {
        super(context, new Beammodel());
    }
}
