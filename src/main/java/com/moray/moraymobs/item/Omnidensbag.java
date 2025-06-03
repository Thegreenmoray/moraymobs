package com.moray.moraymobs.item;

import com.moray.moraymobs.item.basis.Boss_bag;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

public class Omnidensbag extends Boss_bag {
    public Omnidensbag(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
            if (player instanceof ServerPlayer) {
                for (int i = 0; i < 2; i++) {
                    armordrops(player,itemstack);
                }
                 weapondrops(player,itemstack);

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }


            }

            return InteractionResultHolder.consume(itemstack);
        }


private void armordrops(Player player,ItemStack itemstack){

    int random = player.level().random.nextInt(4);
    switch (random) {
        case 0 -> {
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.OMNIDENS_HELMET.toStack());
            } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_HELMET.toStack())) {
                player.drop(Itemregististeries.OMNIDENS_HELMET.toStack(), false);
            }


        }
        case 1 -> {
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.OMNIDENS_CHESTPLATE.toStack());
            } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_CHESTPLATE.toStack())) {
                player.drop(Itemregististeries.OMNIDENS_CHESTPLATE.toStack(), false);
            }


        }
        case 2 -> { if (itemstack.isEmpty()) {
            player.addItem(Itemregististeries.OMNIDENS_LEGGINGS.toStack());
        } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_LEGGINGS.toStack())) {
            player.drop(Itemregististeries.OMNIDENS_LEGGINGS.toStack(), false);
        }




        }
        case 3 ->{
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.OMNIDENS_BOOTS.toStack());
            } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_BOOTS.toStack())) {
                player.drop(Itemregististeries.OMNIDENS_BOOTS.toStack(), false);
            }



        }

    }

}


private void weapondrops(Player player,ItemStack itemstack){
    int random = player.level().random.nextInt(3);

    switch (random) {
        case 0 -> {
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.OMNIDENS_SWORD.toStack());
            } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_SWORD.toStack())) {
                player.drop(Itemregististeries.OMNIDENS_SWORD.toStack(), false);
            }


        }
        case 1 -> {
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.OMNIDENS_SHOVEL.toStack());
            } else if (!player.getInventory().add(Itemregististeries.OMNIDENS_SHOVEL.toStack())) {
                player.drop(Itemregististeries.OMNIDENS_SHOVEL.toStack(), false);
            }


        }
        case 2 -> {
            if (itemstack.isEmpty()) {
                player.addItem(Itemregististeries.BUCCANEER.toStack());
            } else if (!player.getInventory().add(Itemregististeries.BUCCANEER.toStack())) {
                player.drop(Itemregististeries.BUCCANEER.toStack(), false);
            }
        }

    }


        }



}
