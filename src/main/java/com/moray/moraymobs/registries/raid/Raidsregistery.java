package com.moray.moraymobs.registries.raid;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Raidsregistery {



    public static ArrayList<Integer> amountforambgolem(){

      ArrayList<Integer> ambgolem=new ArrayList<>();

      int[] amounts= new int[]{0,0,1,1,0,2,0,2};

       for (int ints:amounts){
        ambgolem.add(ints);}
        return ambgolem;
    }

    public static final EnumProxy<Raid.RaiderType> AMBERGOLEM = new EnumProxy<>(Raid.RaiderType.class, getRaider(Mobregistries.AMBERGOLEM.get()), getWaves(amountforambgolem()));

        public static Supplier<EntityType<?>> getRaider(EntityType<?> type) {
            return () -> type;
        }


        public static int[] getWaves(List<? extends Integer> listValue) {
            int[] intArray = new int[9];
            for (int i = 0; i < listValue.size(); i++) {
                intArray[i] = listValue.get(i);
            }

            return intArray;
        }

}
