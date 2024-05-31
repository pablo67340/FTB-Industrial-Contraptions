package dev.ftb.mods.ftbic.block.entity.generator;

import dev.ftb.mods.ftbic.block.FTBICElectricBlocks;
import dev.ftb.mods.ftbic.screen.GeothermalGeneratorMenu;
import dev.ftb.mods.ftbic.screen.sync.SyncedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeothermalGeneratorBlockEntity extends GeneratorBlockEntity {
	public int fluidAmount = 0;
	private LazyOptional<GeothermalGeneratorTank> tankOptional;

	public GeothermalGeneratorBlockEntity(BlockPos pos, BlockState state) {
		super(FTBICElectricBlocks.GEOTHERMAL_GENERATOR, pos, state);
	}

	@Override
	public void writeData(CompoundTag tag) {
		super.writeData(tag);
		tag.putInt("FluidAmount", fluidAmount);
	}

	@Override
	public void readData(CompoundTag tag) {
		super.readData(tag);
		fluidAmount = tag.getInt("FluidAmount");
	}

	public LazyOptional<?> getTankOptional() {
		if (tankOptional == null) {
			tankOptional = LazyOptional.of(() -> new GeothermalGeneratorTank(this));
		}

		return tankOptional;
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();

		if (tankOptional != null) {
			tankOptional.invalidate();
			tankOptional = null;
		}
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? getTankOptional().cast() : super.getCapability(cap, side);
	}

	@Override
	public void handleGeneration() {
		if (energy < energyCapacity && fluidAmount > 0) {
			energy += Math.min(energyCapacity - energy, maxEnergyOutput);
			fluidAmount--;
			active = true;
		}
	}

	@Override
	public InteractionResult rightClick(Player player, InteractionHand hand, BlockHitResult hit) {
		if (FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler) getTankOptional().orElse(null))) {
			return InteractionResult.SUCCESS;
		} else if (!level.isClientSide()) {
			openMenu((ServerPlayer) player, (id, inventory) -> new GeothermalGeneratorMenu(id, inventory, this));
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void addSyncData(SyncedData data) {
		super.addSyncData(data);
		data.addShort(SyncedData.BAR, () -> fluidAmount);
	}
}
