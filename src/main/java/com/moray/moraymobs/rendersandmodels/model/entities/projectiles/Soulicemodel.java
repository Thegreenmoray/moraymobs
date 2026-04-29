package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Icice_projectile;
import com.moray.moraymobs.entity.projectiles.Soulpiece;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Soulicemodel extends GeoModel<Icice_projectile>{

        @Override
        public ResourceLocation getModelResource(Icice_projectile ambercrystal) {
            return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/icice.geo.json");

        }

        @Override
        public ResourceLocation getTextureResource(Icice_projectile ambercrystal) {
            return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/iceicice.png");
        }

        @Override
        public ResourceLocation getAnimationResource(Icice_projectile ambercrystal) {
            return null;

        }

}
