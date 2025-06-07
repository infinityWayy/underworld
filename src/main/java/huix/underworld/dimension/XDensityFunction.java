package huix.underworld.dimension;

import huix.underworld.Underworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class XDensityFunction {

    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_UNDER = createKey("under_world/base_3d_noise");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Underworld.rl(name));
    }

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        context.register(BASE_3D_NOISE_UNDER, BlendedNoise.createUnseeded(1, 10, 800.0, 2.0, 8.0));
    }
}
