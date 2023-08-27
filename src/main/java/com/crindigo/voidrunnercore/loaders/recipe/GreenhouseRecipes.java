package com.crindigo.voidrunnercore.loaders.recipe;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class GreenhouseRecipes
{
    private GreenhouseRecipes() {}

    public static void init()
    {
        for ( int i = 0; i < 4; i++ ) {
            ArrayList<ItemStack> outputs = new ArrayList<>();
            outputs.add(new ItemStack(Blocks.LOG, 64, i));
            outputs.add(new ItemStack(Blocks.SAPLING, 4, i));
            if ( i == 0 ) {
                outputs.add(new ItemStack(Items.APPLE, 2));
            }

            startRecipe(new ItemStack(Blocks.SAPLING, 1, i))
                    .outputs(outputs)
                    .circuitMeta(1)
                    .buildAndRegister();

            ArrayList<ItemStack> secondary = new ArrayList<>(outputs);
            secondary.set(1, new ItemStack(Blocks.LEAVES, 32, i));

            startRecipe(new ItemStack(Blocks.SAPLING, 1, i))
                    .outputs(secondary)
                    .circuitMeta(2)
                    .buildAndRegister();
        }

        for ( int i = 0; i < 2; i++ ) {
            startRecipe(new ItemStack(Blocks.SAPLING, 1, i + 4))
                    .outputs(
                            new ItemStack(Blocks.LOG2, 64, i),
                            new ItemStack(Blocks.SAPLING, 4, i + 4)
                    )
                    .circuitMeta(1)
                    .buildAndRegister();

            startRecipe(new ItemStack(Blocks.SAPLING, 1, i + 4))
                    .outputs(
                            new ItemStack(Blocks.LOG2, 64, i),
                            new ItemStack(Blocks.LEAVES2, 32, i)
                    )
                    .circuitMeta(2)
                    .buildAndRegister();
        }

        startRecipe(new ItemStack(MetaBlocks.RUBBER_SAPLING, 1))
                .outputs(
                        new ItemStack(MetaBlocks.RUBBER_LOG, 64),
                        new ItemStack(MetaBlocks.RUBBER_SAPLING, 4),
                        MetaItems.STICKY_RESIN.getStackForm(32)
                )
                .circuitMeta(1)
                .buildAndRegister();

        startRecipe(new ItemStack(MetaBlocks.RUBBER_SAPLING, 1))
                .outputs(
                        new ItemStack(MetaBlocks.RUBBER_LOG, 64),
                        new ItemStack(MetaBlocks.RUBBER_LEAVES, 32),
                        MetaItems.STICKY_RESIN.getStackForm(32)
                )
                .circuitMeta(2)
                .buildAndRegister();

        // seeds
        // wheat, pumpkin, melon, beetroot
        startRecipe(new ItemStack(Items.WHEAT_SEEDS, 1))
                .outputs(
                        new ItemStack(Items.WHEAT, 64),
                        new ItemStack(Items.WHEAT_SEEDS, 16)
                )
                .buildAndRegister();

        startRecipe(new ItemStack(Items.MELON_SEEDS, 1))
                .outputs(
                        new ItemStack(Items.MELON, 64),
                        new ItemStack(Items.MELON_SEEDS, 16)
                )
                .buildAndRegister();

        startRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 1))
                .outputs(
                        new ItemStack(Blocks.PUMPKIN, 32),
                        new ItemStack(Items.PUMPKIN_SEEDS, 8)
                )
                .buildAndRegister();

        startRecipe(new ItemStack(Items.BEETROOT_SEEDS, 1))
                .outputs(
                        new ItemStack(Items.BEETROOT, 64),
                        new ItemStack(Items.BEETROOT_SEEDS, 16)
                )
                .buildAndRegister();

        // flowers - yellow_flower:0, then red_flower:0-8
        startRecipe(new ItemStack(Blocks.YELLOW_FLOWER))
                .outputs(new ItemStack(Blocks.YELLOW_FLOWER, 32))
                .buildAndRegister();

        for ( int i = 0; i <= 8; i++ ) {
            startRecipe(new ItemStack(Blocks.RED_FLOWER, 1, i))
                    .outputs(new ItemStack(Blocks.RED_FLOWER, 32, i))
                    .buildAndRegister();
        }

        // sugar cane
        startRecipe(new ItemStack(Items.REEDS))
                .outputs(new ItemStack(Items.REEDS, 32))
                .buildAndRegister();

        // cactus - sand
        startRecipe(new ItemStack(Blocks.CACTUS), Blocks.SAND)
                .outputs(new ItemStack(Blocks.CACTUS, 32))
                .buildAndRegister();

        // mushrooms - need mycelium
        startRecipe(new ItemStack(Blocks.RED_MUSHROOM), Blocks.MYCELIUM)
                .outputs(new ItemStack(Blocks.RED_MUSHROOM, 32))
                .buildAndRegister();

        startRecipe(new ItemStack(Blocks.BROWN_MUSHROOM), Blocks.MYCELIUM)
                .outputs(new ItemStack(Blocks.BROWN_MUSHROOM, 32))
                .buildAndRegister();

        // chorus fruit - need endstone. can you even get plant blocks?
        startRecipe(new ItemStack(Blocks.CHORUS_PLANT), Blocks.END_STONE)
                .outputs(new ItemStack(Items.CHORUS_FRUIT, 32))
                .buildAndRegister();

        // nether wart - soul sand
        startRecipe(new ItemStack(Items.NETHER_WART), Blocks.SOUL_SAND)
                .outputs(new ItemStack(Items.NETHER_WART, 32))
                .buildAndRegister();

    }

    private static SimpleRecipeBuilder startRecipe(ItemStack sapling)
    {
        return VRCRecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .input(GTRecipeItemInput.getOrCreate(sapling).setNonConsumable())
                .input(MetaItems.FERTILIZER, 2)
                .fluidInputs(new FluidStack(FluidRegistry.WATER, 1000))
                .duration(1200)
                .EUt(64);
    }

    private static SimpleRecipeBuilder startRecipe(ItemStack sapling, Block dirt)
    {
        return startRecipe(sapling).input(GTRecipeItemInput.getOrCreate(new ItemStack(dirt)).setNonConsumable());
    }
}
