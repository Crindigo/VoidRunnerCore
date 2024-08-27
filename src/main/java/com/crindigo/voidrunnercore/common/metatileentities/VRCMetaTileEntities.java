package com.crindigo.voidrunnercore.common.metatileentities;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.common.metatileentities.multi.*;
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
    //public static MetaTileEntityGreenhouse GREENHOUSE;
    public static MetaTileEntityPrimitiveEvapPool PRIMITIVE_EVAP_POOL;
    public static MetaTileEntityTrashCollector TRASH_COLLECTOR;
    public static MetaTileEntityDigester DIGESTER;
    public static MetaTileEntityBloomery BLOOMERY;
    public static MetaTileEntityElectricBloomery ELECTRIC_BLOOMERY;


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

        //GREENHOUSE = MetaTileEntities.registerMetaTileEntity(31006,
        //        new MetaTileEntityGreenhouse(vrcId("greenhouse")));

        PRIMITIVE_EVAP_POOL = MetaTileEntities.registerMetaTileEntity(31007,
                new MetaTileEntityPrimitiveEvapPool(vrcId("primitive_evap_pool")));

        TRASH_COLLECTOR = MetaTileEntities.registerMetaTileEntity(31008,
                new MetaTileEntityTrashCollector(vrcId("trash_collector")));

        DIGESTER = MetaTileEntities.registerMetaTileEntity(31009,
                new MetaTileEntityDigester(vrcId("digester")));

        BLOOMERY = MetaTileEntities.registerMetaTileEntity(31010, new MetaTileEntityBloomery(vrcId("bloomery")));

        ELECTRIC_BLOOMERY = MetaTileEntities.registerMetaTileEntity(31011,
                new MetaTileEntityElectricBloomery(vrcId("electric_bloomery")));

    }

    private static @NotNull ResourceLocation vrcId(@NotNull String name) {
        return new ResourceLocation(Tags.MODID, name);
    }
}
