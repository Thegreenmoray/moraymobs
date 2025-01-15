package com.moray.moraymobs.registries;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class Moraytab {

public static final DeferredRegister<CreativeModeTab> CREATIVE_MODES=
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MorayMobs.MODID);

public static final Supplier<CreativeModeTab> MORAYTAB=
        CREATIVE_MODES.register("moray_tab",()-> CreativeModeTab.builder()
                .icon(()->new ItemStack(Blockregistrires.BASALTLAMP.get().asItem()))
                .title(Component.translatable("creative.moray_tab"))
                .displayItems(((itemDisplayParameters, output) ->{
                        output.accept(Blockregistrires.BASALTLAMP.get());
                        output.accept(Blockregistrires.BLOCK_OF_SCALES.get());
                        output.accept(Blockregistrires.END_CELSOSIA.get());
for(DeferredHolder<Item, ? extends Item> itemSupplier:Itemregististeries.ITEM.getEntries()){output.accept(itemSupplier.get());}})).build());


public static void register(IEventBus eventBus){
    CREATIVE_MODES.register(eventBus);
}
}
