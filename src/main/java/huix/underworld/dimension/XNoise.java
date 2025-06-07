package huix.underworld.dimension;

import huix.underworld.Underworld;
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

public class XNoise {
    public static final ResourceKey<NoiseGeneratorSettings> UNDER_NOISE_SETTINGS_KEY = ResourceKey.create(Registries.NOISE_SETTINGS, XDimensions.UNDER_WORLD);

    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);

        DensityFunction islandVerticalGradient = DensityFunctions.yClampedGradient(-64, 500, 1.0, -1.0);
        DensityFunction islandShapeNoise = DensityFunctions.noise(noise.getOrThrow(Noises.CONTINENTALNESS_LARGE), 0.7, 0.9);
        DensityFunction finalDensity = DensityFunctions.min(islandVerticalGradient, islandShapeNoise);


        NoiseRouter router = new NoiseRouter(
                DensityFunctions.zero(), // barrier
                DensityFunctions.zero(), // fluid_level_floodedness
                DensityFunctions.zero(), // fluid_level_spread
                DensityFunctions.zero(), // lava
                DensityFunctions.zero(), // temperature
                DensityFunctions.zero(), // vegetation
                DensityFunctions.zero(), // continents
                DensityFunctions.zero(), // erosion
                DensityFunctions.zero(), // depth
                DensityFunctions.zero(), // weirdness
                DensityFunctions.zero(), // initial_density_without_jaggedness
                finalDensity, // final_density
                DensityFunctions.zero(), // vein_toggle
                DensityFunctions.zero(), // vein_ridged
                DensityFunctions.zero()  // vein_gap
        );

        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(200), -1),
                        STONE),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK),
                SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
                        BEDROCK),
                DEEPSLATE
        );

        NoiseSettings noiseSettings = NoiseSettings.create(-64, 384, 1, 2);

        List<Climate.ParameterPoint> spawnTarget = List.of();

        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(noiseSettings,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                router,
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

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
