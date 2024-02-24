package com.crindigo.voidrunnercore.gq;

import com.crindigo.voidrunnercore.util.VRC;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

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

        Quest q1 = new Quest(1, "Intro");
        q1.desc = "This is the description for the intro quest.";
        q1.icon = new ItemStack(Items.NETHER_STAR);

        Quest q2 = new Quest(2, "Digging Around");
        q2.prereqs = ImmutableList.of(q1);
        q2.icon = new ItemStack(Blocks.DIRT);
        q2.desc = "Only have my bare hands to work with here.";
        q2.rewards = ImmutableList.of(new ItemStack(Blocks.STONE, 4, 3));
        q2.requiredItems = ImmutableList.of(new ItemStack(Blocks.COBBLESTONE, 4));
        q2.requiredFluids = ImmutableList.of(FluidRegistry.getFluidStack("water", 1000));

        Chapter chapter = new Chapter(1, "First Chapter");
        chapter.add(q1);
        chapter.add(q2);
        chapter.desc = "Description for first chapter.";
        chapter.icon = new ItemStack(Blocks.DIAMOND_BLOCK);
        Book book = new Book();
        book.add(chapter);

        book.save();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // idk but bq_admin uses 2
    }
}
