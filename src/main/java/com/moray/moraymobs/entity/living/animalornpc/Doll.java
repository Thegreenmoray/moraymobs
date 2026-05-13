package com.moray.moraymobs.entity.living.animalornpc;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.InventoryCarrier;

public class Doll implements InventoryCarrier {
    private final SimpleContainer inventory = new SimpleContainer(8);


    @Override
    public SimpleContainer getInventory() {
        return inventory;
    }
}
