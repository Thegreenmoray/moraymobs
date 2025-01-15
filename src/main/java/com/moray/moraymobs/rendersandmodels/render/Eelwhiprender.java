package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.item.Eelwhip;
import com.moray.moraymobs.rendersandmodels.model.Eelwhipmodel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Eelwhiprender extends GeoItemRenderer<Eelwhip> {
    public Eelwhiprender() {
        super(new Eelwhipmodel());
    }
}
