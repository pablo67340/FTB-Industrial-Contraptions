package dev.ftb.mods.ftbic.block;

import dev.ftb.mods.ftbic.util.FTBICUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class ExFluidBlock extends Block {
	public ExFluidBlock() {
		super(Properties.of(Material.STONE).strength(0.9F).sound(SoundType.STONE).isValidSpawn((arg, arg2, arg3, object) -> false).randomTicks());
	}

	@Override
	@Deprecated
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		for (Direction direction : FTBICUtils.DIRECTIONS) {
			if (direction != Direction.DOWN && level.getFluidState(pos.relative(direction)).getType() != Fluids.EMPTY) {
				return;
			}
		}

		level.removeBlock(pos, false);
	}
}
