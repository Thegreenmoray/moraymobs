package com.moray.moraymobs.tags;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class MorayKeys {

public static final TagKey<EntityType<?>> ASSIMILABLE= EntityTag("assimilable");
public static final TagKey<EntityType<?>> IS_SPOTTABLE=EntityTag("canbespotted");
public static final TagKey<DamageType> PAWPAWBOMB=DamageTag("pawpawbomb");
public static final TagKey<Item> IS_GOLDEN=ItemTag("is_golden");
    private static TagKey<EntityType<?>> EntityTag(String name){
    return TagKey.create(Registries.ENTITY_TYPE,ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,name));
    }

    private static TagKey<DamageType> DamageTag(String name){
        return TagKey.create(Registries.DAMAGE_TYPE,ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,name));
    }


    private static TagKey<Item> ItemTag(String name){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,name));
    }

}
