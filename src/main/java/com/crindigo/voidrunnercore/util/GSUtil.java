package com.crindigo.voidrunnercore.util;

import gregtech.api.unification.material.Material;
import net.minecraft.util.ResourceLocation;

public class GSUtil
{
    // Handle Groovy's autoboxing bullshit.
    public static Material.Builder newMaterialBuilder(Integer id, ResourceLocation resource) {
        return new Material.Builder(id, resource);
    }

    public static Material.Builder newMaterialBuilder(int id, ResourceLocation resource) {
        return new Material.Builder(id, resource);
    }
}
