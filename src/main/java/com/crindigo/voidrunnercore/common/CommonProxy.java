package com.crindigo.voidrunnercore.common;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.VRCLog;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import com.crindigo.voidrunnercore.loaders.recipe.RecipeManager;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import gregtech.api.unification.material.info.MaterialFlags;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
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
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.setMaxInputs(9);
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
    public static void registerDummyMaterials(MaterialEvent event)
    {
        // seeing if registering a dummy material fixes a BlockCable getFallbackType() crash.
        // there was a "voidrunner:cable_double" name and enabled materials was empty.

        new Material.Builder(32765, new ResourceLocation("voidrunner", "dummy"))
                .ingot()
                .cableProperties(GTValues.V[GTValues.OpV], 16, 1)
                .itemPipeProperties(1024, 64)
                .color(0xff00ff)
                .build();

        new Material.Builder(32766, new ResourceLocation("voidrunner", "dummy_two"))
                .ingot()
                .fluidPipeProperties(65536, 4096, true, true, true, true)
                .color(0xffff00)
                .build();
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
