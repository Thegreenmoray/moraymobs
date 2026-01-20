package com.moray.moraymobs.rendersandmodels.render.entities.animals;


import com.moray.moraymobs.entity.living.animalornpc.Lamprey;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Lampreymodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Lampreyrender extends GeoEntityRenderer<Lamprey> {
    public Lampreyrender(EntityRendererProvider.Context context) {
        super(context, new Lampreymodel());
    }
}
