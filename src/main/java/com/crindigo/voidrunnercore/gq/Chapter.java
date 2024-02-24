package com.crindigo.voidrunnercore.gq;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.function.BiFunction;

public class Chapter
{
    public int id;
    public String name;
    public ItemStack icon;
    public String desc;
    public Map<Quest, int[]> quests;
    public Map<Integer, Quest> byId;

    public int index; // stupid temp var for java

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add(Quest quest, int[] pos) {
        this.quests.put(quest, pos);
        this.byId.put(quest.id, quest);
    }

    public void repos(int id, int[] pos) {
        this.quests.put(this.byId.get(id), pos);
    }

    public JsonObject toJson(int chapterOrder) {
        JsonObject obj = new JsonObject();

        obj.addProperty("lineID:3", this.id);
        obj.addProperty("order:3", chapterOrder);

        final JsonObject properties = new JsonObject();
        final JsonObject bq = new JsonObject();

        bq.addProperty("bg_image:8", "");
        bq.addProperty("bg_size:3", 256);
        bq.addProperty("desc:8", desc);
        bq.add("icon:10", Quest.getItem(icon));
        bq.addProperty("name:8", name);
        bq.addProperty("visibility:8", "NORMAL");

        properties.add("betterquesting:10", bq);
        obj.add("properties:10", properties);

        // we need to read config/betterquesting/DefaultQuests.json and extract the positions there.
        // if we don't have a position... try moving in a grid pattern and find the first open spot?
        // -240, -240 => -192, -240 => etc.
        final JsonObject questsObj = new JsonObject();
        this.index = 0;
        quests.forEach((Quest q, int[] pos) -> {
            final JsonObject entry = new JsonObject();
            entry.addProperty("id:3", q.id);
            entry.addProperty("sizeX:3", 24);
            entry.addProperty("sizeY:3", 24);
            entry.addProperty("x:3", pos[0]);
            entry.addProperty("y:3", pos[1]);
            questsObj.add(this.index + ":10", entry);
            this.index++;
        });

        obj.add("quests:9", questsObj);

        return obj;
    }
}
