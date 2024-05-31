package dev.ftb.mods.ftbic.datagen.recipes;

import dev.ftb.mods.ftbic.datagen.FTBICRecipesGen;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;
import java.util.function.Function;

public class FTBICBatteryRecipes extends FTBICRecipesGen {
	public FTBICBatteryRecipes(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void add(Consumer<FinishedRecipe> consumer) {
		Function<TagKey<Item>, InventoryChangeTrigger.TriggerInstance> tagKeyHas = (e) -> RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(e).build());

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, SINGLE_USE_BATTERY.get())
				.unlockedBy("has_item", tagKeyHas.apply(COAL))
				.group(MODID + ":single_use_battery")
				.pattern("C")
				.pattern("R")
				.pattern("O")
				.define('C', LV_CABLE.get())
				.define('O', COAL)
				.define('R', REDSTONE)
				.save(consumer, shapedLoc("single_use_battery"));

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LV_BATTERY.get())
				.unlockedBy("has_item", tagKeyHas.apply(TIN_INGOT))
				.group(MODID + ":battery")
				.pattern(" C ")
				.pattern("TRT")
				.pattern("TRT")
				.define('C', LV_CABLE.get())
				.define('T', TIN_INGOT)
				.define('R', REDSTONE)
				.save(consumer, shapedLoc("battery"));

		SmithingTransformRecipeBuilder.smithing(Ingredient.of(LV_BATTERY.get()), Ingredient.of(ENERGY_CRYSTAL.get()), Ingredient.EMPTY, RecipeCategory.REDSTONE, MV_BATTERY.get())
				.unlocks("has_item", has(ENERGY_CRYSTAL.get()))
				.save(consumer, smithingLoc("crystal_battery"));

		SmithingTransformRecipeBuilder.smithing(Ingredient.of(MV_BATTERY.get()), Ingredient.of(GRAPHENE.get()), Ingredient.EMPTY, RecipeCategory.REDSTONE, HV_BATTERY.get())
				.unlocks("has_item", has(GRAPHENE.get()))
				.save(consumer, smithingLoc("graphene_battery"));

		SmithingTransformRecipeBuilder.smithing(Ingredient.of(HV_BATTERY.get()), Ingredient.of(IRIDIUM_ALLOY.get()), Ingredient.EMPTY, RecipeCategory.REDSTONE, EV_BATTERY.get())
				.unlocks("has_item", has(IRIDIUM_ALLOY.get()))
				.save(consumer, smithingLoc("iridium_battery"));
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		// TODO: What goes here now :/
	}
}
