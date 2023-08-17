package com.crindigo.voidrunnercore.common.metatileentities;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.common.metatileentities.steam.SteamBotanyGrower;
import com.crindigo.voidrunnercore.common.metatileentities.steam.SteamCrudeMixer;
import com.crindigo.voidrunnercore.common.metatileentities.steam.SteamSifter;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VRCMetaTileEntities
{
    public static SteamCrudeMixer STEAM_CRUDE_MIXER_BRONZE;
    public static SteamCrudeMixer STEAM_CRUDE_MIXER_STEEL;
    public static SteamBotanyGrower STEAM_BOTANY_GROWER_BRONZE;
    public static SteamBotanyGrower STEAM_BOTANY_GROWER_STEEL;
    public static SteamSifter STEAM_SIFTER_BRONZE;
    public static SteamSifter STEAM_SIFTER_STEEL;

    public static void init() {
        STEAM_CRUDE_MIXER_BRONZE = MetaTileEntities.registerMetaTileEntity(31000,
                new SteamCrudeMixer(vrcId("crude_mixer.bronze"), false));
        STEAM_CRUDE_MIXER_STEEL = MetaTileEntities.registerMetaTileEntity(31001,
                new SteamCrudeMixer(vrcId("crude_mixer.steel"), true));

        STEAM_BOTANY_GROWER_BRONZE = MetaTileEntities.registerMetaTileEntity(31002,
                new SteamBotanyGrower(vrcId("botany_grower.bronze"), false));
        STEAM_BOTANY_GROWER_STEEL = MetaTileEntities.registerMetaTileEntity(31003,
                new SteamBotanyGrower(vrcId("botany_grower.steel"), true));

        STEAM_SIFTER_BRONZE = MetaTileEntities.registerMetaTileEntity(31004,
                new SteamSifter(vrcId("steam_sifter.bronze"), false));
        STEAM_SIFTER_STEEL = MetaTileEntities.registerMetaTileEntity(31005,
                new SteamSifter(vrcId("steam_sifter.steel"), true));
    }

    private static @NotNull ResourceLocation vrcId(@NotNull String name) {
        return new ResourceLocation(Tags.MODID, name);
    }
}
