package com.crindigo.voidrunnercore;

import com.crindigo.voidrunnercore.common.CommonProxy;
import com.crindigo.voidrunnercore.common.covers.VRCCoverBehaviors;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import com.crindigo.voidrunnercore.common.metatileentities.VRCMetaTileEntities;
import gregtech.GTInternalTags;
import gregtech.api.GregTechAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME,
        acceptedMinecraftVersions = "[1.12.2]",
        dependencies = GTInternalTags.DEP_VERSION_STRING + ";required-after:gcym")
public class VoidRunnerMod
{
    @SidedProxy(modId = Tags.MODID,
            clientSide = "com.crindigo.voidrunnercore.client.ClientProxy",
            serverSide = "com.crindigo.voidrunnercore.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preLoad();

        VRCMetaItems.initMetaItems();

        VRCMetaTileEntities.init();

        VRCLog.log.info("I am " + Tags.MODNAME + " + at version " + Tags.VERSION);
    }

    @EventHandler
    // load "Do your mod setup. Build whatever data structures you care about." (Remove if not needed)
    public void init(FMLInitializationEvent event)
    {
        proxy.load();
        VRCCoverBehaviors.init();
    }

    @EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
