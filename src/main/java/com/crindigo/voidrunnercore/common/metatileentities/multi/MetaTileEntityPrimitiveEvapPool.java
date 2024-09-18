package com.crindigo.voidrunnercore.common.metatileentities.multi;

import com.crindigo.voidrunnercore.api.capability.NoEnergyMultiblockRecipeLogic;
import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import com.google.common.base.Strings;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityPrimitiveEvapPool extends RecipeMapMultiblockController
{
    public MetaTileEntityPrimitiveEvapPool(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, VRCRecipeMaps.EVAP_POOL_RECIPES);
        this.recipeMapWorkable = new EvapPoolWorkableHandler(this);
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        // right, up, back
        // char, string, aisle
        // so each char in the string goes from left to right on the multiblock
        // each string inside of aisle() goes from bottom to top
        // each aisle call starts from back and goes to front??? so it's the opposite?

        // look at cleanroom code, maybe we can detect a wood wall X blocks behind the controller,
        // then build the structure to be X dimensions
        final int innerDim = 3;
        final int minWalls = (innerDim + 1) * 4 - 5;

        final StringBuilder anyBuilder = new StringBuilder("  ");
        final StringBuilder backWallBuilder = new StringBuilder("WW");
        final StringBuilder frontWallBuilder = new StringBuilder("W");
        final StringBuilder innerWallBuilder = new StringBuilder("W");
        final StringBuilder baseBuilder = new StringBuilder(" ");
        for ( int i = 0; i < innerDim; i++ ) {
            anyBuilder.append(' ');
            backWallBuilder.append('W');
            frontWallBuilder.append((i == innerDim / 2) ? 'S' : 'W');
            innerWallBuilder.append('.');
            baseBuilder.append('B');
        }
        innerWallBuilder.append('W');
        baseBuilder.append(' ');
        frontWallBuilder.append('W');

        final String topAir = Strings.repeat(".", innerDim + 2);

        return FactoryBlockPattern.start()
                // rear of machine, layer 0 then layer 1
                .aisle(anyBuilder.toString(), backWallBuilder.toString(), topAir)
                // all middle slices of machine
                .aisleRepeatable(innerDim, innerDim, baseBuilder.toString(), innerWallBuilder.toString(), topAir)
                // the front, layer 0 then layer 1
                .aisle(anyBuilder.toString(), frontWallBuilder.toString(), topAir)
                .where(' ', any())
                //.where('B', states(
                //        Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT)))
                .where('B',
                        states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH)
                                .getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT))
                        .or(states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH)
                                .getState(StoneVariantBlock.StoneType.CONCRETE_DARK))))
                .where('W', states(MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL))
                        .setMinGlobalLimited(minWalls)
                        .or(autoAbilities(false, false, true, true, true, true, false)))
                .where('.', air())
                .where('S', selfPredicate())
                .build();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.WOOD_WALL;
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLOWER_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPrimitiveEvapPool(metaTileEntityId);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean getIsWeatherOrTerrainResistant() {
        return true;
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }

    private static class EvapPoolWorkableHandler extends NoEnergyMultiblockRecipeLogic {
        public EvapPoolWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPost(int[] overclockResults, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(overclockResults, storage);
            // reduce duration on higher tiers if we reuse this
            //overclockResults[1] = overclockResults[1]
        }
    }
}
