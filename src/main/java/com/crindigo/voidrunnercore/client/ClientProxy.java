package com.crindigo.voidrunnercore.client;

import com.crindigo.voidrunnercore.Tags;
import com.crindigo.voidrunnercore.client.renderer.textures.VRCTextures;
import com.crindigo.voidrunnercore.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Tags.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public void preLoad() {
        super.preLoad();

        VRCTextures.init();
    }
}
