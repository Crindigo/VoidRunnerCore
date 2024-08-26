package com.crindigo.voidrunnercore.common;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.VRCLog;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import com.crindigo.voidrunnercore.loaders.recipe.RecipeManager;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
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
        RecipeMaps.FORGE_HAMMER_RECIPES.setMaxOutputs(2);
    }

    public void load()
    {

    }

    @SubscribeEvent
    public static void registerMaterials(MaterialRegistryEvent event)
    {
        GregTechAPI.materialManager.createRegistry("voidrunner");
    }

    @SubscribeEvent
    public static void registerItems(@NotNull RegistryEvent.Register<Item> event)
    {
        VRCLog.log.info("Register items...");

        IForgeRegistry<Item> registry = event.getRegistry();
        VRCMetaItems.initSubItems();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        RecipeManager.init();
    }
}
