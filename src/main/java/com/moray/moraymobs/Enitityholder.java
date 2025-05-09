package com.moray.moraymobs;
import com.moray.moraymobs.registries.Mobregistries;
import com.moray.moraymobs.rendersandmodels.render.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class Enitityholder {



    public static void setup(FMLClientSetupEvent event){
        EntityRenderers.register(Mobregistries.BODY_SNATCHER.get(), Bodysnatcherrenderer::new);
        EntityRenderers.register(Mobregistries.OPOSSUM.get(), Opossumrenderer::new);
        EntityRenderers.register(Mobregistries.BASALTISK.get(), Basaltliskrender::new);
        EntityRenderers.register(Mobregistries.VOLCANOBACK.get(), Volcanobackrender::new);
    EntityRenderers.register(Mobregistries.FIREHEAP.get(), LavaRender::new);
   EntityRenderers.register(Mobregistries.MORAY.get(), Morayrender::new);
    EntityRenderers.register(Mobregistries.MORAYJAW.get(), Morayjawrender::new);
    EntityRenderers.register(Mobregistries.PADDLEFISH.get(),Paddlefishrender::new);
    EntityRenderers.register(Mobregistries.SOULCATCHER.get(),Soulcatcherrender::new);
    EntityRenderers.register(Mobregistries.SOULPROJECTILE.get(),Soulballrenderer::new);
   EntityRenderers.register(Mobregistries.BOWFIN.get(),Bowfinrender::new);
   EntityRenderers.register(Mobregistries.PRONGHORN.get(),Pronghornrenderer::new);
   EntityRenderers.register(Mobregistries.THRESHER.get(),ThresherSharkRender::new);
   EntityRenderers.register(Mobregistries.STUNWAVE.get(),StunwaveRender::new);
  EntityRenderers.register(Mobregistries.OMNIDENS.get(),Omnidensrender::new);
  EntityRenderers.register(Mobregistries.BOOMERANG.get(),BoomerangRender::new);
  EntityRenderers.register(Mobregistries.GESYER.get(),Whirlpoolrender::new);
    }






}
