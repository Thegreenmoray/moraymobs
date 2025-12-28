package com.moray.moraymobs.datagen;

import com.moray.moraymobs.registries.Itemregististeries;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class MorayMobLootTable extends EntityLootSubProvider {


   protected MorayMobLootTable(HolderLookup.Provider provider) {
       super(FeatureFlags.REGISTRY.allFlags(),provider);
    }

    @Override
    public void generate() {
        this.add(Mobregistries.OPOSSUM.get(), LootTable.lootTable());
   this.add(Mobregistries.BASALTISK.get(),LootTable.lootTable());
   this.add(Mobregistries.BODY_SNATCHER.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.BRAIN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0,1))))));
  this.add(Mobregistries.VOLCANOBACK.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.BEETLE_SCALE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(5,11))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
   this.add(Mobregistries.MORAY.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,4))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1))).when(LootItemKilledByPlayerCondition.killedByPlayer()))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.JAW.get())).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries,0.25F, 0.01F))));
    this.add(Mobregistries.PADDLEFISH.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.PADDLEFISH_FOOD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1))))));
  this.add(Mobregistries.SOULCATCHER.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.SOULJEWEL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries,0.1f,0.75f)))));
   this.add(Mobregistries.BOWFIN.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.RAW_BOWFIN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1))))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0.0F, 1.0F))).apply((SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))));
  this.add(Mobregistries.PRONGHORN.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.RAW_PRONGHORN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,3))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,3))))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Itemregististeries.ANTLER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0,2))))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0,2))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(1,3)))));
  this.add(Mobregistries.THRESHER.get(),LootTable.lootTable());
  this.add(Mobregistries.OMNIDENS.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
          .add(LootItem.lootTableItem(Itemregististeries.OMNI_BAG)
  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))))));
 this.add(Mobregistries.MICRODICTYON.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
         .add(LootItem.lootTableItem(Itemregististeries.BOTTOM_KEY_PART)
                 .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))))));
this.add(Mobregistries.SCHINDERHANNES.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
        .add(LootItem.lootTableItem(Itemregististeries.TOP_KEY_PART)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))))));
 this.add(Mobregistries.WALLISEROPS.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
         .add(LootItem.lootTableItem(Itemregististeries.MIDDLE_KEY_PART)
                 .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))))));

 this.add(Mobregistries.AMBERGOLEM.get(),LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.QUARTZ).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,4))).
                apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1))).when(LootItemKilledByPlayerCondition.killedByPlayer()))).withPool(LootPool.lootPool().
                setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.BLAZE_POWDER)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries,0.50F, 0.02F)).add(LootItem.lootTableItem(Items.REDSTONE)).
                when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries,0.75F, 0.01F)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2,4)))
                .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries,0.75F, 0.01F)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2,4)))));

 this.add(Mobregistries.LESSER_TESSERACT.get(),LootTable.lootTable().
        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(Items.OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(0,4)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries,UniformGenerator.between(0,1)))).
                add(LootItem.lootTableItem(Items.ENDER_EYE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,1))))));

        this.add(Mobregistries.SPRIGGAN.get(),LootTable.lootTable());

   }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Mobregistries.ENTITY_TYPE.getEntries().stream().map(DeferredHolder::value);
    }}

