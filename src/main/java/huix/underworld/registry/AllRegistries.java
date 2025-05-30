package huix.underworld.registry;

import huix.underworld.Underworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllRegistries {

    public static final ResourceLocation THE_UNDER_WORLD_DIM_ID = ResourceLocation.fromNamespaceAndPath(Underworld.MOD_ID,"under_world");
    public static final ResourceKey<DimensionType> UNDER_WORLD_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, THE_UNDER_WORLD_DIM_ID);
    public static final ResourceKey<Level> under_world = ResourceKey.create(Registries.DIMENSION, THE_UNDER_WORLD_DIM_ID);

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registries.BIOME, Underworld.MOD_ID);
    public static final DeferredHolder<Biome,Biome> build = BIOMES.register("build", UnderWorldBiomes::build);

}
