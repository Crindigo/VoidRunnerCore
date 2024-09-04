package com.crindigo.voidrunnercore.common.metatileentities.multi;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityElectricBloomery extends RecipeMapMultiblockController implements IHeatingCoil
{
    private int blastFurnaceTemperature;

    public MetaTileEntityElectricBloomery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, VRCRecipeMaps.ELECTRIC_BLOOMERY_RECIPES);
        //this.recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // right, up, back
        // char, string, aisle
        // so each char in the string goes from left to right on the multiblock
        // each string inside of aisle() goes from bottom to top
        // each aisle call starts from back and goes to front??? so it's the opposite?
        return FactoryBlockPattern.start()
                // layer  0       1       2
                .aisle(" C ", " I ", " T ") // back
                .aisle("CCC", "I#I", "T#T")
                .aisle(" S ", " I ", " T ") // front
                .where('C', states(MetaBlocks.METAL_CASING.getState(
                        BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF))
                        .or(autoAbilities(false, false, true, true, true, false, false))
                        .or(energyAbility()))
                .where('I', heatingCoils())
                .where('T', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF)))
                .where('#', air())
                .where('S', selfPredicate())
                .build();
    }

    private TraceabilityPredicate energyAbility() {
        return abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectricBloomery(metaTileEntityId);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats)type).getCoilTemperature();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }

        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getFloorTierByVoltage(this.getEnergyContainer().getInputVoltage()) - 2);
    }

    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }
}
