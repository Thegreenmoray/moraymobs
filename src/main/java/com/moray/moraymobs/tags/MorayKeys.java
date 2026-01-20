package com.moray.moraymobs.tags;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;


public class MorayKeys {

public static final TagKey<EntityType<?>> ASSIMILABLE= EntityTag("assimilable");
public static final TagKey<EntityType<?>> IS_SPOTTABLE=EntityTag("canbespotted");
public static final TagKey<DamageType> PAWPAWBOMB=DamageTag("pawpawbomb");

    private static TagKey<EntityType<?>> EntityTag(String name){
    return TagKey.create(Registries.ENTITY_TYPE,ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,name));
    }

    private static TagKey<DamageType> DamageTag(String name){
        return TagKey.create(Registries.DAMAGE_TYPE,ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,name));
    }


   // private static TagKey<Block> BlockTag(String name){
  //      return TagKey.create(Registries.BLOCK,new ResourceLocation(MorayMobs.MODID,name));
   // }

}
