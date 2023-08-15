package com.crindigo.voidrunnercore.api.capability;

import gregtech.api.capability.IFilter;
import gregtech.api.capability.impl.CommonFluidFilters;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public enum VRCFluidFilters implements IFilter<FluidStack>
{
    WATER {
        @Override
        public boolean test(@NotNull FluidStack fluidStack) {
            return CommonFluidFilters.matchesFluid(fluidStack, FluidRegistry.WATER);
        }

        @Override
        public int getPriority() {
            return IFilter.whitelistPriority(1);
        }
    },
    NON_STEAM {
        @Override
        public boolean test(@NotNull FluidStack fluidStack) {
            return !CommonFluidFilters.STEAM.test(fluidStack);
        }

        @Override
        public int getPriority() {
            return IFilter.blacklistPriority(1);
        }
    }
}
