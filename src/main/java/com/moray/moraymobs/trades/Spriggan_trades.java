package com.moray.moraymobs.trades;


import com.moray.moraymobs.registries.Itemregististeries;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;


public class Spriggan_trades {

    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> SPRIGGAN_TRADES = toIntMap(ImmutableMap.of(
            1, new VillagerTrades.ItemListing[]{
                    new SellItemFactory(Items.APPLE, 4, 2, 24, 4),
                    new SellItemFactory(Items.OAK_SAPLING, 1, 1, 12, 2),
                    new SellItemFactory(Items.JUNGLE_SAPLING, 2, 1, 12, 2),
                    new SellItemFactory(Items.CHERRY_SAPLING, 2, 1, 12, 2),
                    new SellItemFactory(Itemregististeries.PAWPAW.get(), 4, 2, 24, 4),
                    new SellItemFactory(Itemregististeries.PAWPAW_BOMB.get(), 2, 16, 36, 4),
                    new SellItemFactory(Items.SNIFFER_EGG, 16, 1, 36, 4),

            //will likely sell more custom items at some point
            }
          ));



    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }


    public static class SellItemFactory implements VillagerTrades.ItemListing {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Block block, int price, int count, int maxUses, int experience) {
            this(new ItemStack(block), price, count, maxUses, experience);
        }

        

        public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
            this(stack, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return new MerchantOffer(new ItemCost(Items.AMETHYST_SHARD,this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        //Ametheystshard is stand in will likely replace with something else
        }

    }






}
