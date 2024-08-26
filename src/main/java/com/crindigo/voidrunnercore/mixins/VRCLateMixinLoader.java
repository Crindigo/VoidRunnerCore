package com.crindigo.voidrunnercore.mixins;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class VRCLateMixinLoader implements ILateMixinLoader
{
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.voidrunnercore.json");
    }
}
