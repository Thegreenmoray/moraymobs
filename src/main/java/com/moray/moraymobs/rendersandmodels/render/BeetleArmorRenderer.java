package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.Beetlearmor;
import com.moray.moraymobs.rendersandmodels.model.BeetleArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BeetleArmorRenderer extends GeoArmorRenderer<Beetlearmor> {
    public BeetleArmorRenderer() {
        super(new BeetleArmorModel());
    }
}
