package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.Largecrystalblockitem;
import com.moray.moraymobs.rendersandmodels.model.LargeCrystalitemModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LargeCrystalitemrenderer extends GeoItemRenderer<Largecrystalblockitem> {
    public LargeCrystalitemrenderer() {
        super(new LargeCrystalitemModel());
    }
}
