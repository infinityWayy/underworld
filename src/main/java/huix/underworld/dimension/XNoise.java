package huix.underworld.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import java.util.List;

import static net.minecraft.world.level.levelgen.NoiseRouterData.*;

public class XNoise {
    public static final ResourceKey<NoiseGeneratorSettings> UNDER_NOISE_SETTINGS_KEY = ResourceKey.create(Registries.NOISE_SETTINGS, XDimensions.UNDER_WORLD);

    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(
                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(130), -2),
                        STONE),
                DEEPSLATE
        );

        NoiseSettings noiseSettings = NoiseSettings.create(-64, 384, 1, 2);

        List<Climate.ParameterPoint> spawnTarget = List.of();

        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(noiseSettings,
                Blocks.DEEPSLATE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                under(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
                surfaceRule,
                spawnTarget,
                144,
                true,
                false,
                false,
                true
        );

        context.register(UNDER_NOISE_SETTINGS_KEY, settings);
    }

    /**
     * @param input            原始的密度函数，定义了基础地形的形状。
     * @param minY             应用此效果的区域的最低Y坐标。
     * @param maxY             应用此效果的区域的最高Y坐标。注意：这不是世界高度，而是此函数的参数。
     *
     *                         --- 顶部滑动参数 ---
     * @param topSlideSize     顶部滑动区域的大小（垂直高度）。
     * @param topSlideOffset   顶部滑动区域的偏移量。滑动区域的Y坐标范围是：
     *                         [minY + maxY - topSlideSize, minY + maxY - topSlideOffset]
     * @param topTarget        顶部滑动区域的目标密度值。地形将被平滑过渡到这个值。
     *                         - 负数倾向于生成固体（如地狱顶）。
     *                         - 正数倾向于生成空气。
     *
     *                         --- 底部滑动参数 ---
     * @param bottomSlideSize  底部滑动区域的大小。
     * @param bottomSlideOffset 底部滑动区域的偏移量。滑动区域的Y坐标范围是：
     *                          [minY + bottomSlideSize, minY + bottomSlideOffset]
     * @param bottomTarget     底部滑动区域的目标密度值。地形将被平滑过渡到这个值。
     *
     */
    private static DensityFunction slideUnderLike(HolderGetter<DensityFunction> densityFunctions, int minY, int maxY) {
        return slide(getFunction(densityFunctions, XDensityFunction.BASE_3D_NOISE_UNDER), minY, maxY,
                24, 0, -1.5,
                -8, 24, 2.5);
    }

    private static DensityFunction createFinalDensityFunction(HolderGetter<DensityFunction> densityFunctions) {
        DensityFunction noiseTerrain = slideUnderLike(densityFunctions, 112, 256);
        DensityFunction solidFill = DensityFunctions.constant(1.0);

        DensityFunction switcher = DensityFunctions.yClampedGradient(256, 320, 0.0, 1.0);
        return DensityFunctions.lerp(switcher, noiseTerrain, solidFill);
    }


    protected static NoiseRouter under(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        DensityFunction finalDensityFunction = createFinalDensityFunction(densityFunctions);
        return noNewCaves(densityFunctions, noiseParameters, finalDensityFunction);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
