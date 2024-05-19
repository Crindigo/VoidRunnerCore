package com.crindigo.voidrunnercore.common.metatileentities.multi;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityTrashCollector extends RecipeMapMultiblockController
{
    public MetaTileEntityTrashCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, VRCRecipeMaps.TRASH_COLLECTOR_RECIPES);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // right, up, back
        // char, string, aisle
        // so each char in the string goes from left to right on the multiblock
        // each string inside of aisle() goes from bottom to top
        // each aisle call starts from back and goes to front??? so it's the opposite?
        return FactoryBlockPattern.start()
                // layer  0        1        2
                .aisle("CCCCC", "DBBBD", "DBBBD") // back
                .aisle("CWWWC", "BWBWB", "BWBWB")
                .aisle("CWWWC", "BBWBB", "BBWBB")
                .aisle("CWWWC", "BWBWB", "BWBWB")
                .aisle("CCSCC", "DBBBD", "DBBBD") // front
                .where('C', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                        BlockLargeMultiblockCasing.CasingType.STEAM_CASING))
                        .setMinGlobalLimited(9)
                        .or(autoAbilities()))
                .where('D', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                        BlockLargeMultiblockCasing.CasingType.STEAM_CASING)))
                .where('W', states(Blocks.WOOL.getDefaultState()))
                .where('B', states(Blocks.IRON_BARS.getDefaultState()))
                .where('S', selfPredicate())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.STEAM_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityTrashCollector(metaTileEntityId);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
}
