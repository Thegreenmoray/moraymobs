package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.Stungun;
import com.moray.moraymobs.rendersandmodels.model.Stungunmodel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Stungunrender extends GeoItemRenderer<Stungun> {
    public Stungunrender() {
        super(new Stungunmodel());
    }
}
