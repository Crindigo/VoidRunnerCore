package com.crindigo.voidrunnercore.gq;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class Quest
{
    public int id;

    public String name;
    public String desc;
    public ItemStack icon;
    public ItemStack[] rewards;
    public Quest[] prereqs;
    public ItemStack[] requirements;

    public Quest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static JsonObject getItemList(ItemStack[] items)
    {
        final JsonObject map = new JsonObject();

        int i = 0;
        for (ItemStack item : items) {
            map.add(i + ":10", getItem(item));
        }

        return map;
    }

    public static JsonObject getItem(ItemStack item)
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("Count:3", item.getCount());
        obj.addProperty("Damage:2", item.getMetadata());
        obj.addProperty("id:8", Objects.requireNonNull(item.getItem().getRegistryName()).toString());
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
        final JsonObject rewardSubObj = new JsonObject();
        rewardSubObj.addProperty("index:3", 0);
        rewardSubObj.addProperty("rewardID:8", "bq_standard:item");
        rewardSubObj.add("rewards:9", getItemList(rewards));
        rewardObj.add("0:10", rewardSubObj);
        result.add("rewards:9", rewardObj);

        final JsonObject tasksObj = new JsonObject();
        final JsonObject tasksSubObj = new JsonObject();
        tasksSubObj.addProperty("autoConsume:1", 0);
        tasksSubObj.addProperty("consume:1", 0);
        tasksSubObj.addProperty("groupDetect:1", 0);
        tasksSubObj.addProperty("ignoreNBT:1", 0);
        tasksSubObj.addProperty("index:3", 0);
        tasksSubObj.addProperty("partialMatch:1", 1);
        tasksSubObj.add("requiredItems:9", getItemList(requirements));
        tasksSubObj.addProperty("taskID:8", "bq_standard:retrieval");
        tasksObj.add("0:10", tasksSubObj);
        result.add("tasks:9", tasksObj);

        return result;
    }
}
