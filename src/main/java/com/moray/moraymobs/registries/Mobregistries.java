package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.*;
import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import com.moray.moraymobs.entity.living.monster.*;
import com.moray.moraymobs.entity.projectiles.*;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Bouncy_ball_Friend;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Friendly_Boomerrang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Mobregistries {
  final public static DeferredRegister<EntityType<?>> ENTITY_TYPE=DeferredRegister.create(
          BuiltInRegistries.ENTITY_TYPE, MorayMobs.MODID);

    final public static Supplier<EntityType<Body_Snatcher>> BODY_SNATCHER=
            ENTITY_TYPE.register("bodysnatcher",()->EntityType.Builder.of(Body_Snatcher::new, MobCategory.MONSTER)
                    .sized(0.5F,2).build("bodysnatcher"));

    final public static Supplier<EntityType<Opossum>> OPOSSUM=
            ENTITY_TYPE.register("opossum",()->EntityType.Builder.of(Opossum::new, MobCategory.CREATURE)
                    .sized(1,0.5F).build( "opossum"));

    final public static Supplier<EntityType<Basaltlisk>> BASALTISK=
            ENTITY_TYPE.register("basaltlisk",()->EntityType.Builder.of(Basaltlisk::new, MobCategory.CREATURE)
                    .sized(2,0.5F).fireImmune().build("basaltlisk"));

    final public static Supplier<EntityType<Volcanoback>> VOLCANOBACK=
            ENTITY_TYPE.register("volcanoback",()->EntityType.Builder.of(Volcanoback::new, MobCategory.MONSTER)
                    .sized(4,2F).fireImmune().build("volcanoback"));

  final public static Supplier<EntityType<Fireheap>> FIREHEAP=
          ENTITY_TYPE.register("fireheap",()->EntityType.Builder.<Fireheap>of(Fireheap::new, MobCategory.MISC)
                  .sized(2,0.5F).fireImmune().build("fireheap"));

  final public static Supplier<EntityType<Moray>> MORAY=
          ENTITY_TYPE.register("bonymoray",()->EntityType.Builder.of(Moray::new, MobCategory.MONSTER)
                  .sized(3,1F).build("bonymoray"));

  final public static Supplier<EntityType<Morayjaw>> MORAYJAW=
          ENTITY_TYPE.register("bonymorayjaw",()->EntityType.Builder.<Morayjaw>of(Morayjaw::new, MobCategory.MISC)
                  .sized(0.5f,0.5F).build("bonymorayjaw"));

  final public static Supplier<EntityType<LavaPaddleFish>> PADDLEFISH=
          ENTITY_TYPE.register("paddlefish",()->EntityType.Builder.of(LavaPaddleFish::new, MobCategory.WATER_CREATURE)
                  .sized(0.5f,0.5F).fireImmune().build("paddlefish"));

  final public static Supplier<EntityType<Soulpiece>> SOULPROJECTILE=
          ENTITY_TYPE.register("soulprojectile",()->EntityType.Builder.<Soulpiece>of(Soulpiece::new, MobCategory.MISC)
                  .sized(1,2F).fireImmune().build("soulprojectile"));

  final public static Supplier<EntityType<Soulcatcher>> SOULCATCHER=
          ENTITY_TYPE.register("soulcatcher",()->EntityType.Builder.of(Soulcatcher::new, MobCategory.MONSTER)
                  .sized(1.5F,2.5F).fireImmune().build("soulcatcher"));

  final public static Supplier<EntityType<Enderbowfin>> BOWFIN=
          ENTITY_TYPE.register("bowfin",()->EntityType.Builder.of(Enderbowfin::new, MobCategory.WATER_CREATURE)
                  .sized(0.5f,0.5F).build("bowfin"));

  final public static Supplier<EntityType<Pronghorn>> PRONGHORN=
          ENTITY_TYPE.register("pronghorn",()->EntityType.Builder.of(Pronghorn::new, MobCategory.CREATURE)
                  .sized(1.5F,1.5F).build("pronghorn"));

  final public static Supplier<EntityType<Thresher_shark>> THRESHER=
          ENTITY_TYPE.register("threshershark",()->EntityType.Builder.of(Thresher_shark::new, MobCategory.WATER_CREATURE)
                  .sized(1.5F,1F).build("threshershark"));

  final public static Supplier<EntityType<Stunwave>> STUNWAVE=
          ENTITY_TYPE.register("stunwave",()->EntityType.Builder.<Stunwave>of(Stunwave::new, MobCategory.MISC)
                  .sized(1.5f,1F).fireImmune().build("stunwave"));

  final public static Supplier<EntityType<Boomerrang>> BOOMERANG=
          ENTITY_TYPE.register("boomerang",()->EntityType.Builder.<Boomerrang>of(Boomerrang::new, MobCategory.MISC)
                  .sized(1.5f,1F).fireImmune().build("boomerang"));

  final public static Supplier<EntityType<Geyser>> GESYER=
          ENTITY_TYPE.register("geyser",()->EntityType.Builder.<Geyser>of(Geyser::new, MobCategory.MISC)
                  .sized(1f,10F).fireImmune().build("geyser"));

  final public static Supplier<EntityType<Omnidens>> OMNIDENS=
          ENTITY_TYPE.register("omnidens",()->EntityType.Builder.of(Omnidens::new, MobCategory.MONSTER)
                  .sized(5F,1F).build("omnidens"));

  final public static Supplier<EntityType<Microdictyon>> MICRODICTYON=
          ENTITY_TYPE.register("microdictyon",()->EntityType.Builder.of(Microdictyon::new, MobCategory.MONSTER)
                  .sized(2F,1F).build("microdictyon"));

  final public static Supplier<EntityType<Microprojectile>> MICRODICTYON_PROJECTILE=
          ENTITY_TYPE.register("microdictyon_projectiles",()->EntityType.Builder.<Microprojectile>of(Microprojectile::new, MobCategory.MISC)
                  .sized(1F,1F).fireImmune().build("microdictyon_projectiles"));

  final public static Supplier<EntityType<Schinderhannes>> SCHINDERHANNES=
          ENTITY_TYPE.register("schinderhannes",()->EntityType.Builder.of(Schinderhannes::new, MobCategory.MONSTER)
                  .sized(1F,1F).build("schinderhannes"));

  final public static Supplier<EntityType<Walliserops>> WALLISEROPS=
          ENTITY_TYPE.register("walliserops",()->EntityType.Builder.of(Walliserops::new, MobCategory.MONSTER)
                  .sized(1F,1F).build("walliserops"));

  final public static Supplier<EntityType<Bouncy_ball>> BOUNCE_BALL=
          ENTITY_TYPE.register("bouncy_ball",()->EntityType.Builder.<Bouncy_ball>of(Bouncy_ball::new, MobCategory.MISC)
                  .sized(1F,1F).fireImmune().build("bouncy_ball"));

  final public static Supplier<EntityType<Sea_Mine>> SEA_MINE=
          ENTITY_TYPE.register("sea_mine",()->EntityType.Builder.<Sea_Mine>of(Sea_Mine::new, MobCategory.MISC)
                  .sized(2F,2F).fireImmune().build("sea_mine"));

  final public static Supplier<EntityType<Friendly_Boomerrang>> BOOMERANG_FRIEND=
          ENTITY_TYPE.register("boomerang_f",()->EntityType.Builder.<Friendly_Boomerrang>of(Friendly_Boomerrang::new, MobCategory.MISC)
                  .sized(1.5f,1F).fireImmune().build("boomerang_f"));

  final public static Supplier<EntityType<Bouncy_ball_Friend>> BOUNCE_BALL_FRIEND=
          ENTITY_TYPE.register("bouncy_ball_f",()->EntityType.Builder.<Bouncy_ball_Friend>of(Bouncy_ball_Friend::new, MobCategory.MISC)
                  .sized(1F,1F).fireImmune().build("bouncy_ball_f"));

  final public static Supplier<EntityType<Amber_golem>> AMBERGOLEM=
          ENTITY_TYPE.register("ambergolem",()->EntityType.Builder.of(Amber_golem::new, MobCategory.MONSTER)
                  .sized(1,3F).build("ambergolem"));





  public static void register(IEventBus bus){
    ENTITY_TYPE.register(bus);
}


}
