package com.crindigo.voidrunnercore.common.covers;

import com.crindigo.voidrunnercore.VRCLog;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import com.crindigo.voidrunnercore.util.VRC;
import gregtech.common.covers.CoverBehaviors;
import net.minecraft.util.ResourceLocation;

public class VRCCoverBehaviors
{
    public static void init()
    {
        VRCLog.log.info("Init cover behaviors...");

        CoverBehaviors.registerBehavior(VRC.id("infinite_lava"),
                VRCMetaItems.INFINITE_LAVA_COVER, CoverInfiniteLava::new);
    }
}
