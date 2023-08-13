package com.crindigo.voidrunnercore.client.renderer.textures;

import com.crindigo.voidrunnercore.VRCLog;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class VRCTextures
{
    public static final SimpleOverlayRenderer INFINITE_LAVA = new SimpleOverlayRenderer("cover/overlay_infinite_lava");

    /**
     * Placeholder method so that this class is loaded in client-side pre-init, and not whenever
     * a texture is first referenced.
     */
    public static void init() {
        VRCLog.log.info("textures init");
    }
}
