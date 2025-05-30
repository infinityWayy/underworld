package huix.underworld.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.awt.*;

public class UnderWorldBiomes {

    public static Biome build() {
        return new Biome.BiomeBuilder().build();
    }

    public static Biome under_world(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        int color = new Color(100, 100,120).getRGB();
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(color).waterColor(color).waterFogColor(color).skyColor(color).foliageColorOverride(1787717).grassColorOverride(1787717).build();
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(placedFeatureGetter,carverGetter);
        addDefaultCarvers(biomeGenSettings);

        MobSpawnSettings mobSpawn = (new MobSpawnSettings.Builder())
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 255, 5, 10)).build();

        return new Biome.BiomeBuilder().hasPrecipitation(false)
                //.mobSpawnSettings(mobSpawnInfo)
                .temperature(-1.0F).downfall(0F)
                .specialEffects(effects).generationSettings(biomeGenSettings.build()).build();
    }



    private static void addDefaultCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.NETHER_CAVE);
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.CAVE);
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.CANYON);
    }
}
