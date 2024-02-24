package com.crindigo.voidrunnercore.gq;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RequestReloadEvent extends Event
{
    public static void publish() {
        MinecraftForge.EVENT_BUS.post(new RequestReloadEvent());
    }
}
