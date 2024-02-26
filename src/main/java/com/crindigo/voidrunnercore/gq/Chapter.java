package com.crindigo.voidrunnercore.gq;

import com.crindigo.voidrunnercore.VRCLog;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class Chapter
{
    public static final ItemStack DEFAULT_ICON = new ItemStack(Items.BOOK);

    public int id;
    public String name;
    public ItemStack icon = DEFAULT_ICON;
    public String desc = "(no description)";
    public List<Quest> quests = Lists.newArrayList();

    private int index; // stupid temp var for java

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add(Quest quest) {
        this.quests.add(quest);
    }

    public JsonObject toJson(int chapterOrder, Map<Integer, int[]> positions) {
        JsonObject obj = new JsonObject();

        obj.addProperty("lineID:3", this.id);
        obj.addProperty("order:3", chapterOrder);

        final JsonObject properties = new JsonObject();
        final JsonObject bq = new JsonObject();

        bq.addProperty("bg_image:8", "");
        bq.addProperty("bg_size:3", 256);
        bq.addProperty("desc:8", Quest.format(desc));
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
        quests.forEach((Quest q) -> {
            int[] posn = positions == null ? null : positions.get(q.id);
            if ( posn == null ) {
                posn = makePosition(this.index);
                VRCLog.log.info("assigning pos " + posn[0] + " " + posn[1] + " for quest " + q.id);
            } else {
                VRCLog.log.info("using pos " + posn[0] + " " + posn[1] + " for quest " + q.id);
            }
            final JsonObject entry = new JsonObject();
            entry.addProperty("id:3", q.id);
            entry.addProperty("sizeX:3", posn[2]);
            entry.addProperty("sizeY:3", posn[3]);
            entry.addProperty("x:3", posn[0]);
            entry.addProperty("y:3", posn[1]);
            questsObj.add(this.index + ":10", entry);
            this.index++;
        });

        obj.add("quests:9", questsObj);

        return obj;
    }

    private int[] makePosition(int ix) {
        int[] posn = { 0, 0, 24, 24 };
        // start at 480, -240. go 10 across before going down 1.
        // so they all just get shoved on the right and can be moved into place.
        posn[0] = (ix % 10) * 48 + 480;
        posn[1] = (ix / 10) * 48 - 240;
        return posn;
    }
}
