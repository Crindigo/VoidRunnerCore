package com.crindigo.voidrunnercore.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class VRC
{
    public static TextComponentTranslation tct(String translationKey, Object... args)
    {
        return new TextComponentTranslation(translationKey.replace("@", "voidrunnercore"), args);
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation("voidrunnercore", id);
    }
}
