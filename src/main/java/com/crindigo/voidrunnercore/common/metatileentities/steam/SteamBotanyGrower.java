package com.crindigo.voidrunnercore.common.metatileentities.steam;

import com.crindigo.voidrunnercore.api.recipes.VRCRecipeMaps;
import com.crindigo.voidrunnercore.client.renderer.textures.VRCTextures;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SteamBotanyGrower extends SteamMetaTileEntity
{
    public SteamBotanyGrower(ResourceLocation metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, VRCRecipeMaps.BOTANY_GROWER_RECIPES, VRCTextures.BOTANY_GROWER, isHighPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamBotanyGrower(metaTileEntityId, isHighPressure);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 4, this, true);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
                .slot(this.importItems, 0, 53, 34, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        VRCTextures.SAPLING_OVERLAY_STEAM.get(isHighPressure))
                .progressBar(workableHandler::getProgressPercent, 79, 35, 20, 16,
                        GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(isHighPressure),
                        ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())

                .slot(this.exportItems, 0, 107, 25, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 1, 125, 25, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 2, 107, 43, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 3, 125, 43, GuiTextures.SLOT_STEAM.get(isHighPressure))

                .build(getHolder(), entityPlayer);
    }

    //@SideOnly(Side.CLIENT)
    //@Override
    //protected void randomDisplayTick(float x, float y, float z, EnumParticleTypes flame, EnumParticleTypes smoke) {
    //    super.randomDisplayTick(x, y, z, flame, smoke);
        //if (GTValues.RNG.nextBoolean()) {
        //    getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y + 0.5f, z, 0, 0, 0);
        //}
    //}
}
