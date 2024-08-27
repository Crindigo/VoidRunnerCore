package com.crindigo.voidrunnercore.common.metatileentities.multi;

import com.crindigo.voidrunnercore.api.capability.NoEnergyMultiblockRecipeLogic;
import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityBloomery extends RecipeMapMultiblockController
{
    public MetaTileEntityBloomery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, VRCRecipeMaps.BLOOMERY_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityBloomery.BloomeryWorkableHandler(this);
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        // right, up, back
        // char, string, aisle
        // so each char in the string goes from left to right on the multiblock
        // each string inside of aisle() goes from bottom to top
        // each aisle call starts from back and goes to front??? so it's the opposite?
        return FactoryBlockPattern.start()
                // layer  0        1        2
                .aisle(" C ", " C ", " T ") // back
                .aisle("CCC", "C#C", "T#T")
                .aisle(" S ", " C ", " T ") // front
                .where('C', states(MetaBlocks.METAL_CASING.getState(
                        BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                        .setMinGlobalLimited(6)
                        .or(autoAbilities(false, false, true, true, false, false, false)))
                .where('T', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS)))
                .where('#', air())
                .where('S', selfPredicate())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.PRIMITIVE_BRICKS;
    }

    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBloomery(metaTileEntityId);
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

    private static class BloomeryWorkableHandler extends NoEnergyMultiblockRecipeLogic {
        public BloomeryWorkableHandler(RecipeMapMultiblockController tileEntity) {
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
