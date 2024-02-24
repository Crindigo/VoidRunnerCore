package com.crindigo.voidrunnercore.loaders.recipe;

import com.google.gson.JsonObject;

public class RecipeManager
{
    private RecipeManager() {}

    public static void init()
    {
        MachineRecipes.init();
        BotanyGrowerRecipes.init();
        //GreenhouseRecipes.init();
    }
}
