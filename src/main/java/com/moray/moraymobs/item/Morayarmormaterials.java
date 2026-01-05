package com.moray.moraymobs.item;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class Morayarmormaterials  {
    public class ModArmorMaterials {
        public static final Holder<ArmorMaterial> BEETLE_ARMOR_MATERIAL = register("beetle",
                Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                    attribute.put(ArmorItem.Type.BOOTS, 3);
                    attribute.put(ArmorItem.Type.LEGGINGS, 5);
                    attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                    attribute.put(ArmorItem.Type.HELMET, 3);
                    attribute.put(ArmorItem.Type.BODY, 8);
                }), 20, 1f, 0.0f, () -> Itemregististeries.BEETLE_SCALE.get());


        public static final Holder<ArmorMaterial> OMNIDENS_ARMOR_MATERIAL = register("omnidens",
                Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                    attribute.put(ArmorItem.Type.BOOTS, 3);
                    attribute.put(ArmorItem.Type.LEGGINGS, 6);
                    attribute.put(ArmorItem.Type.CHESTPLATE, 8);
                    attribute.put(ArmorItem.Type.HELMET, 4);
                    attribute.put(ArmorItem.Type.BODY, 3);
                }), 25, 2f, 1.0f, () -> Items.NAUTILUS_SHELL);




        private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                      int enchantability, float toughness, float knockbackResistance,
                                                      Supplier<Item> ingredientItem) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID, name);
            Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
            Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
            List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

            EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
            for (ArmorItem.Type type : ArmorItem.Type.values()) {
                typeMap.put(type, typeProtection.get(type));
            }

            return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                    new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
        }
    }

}
