package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.Omnidensarmor;
import com.moray.moraymobs.rendersandmodels.model.OmnidensArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class OmnidensArmorRenderer extends GeoArmorRenderer<Omnidensarmor> {
    public OmnidensArmorRenderer() {
        super(new OmnidensArmorModel());
    }
}
