package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class Itemregististeries {

    final public static DeferredRegister.Items ITEM=DeferredRegister.createItems(MorayMobs.MODID);

    final public static DeferredItem<Item> OPPOSUM_SPAWN_EGG=ITEM.register
            ("spawn_opposum",()-> new DeferredSpawnEggItem(Mobregistries.OPOSSUM,
                    0xAEB2AF,0xf1e7de,new Item.Properties()));

    final public static DeferredItem<Item> BODYSNATCHER_SPAWN_EGG=ITEM.register
            ("spawn_body_snatcher",()-> new DeferredSpawnEggItem(Mobregistries.BODY_SNATCHER,0xf3b5b8,0xc41f26,new Item.Properties()));

    final public static DeferredItem<Item> BASALTlISK_SPAWN_EGG=ITEM.register
            ("spawn_basaltlisk",()-> new DeferredSpawnEggItem(Mobregistries.BASALTISK,0x575d5e,0x545058,new Item.Properties()));

    final public static DeferredItem<Item> VOLCANOBACK_SPAWN_EGG=ITEM.register
            ("spawn_volcanoback",()-> new DeferredSpawnEggItem(Mobregistries.VOLCANOBACK,0x652828,0xc65626,new Item.Properties()));
    final public static DeferredItem<Item> MORAY_SPAWN_EGG=ITEM.register
            ("spawn_moray",()-> new DeferredSpawnEggItem(Mobregistries.MORAY,0xbcbcbc,0xadabad,new Item.Properties()));

    final public static DeferredItem<Item> PADDLE_SPAWN_EGG=ITEM.register
            ("spawn_lava_paddlefish",()-> new DeferredSpawnEggItem(Mobregistries.PADDLEFISH,0xff2500,0xe56520,new Item.Properties()));
    final public static DeferredItem<Item> SOULCATCHER_SPAWN_EGG=ITEM.register
            ("spawn_soulcatcher",()-> new DeferredSpawnEggItem(Mobregistries.SOULCATCHER,0x00FFFF,0x964B00,new Item.Properties()));

    final public static DeferredItem<Item> BOWFIN_SPAWN_EGG=ITEM.register
            ("spawn_endbowfin",()-> new DeferredSpawnEggItem(Mobregistries.BOWFIN,0xEEF6B4,0x3f1b40,new Item.Properties()));

    final public static DeferredItem<Item> PRONGHORN_SPAWN_EGG=ITEM.register
            ("spawn_pronghorn",()-> new DeferredSpawnEggItem(Mobregistries.PRONGHORN,0xcfa141,0xb39671,new Item.Properties()));

    final public static DeferredItem<Item>  THRESHERSHARK_SPAWN_EGG=ITEM.register
            ("spawn_thresher",()-> new DeferredSpawnEggItem(Mobregistries.THRESHER,0xADD8E6,0xFFFFFF,new Item.Properties()));

    final public static DeferredItem<Item> BRAIN=ITEM.register("brain",
           ()->new Brainitem(new Item.Properties().stacksTo(8)));

    final public static DeferredItem<Item> JAW= ITEM.register("jawloose",
            ()->new Item(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> END_SEED=ITEM.register("shulkerberryseed",
            ()->new ItemNameBlockItem(Blockregistrires.SHULKERFRUIT_CROP.get(),new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> SHULKERBERRY=ITEM.register("shulkerberry",
            ()->new Shulkerfruit(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(3).build()).stacksTo(64)));

    final public static DeferredItem<Item> RAW_BOWFIN= ITEM.register("rawbowfin",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(3).build()).stacksTo(64)));

    final public static DeferredItem<Item> COOKED_BOWFIN= ITEM.register("cookedbowfin",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(7).build()).stacksTo(64)));

    final public static DeferredItem<Item> RAW_PRONGHORN= ITEM.register("rawpronghorn",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(2).build()).stacksTo(64)));

    final public static DeferredItem <Item> COOKED_PRONGHORN= ITEM.register("cookedpronghorn",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(10).build()).stacksTo(64)));

    final public static DeferredItem<Item> MOSS=ITEM.register("chorousmoss",
            ()->new PlaceOnWaterBlockItem(Blockregistrires.PADDED_MOSS.get(),
                    new Item.Properties().stacksTo(64)));


    final public static DeferredItem<Item> BEETLE_SCALE= ITEM.register("scale",
            ()->new Item(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> BEETLE_HELMET=ITEM.register("beetlemask",()->new Beetlearmor(
            Morayarmormaterials.ModArmorMaterials.BEETLE_ARMOR_MATERIAL ,ArmorItem.Type.HELMET,new Item.Properties().fireResistant().stacksTo(1)));

    final public static DeferredItem<Item> BEETLE_CHESTPLATE=ITEM.register("beetlechestplate",
            ()->new Beetlearmor(Morayarmormaterials.ModArmorMaterials.BEETLE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,new Item.Properties().fireResistant().stacksTo(1)));

    final public static DeferredItem<Item> BEETLE_LEGGINGS=ITEM.register("beetleleggings",
            ()->new Beetlearmor(Morayarmormaterials.ModArmorMaterials.BEETLE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,new Item.Properties().fireResistant().stacksTo(1)));

    final public static DeferredItem<Item> BEETLE_BOOTS=ITEM.register("beetleboots",
            ()->new Beetlearmor(Morayarmormaterials.ModArmorMaterials.BEETLE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,new Item.Properties().fireResistant().stacksTo(1)));

    final public static DeferredItem<Item> EEL_WHIP=ITEM.register("eelwhip",
            ()->new Eelwhip(Tiers.WOOD,new Item.Properties().attributes(Eelwhip.createAttributes(Tiers.WOOD,3f,4f)).durability(450)));

    final public static DeferredItem<Item> BASALT_CRYSTAL=ITEM.register("basalt_crystal",
            ()->new Cagedbasalitisk(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> BUCKETED_PADDLEFISH=ITEM.register("paddlefishbucket",
           ()->new MobBucketItem(Mobregistries.PADDLEFISH.get(),Fluids.LAVA, SoundEvents.BUCKET_EMPTY_FISH,new Item.Properties()));

    final public static DeferredItem<Item> BUCKETED_BOWFIN=ITEM.register("bowfinbucket",
            ()->new MobBucketItem( Mobregistries.BOWFIN.get(),Fluids.WATER,SoundEvents.BUCKET_EMPTY_FISH,new Item.Properties()));

    final public static DeferredItem<Item> PADDLEFISH_FOOD= ITEM.register("paddlefishfood",
            ()->new Item(new Item.Properties().fireResistant().food(new FoodProperties.Builder().nutrition(6).saturationModifier(3).build())));

    final public static DeferredItem<Item> SOULJEWEL= ITEM.register("soulbead",
            ()->new Item(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> SOULBEADRING=ITEM.register("soulbeamjewel",
            ()->new Soulbeamweapon(new Item.Properties().durability(100)));

    final public static DeferredItem<Item> ANTLER= ITEM.register("pronghornantler",
            ()->new Item(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> BOTTLE_OF_SHOCK=ITEM.register("glassofstunwave",
            ()->new Bottleofshock(new Item.Properties().stacksTo(64)));

    final public static DeferredItem<Item> STUNGUN=ITEM.register("stungun3",
            ()->new Stungun(new Item.Properties().durability(100)));

    final public static DeferredItem<Item> CRYSTAL_LARGE_ITEM = ITEM.register("large_orb",
            () -> new Largecrystalblockitem(Blockregistrires.LARGE_CRYSTAL.get(), new Item.Properties()));

    final public static DeferredItem<Item> TOP_KEY=ITEM.register("red_key",
            ()->new Item(new Item.Properties()));

    final public static DeferredItem<Item> BOTTOM_KEY=ITEM.register("yellow_key",
            ()->new Item(new Item.Properties()));

    public static void register(IEventBus bus){
        ITEM.register(bus);
    }

}
