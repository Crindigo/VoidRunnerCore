package com.crindigo.voidrunnercore.gq;

import com.crindigo.voidrunnercore.util.VRC;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ReloadQuestsCommand extends CommandBase
{
    @Override
    public String getName() {
        return "gqreload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/gqreload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(VRC.tct("voidrunnercore.command.reloading_quests"));
        RequestReloadEvent.publish();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // idk but bq_admin uses 2
    }
}
