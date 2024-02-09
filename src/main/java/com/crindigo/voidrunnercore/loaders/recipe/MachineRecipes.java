package com.crindigo.voidrunnercore.loaders.recipe;

import com.crindigo.voidrunnercore.common.metatileentities.VRCMetaTileEntities;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.BRONZE_HULL;

public class MachineRecipes
{
    private MachineRecipes() {}

    public static void init()
    {
        // Crude Mixer

        ModHandler.addShapedRecipe(true, "crude_mixer_bronze",
                VRCMetaTileEntities.STEAM_CRUDE_MIXER_BRONZE.getStackForm(),
                "GRG",
                "GMG",
                "XXX",
                'G', new UnificationEntry(OrePrefix.block, Materials.Glass),
                'R', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
                'M', MetaBlocks.STEAM_CASING.getItemVariant(BRONZE_HULL),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.Bronze));

        makeSteelVariant("crude_mixer_steel",
                VRCMetaTileEntities.STEAM_CRUDE_MIXER_BRONZE, VRCMetaTileEntities.STEAM_CRUDE_MIXER_STEEL);

        // Botany Grower

        ModHandler.addShapedRecipe(true, "botany_grower_bronze",
                VRCMetaTileEntities.STEAM_BOTANY_GROWER_BRONZE.getStackForm(),
                "XGX",
                "FDF",
                "XMX",
                'G', new UnificationEntry(OrePrefix.block, Materials.Glass),
                'F', MetaItems.FERTILIZER.getStackForm(),
                'D', new ItemStack(Blocks.DIRT),
                'M', MetaBlocks.STEAM_CASING.getItemVariant(BRONZE_HULL),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.Bronze));

        makeSteelVariant("botany_grower_steel",
                VRCMetaTileEntities.STEAM_BOTANY_GROWER_BRONZE, VRCMetaTileEntities.STEAM_BOTANY_GROWER_STEEL);

        // Sifter

        ModHandler.addShapedRecipe(true, "steam_sifter_bronze",
                VRCMetaTileEntities.STEAM_SIFTER_BRONZE.getStackForm(),
                "BBB",
                "WMW",
                "XXX",
                'B', new ItemStack(Blocks.IRON_BARS),
                'W', new ItemStack(Blocks.WOOL, 1, 0),
                'M', MetaBlocks.STEAM_CASING.getItemVariant(BRONZE_HULL),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.Bronze));

        makeSteelVariant("steam_sifter_steel",
                VRCMetaTileEntities.STEAM_SIFTER_BRONZE, VRCMetaTileEntities.STEAM_SIFTER_STEEL);

        // Primitive Evap Pool

        ModHandler.addShapedRecipe(true, "primitive_evap_pool",
                VRCMetaTileEntities.PRIMITIVE_EVAP_POOL.getStackForm(),
                "WPW",
                "R R",
                "WPW",
                'W', new UnificationEntry(OrePrefix.block, Materials.TreatedWood),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Lead),
                'R',  new UnificationEntry(OrePrefix.rotor, Materials.Tin));
    }

    private static void makeSteelVariant(String name, SteamMetaTileEntity bronzeMachine, SteamMetaTileEntity steelMachine)
    {
        ModHandler.addShapedRecipe(true, name,
                steelMachine.getStackForm(),
                "WWW",
                "SMS",
                "XXX",
                'W', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron),
                'S', new UnificationEntry(OrePrefix.plate, Materials.Steel),
                'M', bronzeMachine.getStackForm(),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.TinAlloy));
    }
}
