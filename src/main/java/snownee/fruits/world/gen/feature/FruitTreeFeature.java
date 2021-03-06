package snownee.fruits.world.gen.feature;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import snownee.fruits.block.FruitLeavesBlock;

public class FruitTreeFeature extends AbstractSmallTreeFeature<TreeFeatureConfig> {

    public FruitTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225820_1_) {
        super(p_i225820_1_);
    }

    @Override
    public boolean func_225557_a_(IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> trunkSet, Set<BlockPos> foliageSet, MutableBoundingBox box, TreeFeatureConfig config) {
        int i = config.baseHeight + rand.nextInt(config.heightRandA + 1) + rand.nextInt(config.heightRandB + 1);
        int j = config.trunkHeight >= 0 ? config.trunkHeight + rand.nextInt(config.trunkHeightRandom + 1) : i - (config.foliageHeight + rand.nextInt(config.foliageHeightRandom + 1));
        int k = config.foliagePlacer.func_225573_a_(rand, j, i, config);
        Optional<BlockPos> optional = this.func_227212_a_(world, i, j, k, pos, config);
        if (!optional.isPresent()) {
            return false;
        } else {
            BlockPos blockpos = optional.get();
            this.setDirtAt(world, blockpos.down(), blockpos);
            config.foliagePlacer.func_225571_a_(world, rand, config, i, j, k, blockpos, foliageSet);
            this.func_227213_a_(world, rand, i, blockpos, config.trunkTopOffset + rand.nextInt(config.trunkTopOffsetRandom + 1), trunkSet, box, config);
            blockpos = blockpos.up(i);
            BlockState core = config.leavesProvider.getBlockState(rand, blockpos);
            if (core.getBlock() instanceof FruitLeavesBlock) {
                core = core.with(LeavesBlock.DISTANCE, 1).with(LeavesBlock.PERSISTENT, true);
            }
            world.setBlockState(blockpos, core, 19);
            return true;
        }
    }

}
