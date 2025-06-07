package huix.underworld.dimension;

import huix.underworld.Underworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class XDimensions {
    
    public static final ResourceLocation UNDER_WORLD = Underworld.rl("under_world");
    public static final ResourceKey<Level> UNDER_KEY = ResourceKey.create(Registries.DIMENSION, UNDER_WORLD);
    public static final ResourceKey<DimensionType> UNDER_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, UNDER_WORLD);
    public static final ResourceKey<NoiseGeneratorSettings> UNDER_NOISE_SETTINGS_KEY = ResourceKey.create(Registries.NOISE_SETTINGS, UNDER_WORLD);

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        context.register(UNDER_TYPE_KEY, new DimensionType(
                OptionalLong.of(18000L), // fixedTime
                false,  // hasSkylight
                true, // hasCeiling
                false, // ultrawarm
                false,  // natural
                4.0F,   // coordinateScale
                false,  // bedWorks
                true, // respawnAnchorWorks
                -64,   // minY
                384,   // height
                384,   // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn tag
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0F,  // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var biomes = context.lookup(Registries.BIOME);
        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        context.register(ResourceKey.create(Registries.LEVEL_STEM, UNDER_WORLD),
                new LevelStem(dimensionTypes.getOrThrow(UNDER_TYPE_KEY),
                        new NoiseBasedChunkGenerator(
                                new FixedBiomeSource(biomes.getOrThrow(XBiomes.UNDER_BIOME_KEY)),
                                noiseSettings.getOrThrow(UNDER_NOISE_SETTINGS_KEY)
                        )
                )
        );
    }
}
