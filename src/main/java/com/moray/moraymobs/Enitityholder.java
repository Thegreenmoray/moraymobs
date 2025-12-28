package com.moray.moraymobs;
import com.moray.moraymobs.entity.projectiles.Treebeam;
import com.moray.moraymobs.registries.Mobregistries;
import com.moray.moraymobs.registries.Morayblockentities;
import com.moray.moraymobs.rendersandmodels.render.*;
import com.moray.moraymobs.rendersandmodels.render.entities.animals.*;
import com.moray.moraymobs.rendersandmodels.render.entities.monsters.*;
import com.moray.moraymobs.rendersandmodels.render.entities.projectiles.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.http.cookie.SM;

public class Enitityholder {



    public static void setup(FMLClientSetupEvent event){
   EntityRenderers.register(Mobregistries.BODY_SNATCHER.get(), Bodysnatcherrenderer::new);
    EntityRenderers.register(Mobregistries.OPOSSUM.get(), Opossumrenderer::new);
     EntityRenderers.register(Mobregistries.BASALTISK.get(), Basaltliskrender::new);
     EntityRenderers.register(Mobregistries.VOLCANOBACK.get(), Volcanobackrender::new);
    EntityRenderers.register(Mobregistries.FIREHEAP.get(), LavaRender::new);
   EntityRenderers.register(Mobregistries.MORAY.get(), Morayrender::new);
    EntityRenderers.register(Mobregistries.MORAYJAW.get(), Morayjawrender::new);
    EntityRenderers.register(Mobregistries.PADDLEFISH.get(), Paddlefishrender::new);
    EntityRenderers.register(Mobregistries.SOULCATCHER.get(), Soulcatcherrender::new);
    EntityRenderers.register(Mobregistries.SOULPROJECTILE.get(), Soulballrenderer::new);
   EntityRenderers.register(Mobregistries.BOWFIN.get(), Bowfinrender::new);
   EntityRenderers.register(Mobregistries.PRONGHORN.get(),Pronghornrenderer::new);
   EntityRenderers.register(Mobregistries.THRESHER.get(), ThresherSharkRender::new);
   EntityRenderers.register(Mobregistries.STUNWAVE.get(), StunwaveRender::new);
  EntityRenderers.register(Mobregistries.OMNIDENS.get(), Omnidensrender::new);
  EntityRenderers.register(Mobregistries.BOOMERANG.get(), BoomerangRender::new);
  EntityRenderers.register(Mobregistries.GESYER.get(), Whirlpoolrender::new);
  EntityRenderers.register(Mobregistries.MICRODICTYON.get(),Microdictyonrenderer::new);
  EntityRenderers.register(Mobregistries.MICRODICTYON_PROJECTILE.get(),Microprojectilerander::new);
EntityRenderers.register(Mobregistries.SCHINDERHANNES.get(),Schinderhannesrenderer::new);
 EntityRenderers.register(Mobregistries.WALLISEROPS.get(),Walliseropsrenderer::new);
EntityRenderers.register(Mobregistries.BOUNCE_BALL.get(),Bouncerender::new);
EntityRenderers.register(Mobregistries.SEA_MINE.get(),Seaminerenderer::new);
 EntityRenderers.register(Mobregistries.BOOMERANG_FRIEND.get(),BoomerangFriendlyRender::new);
EntityRenderers.register(Mobregistries.BOUNCE_BALL_FRIEND.get(),Bouncefrender::new);
BlockEntityRenderers.register(Morayblockentities.LARGE_CRYSTAL_ENTITY.get(), CrystalLargeRender::new);
BlockEntityRenderers.register(Morayblockentities.SMALL_CRYSTAL_ENTITY.get(), SmallCrystalrenderer::new);
EntityRenderers.register(Mobregistries.AMBERGOLEM.get(),Ambergolemrender::new);
EntityRenderers.register(Mobregistries.AMBERCRYSTAL.get(),Ambercrystalrender::new);
EntityRenderers.register(Mobregistries.AMBERPORTAL.get(),Amberportalrenderer::new);
EntityRenderers.register(Mobregistries.LESSER_TESSERACT.get(),Tesseractrender::new);
 EntityRenderers.register(Mobregistries.SPRIGGAN.get(),Sprigganrender::new);
 EntityRenderers.register(Mobregistries.TREE_BEAM.get(), Beamrender::new);
 EntityRenderers.register(Mobregistries.BOMBA.get(), ThrownItemRenderer::new);
    }






}
