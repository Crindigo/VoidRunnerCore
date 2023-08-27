package com.crindigo.voidrunnercore.common;
import com.crindigo.voidrunnercore.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MODID)
public class ConfigHolder
{
    @Config.Name("Machine Options")
    @Config.Comment("Machine-related configuration")
    public static MachineOptions machine = new MachineOptions();

    public static class MachineOptions
    {
        @Config.Comment("Duration in ticks for High Pressure Botany Grower recipes")
        @Config.RequiresMcRestart()
        public int baseBotanyGrowerDuration = 1200;

        @Config.Comment("EU/t of botany grower")
        @Config.RequiresMcRestart()
        public int botanyGrowerConsumption = 2;
    }
}
