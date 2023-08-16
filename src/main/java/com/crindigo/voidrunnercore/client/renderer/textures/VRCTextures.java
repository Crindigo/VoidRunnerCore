package com.crindigo.voidrunnercore.client.renderer.textures;

import com.crindigo.voidrunnercore.VRCLog;
import gregtech.api.gui.resources.SteamTexture;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class VRCTextures
{
    public static final SimpleOverlayRenderer INFINITE_LAVA = new SimpleOverlayRenderer("cover/overlay_infinite_lava");

    // at a minimum need overlay_front.png and overlay_front_active.png
    // can add _emissive variants, and .mcmeta to give animations to active images.
    // other faces include _back, _side, _top, _bottom. also can have _paused.
    // in resources/assets/gregtech/textures/blocks/BASE_PATH/
    public static final OrientedOverlayRenderer BOTANY_GROWER = new OrientedOverlayRenderer("machines/botany_grower");

    // _bronze.png and _steel.png assuming we make a steel variant of this.
    // as long as you don't make a high pressure version, i think the tex can go missing.
    public static final SteamTexture SAPLING_OVERLAY_STEAM = SteamTexture.fullImage("textures/gui/overlay/sapling_overlay_%s.png");


    /**
     * Placeholder method so that this class is loaded in client-side pre-init, and not whenever
     * a texture is first referenced.
     */
    public static void init() {
        VRCLog.log.info("textures init");
    }
}
