package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.block.blockentity.Largecrystalentity;
import com.moray.moraymobs.block.blockentity.Smallcrystalentity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Morayblockentities {

    final public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE=DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, MorayMobs.MODID);

    public static final Supplier<BlockEntityType<Largecrystalentity>> LARGE_CRYSTAL_ENTITY =
            BLOCK_ENTITY_TYPE.register("large_crystal_entity", () ->
                    BlockEntityType.Builder.of(Largecrystalentity::new,
                            Blockregistrires.LARGE_CRYSTAL.get()).build(null));

    public static final Supplier<BlockEntityType<Smallcrystalentity>> SMALL_CRYSTAL_ENTITY =
            BLOCK_ENTITY_TYPE.register("small_crystal_entity", () ->
                    BlockEntityType.Builder.of(Smallcrystalentity::new,
                            Blockregistrires.SMALL_CRYSTAL.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITY_TYPE.register(bus);
    }


}
