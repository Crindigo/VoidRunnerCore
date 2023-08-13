package com.crindigo.voidrunnercore.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.crindigo.voidrunnercore.client.renderer.textures.VRCTextures;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;


public class CoverInfiniteLava extends CoverBehavior implements ITickable {
    public CoverInfiniteLava(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
    }

    @Override
    public boolean canAttach() {
        return this.coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.attachedSide) != null;
    }

    @Override
    public void renderCover(CCRenderState ccRenderState, Matrix4 matrix4, IVertexOperation[] iVertexOperations,
                            Cuboid6 cuboid6, BlockRenderLayer blockRenderLayer) {
        VRCTextures.INFINITE_LAVA.renderSided(this.attachedSide, cuboid6, ccRenderState, iVertexOperations, matrix4);
    }

    @Override
    public void update() {
        if (!this.coverHolder.getWorld().isRemote && this.coverHolder.getOffsetTimer() % 20L == 0L) {
            IFluidHandler fluidHandler = this.coverHolder.getCapability(
                    CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.attachedSide);
            if (fluidHandler != null) {
                fluidHandler.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
            }
        }
    }
}
