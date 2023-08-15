package com.crindigo.voidrunnercore.common;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.VRCLog;
import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import com.crindigo.voidrunnercore.common.item.VRCMetaItems;
import com.crindigo.voidrunnercore.common.metatileentities.VRCMetaTileEntities;
import gregtech.api.items.OreDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.BRONZE_HULL;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class CommonProxy
{
    public void preLoad()
    {

    }

    public void load()
    {

    }

    @SubscribeEvent
    public static void registerItems(@NotNull RegistryEvent.Register<Item> event)
    {
        VRCLog.log.info("Register items...");

        IForgeRegistry<Item> registry = event.getRegistry();
        VRCMetaItems.initSubItems();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        VRCRecipeMaps.CRUDE_MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Stone)
                .fluidInputs(Materials.Water.getFluid(1000))
                .fluidOutputs(Materials.Acetone.getFluid(1000)) // testing
                .duration(100)
                .EUt(2)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "crude_mixer_bronze",
                VRCMetaTileEntities.STEAM_CRUDE_MIXER_BRONZE.getStackForm(),
                "GRG",
                "GMG",
                "XXX",
                'G', new UnificationEntry(OrePrefix.block, Materials.Glass),
                'R', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
                'M', MetaBlocks.STEAM_CASING.getItemVariant(BRONZE_HULL),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.Bronze));

        ModHandler.addShapedRecipe(true, "crude_mixer_steel",
                VRCMetaTileEntities.STEAM_CRUDE_MIXER_STEEL.getStackForm(),
                "GRG",
                "SMS",
                "XXX",
                'G', new UnificationEntry(OrePrefix.block, Materials.Glass),
                'R', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
                'S', new UnificationEntry(OrePrefix.plate, Materials.Steel),
                'M', VRCMetaTileEntities.STEAM_CRUDE_MIXER_BRONZE.getStackForm(),
                'X', new UnificationEntry(OrePrefix.pipeSmallFluid, Materials.TinAlloy));
    }
}
