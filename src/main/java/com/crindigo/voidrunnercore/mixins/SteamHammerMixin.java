package com.crindigo.voidrunnercore.mixins;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.steam.SteamHammer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SteamHammer.class)
public class SteamHammerMixin extends SteamMetaTileEntity
{
    public SteamHammerMixin(ResourceLocation metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, isHighPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamHammer(metaTileEntityId, isHighPressure);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 2, this, true);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player)
                .slot(this.importItems, 0, 53, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.HAMMER_OVERLAY_STEAM.get(isHighPressure))
                .progressBar(workableHandler::getProgressPercent, 79, 25, 20, 18,
                        GuiTextures.PROGRESS_BAR_HAMMER_STEAM.get(isHighPressure), ProgressWidget.MoveType.VERTICAL_DOWNWARDS,
                        workableHandler.getRecipeMap())
                .image(79, 41, 20, 18, GuiTextures.PROGRESS_BAR_HAMMER_BASE_STEAM.get(isHighPressure))
                .slot(this.exportItems, 0, 107, 25, true, false, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .slot(this.exportItems, 0, 125, 25, true, false, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .build(getHolder(), player);
    }
}
