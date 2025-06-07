package huix.underworld.datagen;

import huix.underworld.Underworld;
import huix.underworld.dimension.XBiomes;
import huix.underworld.dimension.XDimensions;
import huix.underworld.dimension.XNoise;
import huix.underworld.dimension.XNoiseParameters;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class XWorldGenProvider extends DatapackBuiltinEntriesProvider {


    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.NOISE, XNoiseParameters::bootstrap)
            .add(Registries.NOISE_SETTINGS, XNoise::bootstrapNoiseSettings)
            .add(Registries.BIOME, XBiomes::bootstrap)
            .add(Registries.DIMENSION_TYPE, XDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, XDimensions::bootstrapStem);

    public XWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Underworld.MOD_ID));
    }
}
