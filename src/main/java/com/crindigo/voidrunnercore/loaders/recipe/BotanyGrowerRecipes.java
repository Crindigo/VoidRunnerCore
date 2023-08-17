package com.crindigo.voidrunnercore.loaders.recipe;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BotanyGrowerRecipes
{
    private BotanyGrowerRecipes() {}

    public static void init()
    {
        for ( int i = 0; i < 4; i++ ) {
            VRCRecipeMaps.BOTANY_GROWER_RECIPES.recipeBuilder()
                    .input(GTRecipeItemInput.getOrCreate(new ItemStack(Blocks.SAPLING, 1, i))
                            .setNonConsumable())
                    .outputs(
                            new ItemStack(Blocks.LOG, 1, i),
                            new ItemStack(Items.STICK, 1),
                            new ItemStack(Blocks.LEAVES, 2, i)
                    )
                    .duration(1200)
                    .EUt(2)
                    .buildAndRegister();

            // costs 4800 steam/2400 eu
            // multi greenhouse:
            // 100 eu/t for 1200 ticks = 48 logs at 50x more power
            // 100 for 800 ticks = 32 logs
        }

        for ( int i = 0; i < 2; i++ ) {
            VRCRecipeMaps.BOTANY_GROWER_RECIPES.recipeBuilder()
                    .input(GTRecipeItemInput.getOrCreate(new ItemStack(Blocks.SAPLING, 1, i + 4))
                            .setNonConsumable())
                    .outputs(
                            new ItemStack(Blocks.LOG2, 1, i),
                            new ItemStack(Items.STICK, 1),
                            new ItemStack(Blocks.LEAVES2, 2, i)
                    )
                    .duration(1200)
                    .EUt(2)
                    .buildAndRegister();
        }

        VRCRecipeMaps.BOTANY_GROWER_RECIPES.recipeBuilder()
                .input(GTRecipeItemInput.getOrCreate(new ItemStack(MetaBlocks.RUBBER_SAPLING, 1))
                        .setNonConsumable())
                .outputs(
                        new ItemStack(MetaBlocks.RUBBER_LOG, 1),
                        new ItemStack(Items.STICK, 1),
                        new ItemStack(MetaBlocks.RUBBER_LEAVES, 2),
                        MetaItems.STICKY_RESIN.getStackForm()
                )
                .duration(1200)
                .EUt(2)
                .buildAndRegister();
    }
}
