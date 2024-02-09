package com.crindigo.voidrunnercore.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.crindigo.voidrunnercore.client.renderer.textures.VRCTextures;
import gregtech.api.cover.CoverBase;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverableView;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;


public class CoverInfiniteLava extends CoverBase implements ITickable {

    public CoverInfiniteLava(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView,
                             @NotNull EnumFacing attachedSide) {
        super(definition, coverableView, attachedSide);
    }

    @Override
    public boolean canAttach(@NotNull CoverableView coverableView, @NotNull EnumFacing enumFacing) {
        return coverableView.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, getAttachedSide()) != null;
    }

    @Override
    public void renderCover(CCRenderState ccRenderState, Matrix4 matrix4, IVertexOperation[] iVertexOperations,
                            Cuboid6 cuboid6, BlockRenderLayer blockRenderLayer) {
        VRCTextures.INFINITE_LAVA.renderSided(getAttachedSide(), cuboid6, ccRenderState, iVertexOperations, matrix4);
    }

    @Override
    public void update() {
        if (!getWorld().isRemote && getOffsetTimer() % 20L == 0L) {
            IFluidHandler fluidHandler = getCoverableView().getCapability(
                    CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, getAttachedSide());
            if (fluidHandler != null) {
                fluidHandler.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
            }
        }
    }
}
