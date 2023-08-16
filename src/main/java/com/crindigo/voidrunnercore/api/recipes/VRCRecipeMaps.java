package com.crindigo.voidrunnercore.api.recipes;

import gregtech.api.recipes.RecipeMap;
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
}
