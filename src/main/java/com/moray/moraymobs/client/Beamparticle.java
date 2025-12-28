package com.moray.moraymobs.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Beamparticle extends TextureSheetParticle {
    protected Beamparticle(ClientLevel level, double x, double y, double z,
      double xSpeed, double ySpeed, double zSpeed,SpriteSet setsprite) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.rCol = 1;  //mess around this these
        this.gCol = 1;
        this.bCol = 1;
        this.alpha= 0.4F;
        this.setSpriteFromAge(setsprite);
        this.lifetime=30;


    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new Beamparticle(level, x, y, z, xSpeed, ySpeed, zSpeed,sprites);
        }
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
