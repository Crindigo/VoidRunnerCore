package com.crindigo.voidrunnercore.common;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.VRCLog;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class CommonProxy
{
    public void preLoad()
    {

    }

    public void load()
    {

    }

    @SubscribeEvent
    public static void registerItems(@NotNull RegistryEvent.Register<Item> event)
    {
        VRCLog.log.info("Register items...");

        IForgeRegistry<Item> registry = event.getRegistry();
        VRCMetaItems.initSubItems();
    }
}
