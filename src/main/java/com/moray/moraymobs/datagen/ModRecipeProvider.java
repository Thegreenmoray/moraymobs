package com.moray.moraymobs.datagen;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.registries.Blockregistrires;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {


    private RecipeOutput output;

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
    }




    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blockregistrires.BLOCK_OF_SCALES.get())
                .pattern("$$$")
                .pattern("$$$")
                .pattern("$$$").define('$',Itemregististeries.BEETLE_SCALE.get())
                .unlockedBy(getHasName(Itemregististeries.BEETLE_SCALE.get()),has(Itemregististeries.BEETLE_SCALE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Itemregististeries.BEETLE_SCALE.get(),9)
                .requires(Blockregistrires.BLOCK_OF_SCALES.get())
                .unlockedBy(getHasName(Blockregistrires.BLOCK_OF_SCALES.get()), has(Blockregistrires.BLOCK_OF_SCALES.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.BEETLE_BOOTS.get())
                .pattern("   ")
                .pattern("$ $")
                .pattern("$ $").define('$',Itemregististeries.BEETLE_SCALE.get())
                .unlockedBy(getHasName(Itemregististeries.BEETLE_SCALE.get()),has(Itemregististeries.BEETLE_SCALE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.BEETLE_LEGGINGS.get())
                .pattern("$$$")
                .pattern("$ $")
                .pattern("$ $").define('$',Itemregististeries.BEETLE_SCALE.get())
                .unlockedBy(getHasName(Itemregististeries.BEETLE_SCALE.get()),has(Itemregististeries.BEETLE_SCALE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.BEETLE_HELMET.get())
                .pattern("$$$")
                .pattern("$ $")
                .pattern("   ").define('$',Itemregististeries.BEETLE_SCALE.get())
                .unlockedBy(getHasName(Itemregististeries.BEETLE_SCALE.get()),has(Itemregististeries.BEETLE_SCALE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.BEETLE_CHESTPLATE.get())
                .pattern("$ $")
                .pattern("$$$")
                .pattern("$$$").define('$',Itemregististeries.BEETLE_SCALE.get())
                .unlockedBy(getHasName(Itemregististeries.BEETLE_SCALE.get()),has(Itemregististeries.BEETLE_SCALE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.EEL_WHIP.get())
                .pattern("$  ")
                .pattern(" **")
                .pattern(" *&").define('$',Itemregististeries.JAW.get()).define('*', Items.BONE).define('&',Items.LEATHER)
                .unlockedBy(getHasName(Itemregististeries.JAW.get()),has(Itemregististeries.JAW.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Itemregististeries.SOULBEADRING.get())
                .pattern("*&*")
                .pattern("&$&")
                .pattern("*&*").define('$',Itemregististeries.SOULJEWEL.get()).define('*', Items.STRING).define('&',Items.SOUL_SOIL)
                .unlockedBy(getHasName(Itemregististeries.SOULJEWEL.get()),has(Itemregististeries.SOULJEWEL.get()))
                .save(recipeOutput);


       ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,Itemregististeries.STUNGUN.get())
               .pattern("%@ ")
               .pattern("@*@")
               .pattern("  *").define('@',Items.LAPIS_LAZULI).define('*',Items.IRON_INGOT)
               .define('%',Itemregististeries.BOTTLE_OF_SHOCK.get()).
               unlockedBy(getHasName(Itemregististeries.BOTTLE_OF_SHOCK.get()),has(Itemregististeries.BOTTLE_OF_SHOCK.get()))
               .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE,2)
                .requires(Itemregististeries.ANTLER.get())
                .unlockedBy(getHasName(Itemregististeries.ANTLER.get()), has(Itemregististeries.ANTLER.get()))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,Itemregististeries.TOP_KEY.get())
                .pattern("@@@")
                .pattern("***")
                .pattern("%%%").define('@',Itemregististeries.TOP_KEY_PART).define('*',Itemregististeries.MIDDLE_KEY_PART)
                .define('%',Itemregististeries.BOTTOM_KEY_PART.get()).
                unlockedBy(getHasName(Itemregististeries.MIDDLE_KEY_PART.get()),has(Itemregististeries.MIDDLE_KEY_PART.get()))
                .save(recipeOutput);



cookingRecipes(recipeOutput,"smelted", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new,200);
        cookingRecipes(recipeOutput,"smoked", RecipeSerializer.SMOKING_RECIPE,SmokingRecipe::new, 100);
cookingRecipes(recipeOutput,"campfired", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new,500);


    }

    private <T extends AbstractCookingRecipe> void cookingRecipes(RecipeOutput recipeOutput,String processName, RecipeSerializer<T> process, AbstractCookingRecipe.Factory<T> factory, int smeltingTime) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(Itemregististeries.RAW_BOWFIN.get()), RecipeCategory.FOOD, Itemregististeries.COOKED_BOWFIN.get(), 0.35F, smeltingTime, process, factory).unlockedBy("has_food", has(Itemregististeries.RAW_BOWFIN.get())).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"food/" + processName + "_meef"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(Itemregististeries.RAW_PRONGHORN.get()), RecipeCategory.FOOD, Itemregististeries.COOKED_PRONGHORN.get(), 0.35F, smeltingTime, process, factory).unlockedBy("has_food", has(Itemregististeries.RAW_PRONGHORN.get())).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"food/" + processName + "_venison"));
    }


    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, MorayMobs.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
