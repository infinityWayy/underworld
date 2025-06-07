package huix.underworld;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Underworld.MOD_ID)
public class Underworld {

    public static final String MOD_ID = "underworld";
    public static final Logger LOGGER = LogUtils.getLogger();



    public Underworld(IEventBus modEventBus) {

    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
