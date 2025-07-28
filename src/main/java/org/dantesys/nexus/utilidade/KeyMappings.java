package org.dantesys.nexus.utilidade;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.jarjar.nio.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static final Lazy<KeyMapping> HAB_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.nexus.hab",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.nexus.cat"
    ));
    public static final Lazy<KeyMapping> SWAP_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.nexus.swap",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.nexus.cat"
    ));
}
