package com.moray.moraymobs.registries;



import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Blockregistrires {

        public static final DeferredRegister.Blocks BLOCKS =
                DeferredRegister.createBlocks( MorayMobs.MODID);


    public static final DeferredBlock<Block> BASALTLAMP=registerBlock("basaltlamp",
            ()->new Basaltlightblock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE).lightLevel((w)->10).instabreak()));

    public static final DeferredBlock<Block> BLOCK_OF_SCALES=registerBlock("blockscale",
            ()->new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> END_CELSOSIA=registerBlock("endercelosia",
            ()->new EndFlowerBlock(Effectregisteries.STUN,5,BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM).noCollission().instabreak()));

    public static final DeferredBlock<Block> END_CELSOSIA_POTTED=BLOCKS.register("potted_endercelosia",
            ()->new FlowerPotBlock(()->(FlowerPotBlock)Blocks.FLOWER_POT,Blockregistrires.END_CELSOSIA,BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM).instabreak()));

    public static final DeferredBlock<Block> END_GRASS=registerBlock("chorousgrass",
            ()->new EndGrass(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).noCollission().instabreak()));

    public static final DeferredBlock<Block> PADDED_MOSS=BLOCKS.register("chorousmoss",
            ()->new Voidmoss(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollission().instabreak()));

    public static final DeferredBlock<Block> SHULKERFRUIT_CROP=BLOCKS.register("shulkerberrycrop",
            ()->new Shulkerberrycrop(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollission().noOcclusion().instabreak()));
    public static final DeferredBlock<Block> LARGE_CRYSTAL =BLOCKS.register("large_orb",
            () -> new Largecrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));;

    public static final DeferredBlock<Block> SMALL_CRYSTAL =BLOCKS.register("small_orb",
            () -> new Smallcrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));;

    public static final DeferredBlock<Block> KEYTOPBLOCK= registerBlock("keytopblock",
            () -> new Keytopblock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));

    public static final DeferredBlock<Block> KEYDOWNBLOCK= registerBlock("keydownblock",
            () -> new Keydownblock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));

    public static final DeferredBlock<Block> CRACKEDDARKPRISMANE= registerBlock("dark_prismarine_gate",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
         Itemregististeries.ITEM.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
