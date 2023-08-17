package com.crindigo.voidrunnercore.common.metatileentities.steam;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SteamSifter extends SteamMetaTileEntity
{
    public SteamSifter(ResourceLocation metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, isHighPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamSifter(metaTileEntityId, isHighPressure);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(1, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(6, this, true);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
                .slot(this.importItems, 0, 52, 34, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.DUST_OVERLAY_STEAM.get(isHighPressure))
                .progressBar(workableHandler::getProgressPercent, 78, 35, 20, 16,
                        GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(isHighPressure),
                        ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())

                .slot(this.exportItems, 0, 106, 25, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 1, 124, 25, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 2, 142, 25, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 3, 106, 43, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 4, 124, 43, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 5, 142, 43, GuiTextures.SLOT_STEAM.get(isHighPressure))

                .build(getHolder(), entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void randomDisplayTick(float x, float y, float z, EnumParticleTypes flame, EnumParticleTypes smoke) {
        super.randomDisplayTick(x, y, z, flame, smoke);
        //if (GTValues.RNG.nextBoolean()) {
        //    getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y + 0.5f, z, 0, 0, 0);
        //}
    }
}
