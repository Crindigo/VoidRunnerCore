package com.crindigo.voidrunnercore.loaders.recipe;

public class RecipeManager
{
    private RecipeManager() {}

    public static void init()
    {
        MachineRecipes.init();
        BotanyGrowerRecipes.init();
        GreenhouseRecipes.init();
    }
}
