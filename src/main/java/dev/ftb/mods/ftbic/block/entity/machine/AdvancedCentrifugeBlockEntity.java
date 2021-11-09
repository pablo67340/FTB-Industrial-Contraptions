package dev.ftb.mods.ftbic.block.entity.machine;

import dev.ftb.mods.ftbic.block.FTBICElectricBlocks;
import dev.ftb.mods.ftbic.recipe.MachineRecipeResults;
import dev.ftb.mods.ftbic.recipe.RecipeCache;
import dev.ftb.mods.ftbic.util.EnergyTier;

public class AdvancedCentrifugeBlockEntity extends MachineBlockEntity {
	public AdvancedCentrifugeBlockEntity() {
		super(FTBICElectricBlocks.ADVANCED_CENTRIFUGE.blockEntity.get(), 2, 2);
	}

	@Override
	public void initProperties() {
		super.initProperties();
		inputEnergyTier = EnergyTier.MV;
		energyCapacity = 8000;
		energyUse = 20;
		shouldAccelerate = true;
	}

	@Override
	public MachineRecipeResults getRecipes(RecipeCache cache) {
		return cache.separating;
	}
}