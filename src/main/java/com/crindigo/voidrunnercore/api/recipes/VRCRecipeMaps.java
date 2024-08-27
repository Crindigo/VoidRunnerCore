package com.crindigo.voidrunnercore.api.recipes;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

public class VRCRecipeMaps
{
    public static final RecipeMap<SimpleRecipeBuilder> CRUDE_MIXER_RECIPES = new RecipeMap<>(
            "crude_mixer", 2, 1, 2, 1,
            new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.MIXER);

    public static final RecipeMap<SimpleRecipeBuilder> BOTANY_GROWER_RECIPES = new RecipeMap<>(
            "botany_grower", 2, 4, 0, 0,
            new SimpleRecipeBuilder(), false);

    public static final RecipeMap<SimpleRecipeBuilder> GREENHOUSE_RECIPES = new RecipeMap<>(
            "vrc_greenhouse", 4, 6, 2, 0,
            new SimpleRecipeBuilder(), false);

    public static final RecipeMap<PrimitiveRecipeBuilder> EVAP_POOL_RECIPES = new RecipeMap<>(
            "evap_pool", 1, 4, 1, 0,
            new PrimitiveRecipeBuilder(), false);

    public static final RecipeMap<SimpleRecipeBuilder> TRASH_COLLECTOR_RECIPES = new RecipeMap<>(
            "trash_collector", 1, 8, 1, 1,
            new SimpleRecipeBuilder(), false);

    public static final RecipeMap<SimpleRecipeBuilder> DIGESTER_RECIPES = new RecipeMap<>(
            "digester", 4, 4, 1, 1,
            new SimpleRecipeBuilder(), false);

    public static final RecipeMap<SimpleRecipeBuilder> ELECTRIC_BLOOMERY_RECIPES = new RecipeMap<>(
            "electric_bloomery", 2, 4, 0, 0,
            new SimpleRecipeBuilder(), false);

    public static final RecipeMap<PrimitiveRecipeBuilder> BLOOMERY_RECIPES = new RecipeMap<>(
            "bloomery", 2, 4, 0, 0,
            new PrimitiveRecipeBuilder(), false)
            .onRecipeBuild(recipeBuilder -> {
                ELECTRIC_BLOOMERY_RECIPES.recipeBuilder()
                        .duration(recipeBuilder.getDuration() / 2)
                        .input(recipeBuilder.getInputs().get(0))
                        .outputs(recipeBuilder.getOutputs())
                        .EUt(30)
                        .buildAndRegister();
            });
}
