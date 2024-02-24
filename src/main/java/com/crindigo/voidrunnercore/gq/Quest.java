package com.crindigo.voidrunnercore.gq;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Objects;

public class Quest
{
    public static final ItemStack DEFAULT_ICON = new ItemStack(Items.NETHER_STAR);

    public int id;

    public String name;
    public String desc = "(no description)";
    public ItemStack icon = DEFAULT_ICON;
    public List<ItemStack> rewards = Lists.newArrayList();
    public List<Quest> prereqs = Lists.newArrayList();
    public List<ItemStack> requiredItems = Lists.newArrayList();
    public List<FluidStack> requiredFluids = Lists.newArrayList();

    public Quest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void task(ItemStack item) {
        requiredItems.add(item);
    }

    public void task(FluidStack fluid) {
        requiredFluids.add(fluid);
    }

    public static JsonObject getItemList(List<ItemStack> items)
    {
        final JsonObject map = new JsonObject();

        int i = 0;
        for (ItemStack item : items) {
            map.add(i + ":10", getItem(item));
        }

        return map;
    }

    public static JsonObject getFluidList(List<FluidStack> fluidStacks)
    {
        final JsonObject map = new JsonObject();

        int i = 0;
        for (FluidStack fluidStack : fluidStacks) {
            map.add(i + ":10", getFluid(fluidStack));
        }

        return map;
    }

    public static JsonObject getFluid(FluidStack fluid)
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("Amount:3", fluid.amount);
        obj.addProperty("FluidName:8", FluidRegistry.getFluidName(fluid));
        return obj;
    }

    public static JsonObject getItem(ItemStack item)
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("Count:3", item.getCount());
        obj.addProperty("Damage:2", item.getMetadata());
        obj.addProperty("id:8", Objects.requireNonNull(item.getItem().getRegistryName()).toString());
        obj.addProperty("OreDict:8", "");
        final NBTTagCompound tag = item.getTagCompound();
        if ( tag != null ) {
            obj.add("tag:10", NBTConverter.NBTtoJSON_Compound(tag, new JsonObject(), true));
        }
        return obj;
    }

    public JsonObject toJson() {
        final JsonObject result = new JsonObject();

        final JsonArray idList = new JsonArray();
        for (Quest req : prereqs) {
            idList.add(req.id);
        }
        result.add("preRequisites:11", idList);

        final JsonObject properties = new JsonObject();
        final JsonObject betterquesting = new JsonObject();
        betterquesting.addProperty("autoclaim:1", 0);
        betterquesting.addProperty("desc:8", desc);
        betterquesting.addProperty("globalshare:1", 0);
        betterquesting.add("icon:10", getItem(icon));
        betterquesting.addProperty("ignoresview:1", 0);
        betterquesting.addProperty("ismain:1", 0);
        betterquesting.addProperty("issilent:1", 0);
        betterquesting.addProperty("lockedprogress:1", 0);
        betterquesting.addProperty("name:8", name);
        betterquesting.addProperty("questlogic:8", "AND");
        betterquesting.addProperty("repeat_relative:1", 1);
        betterquesting.addProperty("repeattime:3", -1);
        betterquesting.addProperty("simultaneous:1", 0);
        betterquesting.addProperty("snd_complete:8", "minecraft:entity.player.levelup");
        betterquesting.addProperty("snd_update:8", "minecraft:entity.player.levelup");
        betterquesting.addProperty("tasklogic:8", "AND");
        betterquesting.addProperty("visibility:8", "NORMAL");
        properties.add("betterquesting:10", betterquesting);
        result.add("properties:10", properties);

        result.addProperty("questID:3", id);

        final JsonObject rewardObj = new JsonObject();
        if ( rewards != null && !rewards.isEmpty() ) {
            final JsonObject rewardSubObj = new JsonObject();
            rewardSubObj.addProperty("index:3", 0);
            rewardSubObj.addProperty("rewardID:8", "bq_standard:item");
            rewardSubObj.add("rewards:9", getItemList(rewards));
            rewardObj.add("0:10", rewardSubObj);
        }
        result.add("rewards:9", rewardObj);

        final JsonObject tasksObj = new JsonObject();

        if ( requiredItems.isEmpty() && requiredFluids.isEmpty() ) {
            tasksObj.add("0:10", checkboxTask(0));
        } else {
            int ix = 0;
            if ( !requiredItems.isEmpty() ) {
                tasksObj.add(ix + ":10", itemsTask(ix));
                ix++;
            }
            if ( !requiredFluids.isEmpty() ) {
                tasksObj.add(ix + ":10", fluidsTask(ix));
            }
        }
        result.add("tasks:9", tasksObj);

        return result;
    }

    private JsonObject checkboxTask(int index) {
        final JsonObject tasksSubObj = new JsonObject();
        tasksSubObj.addProperty("index:3", index);
        tasksSubObj.addProperty("taskID:8", "bq_standard:checkbox");
        return tasksSubObj;
    }

    private JsonObject itemsTask(int index) {
        final JsonObject tasksSubObj = new JsonObject();
        tasksSubObj.addProperty("index:3", index);
        tasksSubObj.addProperty("autoConsume:1", 0);
        tasksSubObj.addProperty("consume:1", 0);
        tasksSubObj.addProperty("groupDetect:1", 0);
        tasksSubObj.addProperty("ignoreNBT:1", 0);
        tasksSubObj.addProperty("partialMatch:1", 1);
        tasksSubObj.add("requiredItems:9", getItemList(requiredItems));
        tasksSubObj.addProperty("taskID:8", "bq_standard:retrieval");
        return tasksSubObj;
    }

    private JsonObject fluidsTask(int index) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("ignoreNBT:1", 1);
        obj.addProperty("index:3", index);
        obj.add("requiredFluids:9", getFluidList(requiredFluids));
        obj.addProperty("taskID:8", "bq_standard:fluid");
        return obj;
    }
}
