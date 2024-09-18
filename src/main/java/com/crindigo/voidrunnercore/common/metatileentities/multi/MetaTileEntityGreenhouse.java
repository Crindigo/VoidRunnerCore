package com.crindigo.voidrunnercore.common.metatileentities.multi;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import com.crindigo.voidrunnercore.client.renderer.textures.VRCTextures;
import com.crindigo.voidrunnercore.util.VRC;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityGreenhouse extends RecipeMapMultiblockController
{
    public MetaTileEntityGreenhouse(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, VRCRecipeMaps.GREENHOUSE_RECIPES);
        this.recipeMapWorkable = new GreenhouseWorkableHandler(this);
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
                // layer  0        1        2        3        4        5
                .aisle("FCCCF", "FGGGF", "FGGGF", "FGGGF", "FGGGF", "FFFFF") // back
                .aisle("CDDDC", "G###G", "G###G", "G###G", "G###G", "FGGGF")
                .aisle("CDDDC", "G###G", "G###G", "G###G", "G###G", "FGGGF")
                .aisle("CDDDC", "G###G", "G###G", "G###G", "G###G", "FGGGF")
                .aisle("FCSCF", "FGGGF", "FGGGF", "FGGGF", "FGGGF", "FFFFF") // front
                .where('F', frames(Materials.Steel))
                .where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                        .setMinGlobalLimited(4)
                        .or(autoAbilities()))
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS)))
                .where('D', states(Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState()))
                .where('#', air())
                .where('S', selfPredicate())
                .build();

        // want to remove bottom layer of casing, make it 4 tall inside
        // also allow grass to be used instead of dirt in case it spreads.
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    protected ICubeRenderer getFrontOverlay() {
        return VRCTextures.BOTANY_GROWER;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityGreenhouse(metaTileEntityId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("voidrunnercore.greenhouse.supply_co2_to_boost"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if ( isStructureFormed() ) {
            if ( getInputFluidInventory() != null ) {
                FluidStack co2Stack = getInputFluidInventory().drain(
                        Materials.CarbonDioxide.getFluid(Integer.MAX_VALUE), false);

                if ( ((GreenhouseWorkableHandler) recipeMapWorkable).isCO2Boosted ) {
                    int amount = co2Stack == null ? 0 : co2Stack.amount;
                    textList.add(VRC.tct("@.greenhouse.co2_amount", TextFormattingUtil.formatNumbers(amount)));
                    textList.add(VRC.tct("@.greenhouse.co2_boosted"));
                } else {
                    textList.add(VRC.tct("@.greenhouse.supply_co2_to_boost"));
                }
            }
        }
    }

    private static class GreenhouseWorkableHandler extends MultiblockRecipeLogic
    {
        private boolean isCO2Boosted = false;
        final private MetaTileEntityGreenhouse greenhouse;
        private long runningTime;
        private static final FluidStack CO2_STACK = Materials.CarbonDioxide.getFluid(100);


        public GreenhouseWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.greenhouse = (MetaTileEntityGreenhouse) tileEntity;
        }

        @Override
        public void invalidate() {
            this.isCO2Boosted = false;
            this.runningTime = 0L;
            super.invalidate();
        }

        @Override
        public void update() {
            super.update();
            if ( isWorking() ) {
                this.runningTime++;
            }
        }

        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                // boost with co2
                // guess i could have made alt recipes use 3000L CO2 and take 30 seconds, but
                // this was more fun.
                if ( runningTime == 1 || runningTime % 20 == 0 ) {
                    IMultipleTankHandler tanks = greenhouse.getInputFluidInventory();
                    if ( CO2_STACK.isFluidStackIdentical(tanks.drain(CO2_STACK, false)) ) {
                        isCO2Boosted = true;
                        tanks.drain(CO2_STACK, true);
                    } else {
                        isCO2Boosted = false;
                    }
                }

                drawEnergy(recipeEUt, false);

                progressTime++;
                if ( isCO2Boosted ) {
                    progressTime++;
                }

                //as recipe starts with progress on 1 this has to be > only not => to compensate for it
                if (progressTime > maxProgressTime) {
                    completeRecipe();
                }
                if (this.hasNotEnoughEnergy && getEnergyInputPerSecond() > 19L * recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (recipeEUt > 0) {
                //only set hasNotEnoughEnergy if this recipe is consuming recipe
                //generators always have enough energy
                this.hasNotEnoughEnergy = true;
                decreaseProgress();
            }
        }
    }
}
