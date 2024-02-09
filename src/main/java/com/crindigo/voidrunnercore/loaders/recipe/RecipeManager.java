package com.crindigo.voidrunnercore.loaders.recipe;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipeManager
{
    private RecipeManager() {}

    public static void init()
    {
        MachineRecipes.init();
        BotanyGrowerRecipes.init();
        //GreenhouseRecipes.init();

        VRCRecipeMaps.EVAP_POOL_RECIPES.recipeBuilder()
                .fluidInputs(new FluidStack(FluidRegistry.WATER, 1000))
                .output(OrePrefix.dust, Materials.Salt, 8)
                .duration(1200)
                .buildAndRegister();
    }
}
