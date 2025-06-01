package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.SmallCrystalitem;
import com.moray.moraymobs.rendersandmodels.model.SmallCrystalitemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SmallCrystalitemrender extends GeoItemRenderer<SmallCrystalitem> {

    public SmallCrystalitemrender() {
        super(new SmallCrystalitemModel());
    }
}
