package huix.underworld.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;

import java.awt.*;

public class XBiomes {

    public static final ResourceKey<Biome> MY_DIMENSION_BIOME_KEY = ResourceKey.create(Registries.BIOME, XDimensions.UNDER_WORLD);

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(MY_DIMENSION_BIOME_KEY, createMyBiome(context));
    }

    private static Biome createMyBiome(BootstrapContext<Biome> context) {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        // GlobalFeatureGeneration.addDefaultOres(generationSettings);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        // spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 100, 4, 4));

        int color = new Color(100, 100,120).getRGB();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(color)
                        .waterFogColor(color)
                        .fogColor(color)
                        .skyColor(color)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
