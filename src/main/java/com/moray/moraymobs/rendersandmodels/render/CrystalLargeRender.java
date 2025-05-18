package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.block.blockentity.Largecrystalentity;
import com.moray.moraymobs.rendersandmodels.model.CrystalLargeModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CrystalLargeRender extends GeoBlockRenderer<Largecrystalentity> {
    public CrystalLargeRender(BlockEntityRendererProvider.Context model) {
        super(new CrystalLargeModel());
    }
}
