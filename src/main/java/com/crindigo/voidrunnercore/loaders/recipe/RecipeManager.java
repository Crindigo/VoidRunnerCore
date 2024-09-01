package com.crindigo.voidrunnercore.loaders.recipe;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class RecipeManager
{
    private RecipeManager() {}

    public static void init()
    {
        MachineRecipes.init();
        BotanyGrowerRecipes.init();
        //GreenhouseRecipes.init();

        VRCRecipeMaps.BLOOMERY_RECIPES.recipeBuilder()
                .input("oreMagnetite", 4)
                .output(OrePrefix.block, Materials.WroughtIron)
                .duration(600)
                .buildAndRegister();
    }
}
