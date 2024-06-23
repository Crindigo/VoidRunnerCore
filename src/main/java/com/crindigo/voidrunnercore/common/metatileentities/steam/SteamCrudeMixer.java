package com.crindigo.voidrunnercore.common.metatileentities.steam;

import com.crindigo.voidrunnercore.api.capability.VRCFluidFilters;
import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SteamCrudeMixer extends SteamMetaTileEntity
{
    private FluidTank inputFluidOne;
    private FluidTank inputFluidTwo;
    private FluidTank outputFluid;

    public SteamCrudeMixer(ResourceLocation metaTileEntityId, boolean isHighPressure)
    {
        super(metaTileEntityId, VRCRecipeMaps.CRUDE_MIXER_RECIPES, Textures.MIXER_OVERLAY, isHighPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamCrudeMixer(metaTileEntityId, isHighPressure);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 2, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, true);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        super.createImportFluidHandler(); // create the steam fluid tank
        inputFluidOne = new NotifiableFilteredFluidHandler(8000, this, false)
                .setFilter(VRCFluidFilters.NON_STEAM);
        inputFluidTwo = new NotifiableFilteredFluidHandler(8000, this, false)
                .setFilter(VRCFluidFilters.NON_STEAM);
        return new FluidTankList(false, steamFluidTank, inputFluidOne, inputFluidTwo);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        outputFluid = new NotifiableFluidTank(8000, this, true);
        return new FluidTankList(false, outputFluid);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
                .slot(this.importItems, 0, 53, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.DUST_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 1, 35, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.DUST_OVERLAY_STEAM.get(isHighPressure))
                .widget(new TankWidget(inputFluidOne, 53, 43, 18, 18).setAlwaysShowFull(true)
                        .setBackgroundTexture(GuiTextures.SLOT_STEAM.get(isHighPressure))
                        .setContainerClicking(true, true))
                .widget(new TankWidget(inputFluidTwo, 35, 43, 18, 18).setAlwaysShowFull(true)
                        .setBackgroundTexture(GuiTextures.SLOT_STEAM.get(isHighPressure))
                        .setContainerClicking(true, true))
                .progressBar(workableHandler::getProgressPercent, 79, 35, 20, 16,
                        GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(isHighPressure),
                        ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())
                .slot(this.exportItems, 0, 107, 25, true, false,
                        GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.DUST_OVERLAY_STEAM.get(isHighPressure))
                .widget(new TankWidget(outputFluid, 107, 43, 18, 18).setAlwaysShowFull(true)
                        .setBackgroundTexture(GuiTextures.SLOT_STEAM.get(isHighPressure))
                        .setContainerClicking(true, false))
                .build(getHolder(), entityPlayer);
    }
}
