package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MorayMobs.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput,lookupProvider));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput,lookupProvider));
        generator.addProvider(event.includeClient(), new MorayBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MorayItemModelProvider(packOutput, existingFileHelper));

        MorayBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new MorayBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new MorayItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new MorayDatapackprovider(packOutput, lookupProvider));
   generator.addProvider(event.includeServer(),new EntityTagGenerator(packOutput,lookupProvider));
    }
}