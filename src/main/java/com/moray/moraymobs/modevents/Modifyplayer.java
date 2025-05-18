package com.moray.moraymobs.modevents;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class Modifyplayer {
    //private static final Holder<Attribute> BASE_ENTITY_INTERACTION_RANGE = register("player.entity_interaction_range", (new RangedAttribute("attribute.name.player.entity_interaction_range", 4.5, 0.0, 64.0)).setSyncable(true));;
    public static final ResourceLocation BASE_ENTITY_INTERACTION_RANGE_ID= ResourceLocation.withDefaultNamespace("base_attack_range");
    private static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ResourceLocation.withDefaultNamespace(name), attribute);
    }

}
