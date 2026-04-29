package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Icice_projectile;
import com.moray.moraymobs.entity.projectiles.Soulpiece;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Soulicemodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Soulicerender extends GeoEntityRenderer<Icice_projectile> {
    public Soulicerender(EntityRendererProvider.Context context) {
        super(context, new Soulicemodel());
    }
}
