package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.ItemDullahanaxe;
import com.moray.moraymobs.rendersandmodels.model.Axeitemmodel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Axeitemrender extends GeoItemRenderer<ItemDullahanaxe> {
    public Axeitemrender() {
        super(new Axeitemmodel());
    }
}
