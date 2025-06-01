package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.block.blockentity.Largecrystalentity;
import com.moray.moraymobs.block.blockentity.Smallcrystalentity;
import com.moray.moraymobs.rendersandmodels.model.LargeCrystalitemModel;
import com.moray.moraymobs.rendersandmodels.model.SmallCrystalmodel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SmallCrystalrenderer extends GeoBlockRenderer<Smallcrystalentity> {
    public SmallCrystalrenderer(BlockEntityRendererProvider.Context model) {
        super(new SmallCrystalmodel());
    }
}
