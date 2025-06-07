package huix.underworld.dimension;

import huix.underworld.Underworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class XNoiseParameters {

    public static final ResourceKey<NormalNoise.NoiseParameters> UNDERWORLD_NOISE_1 = createKey("underworld_1");
    public static final ResourceKey<NormalNoise.NoiseParameters> UNDERWORLD_NOISE_2 = createKey("underworld_2");
    public static final ResourceKey<NormalNoise.NoiseParameters> UNDERWORLD_SELECTOR = createKey("underworld_selector");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, Underworld.rl(name));
    }

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        // firstOctave 对应旧代码的八度音阶数 (通常是 -(octaves-1))
        // amplitudes 是每个八度音阶的振幅，通常是 1.0

        // 对应 netherNoiseGen1/2 (16 octaves)
        context.register(UNDERWORLD_NOISE_1, new NormalNoise.NoiseParameters(-15, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));
        context.register(UNDERWORLD_NOISE_2, new NormalNoise.NoiseParameters(-15, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));

        // 对应 netherNoiseGen3 (8 octaves)
        context.register(UNDERWORLD_SELECTOR, new NormalNoise.NoiseParameters(-7, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));

    }
}
