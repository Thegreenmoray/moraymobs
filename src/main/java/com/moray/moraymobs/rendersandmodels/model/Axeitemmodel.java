package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.ItemDullahanaxe;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Axeitemmodel extends GeoModel<ItemDullahanaxe> {
    @Override
    public ResourceLocation getModelResource(ItemDullahanaxe animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/axeitem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemDullahanaxe animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/axeitem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemDullahanaxe animatable) {
        return null;
    }
}
