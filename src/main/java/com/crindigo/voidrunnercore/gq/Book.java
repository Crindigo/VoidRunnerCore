package com.crindigo.voidrunnercore.gq;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Book
{
    public List<Quest> quests;
    public List<Chapter> chapters;
    public Map<Integer, Chapter> byId;

    public void add(Chapter chapter) {
        this.chapters.add(chapter);
        this.byId.put(chapter.id, chapter);
    }

    public void add(Quest quest) {
        this.quests.add(quest);
    }

    public JsonObject toJson() {
        final JsonObject obj = new JsonObject();
        obj.addProperty("build:8", "4.1.0");
        obj.addProperty("format:8", "2.0.0");

        final JsonObject questsObj = new JsonObject();
        int i = 0;
        for ( Quest quest : quests ) {
            questsObj.add(i + ":10", quest.toJson());
            i++;
        }
        obj.add("questDatabase:9", questsObj);

        final JsonObject questLines = new JsonObject();
        i = 0;
        for ( Chapter chapter : chapters ) {
            questLines.add(i + ":10", chapter.toJson(i));
            i++;
        }
        obj.add("questLines:9", questLines);

        obj.add("questSettings:10", getSettingsJson());

        return obj;
    }

    @NotNull
    private static JsonObject getSettingsJson() {
        final JsonObject settings = new JsonObject();
        final JsonObject bq = new JsonObject();
        bq.addProperty("editmode:1", 1);
        bq.addProperty("hardcore:1", 0);
        bq.addProperty("home_anchor_x:5", 0.5);
        bq.addProperty("home_anchor_y:5", 0.0);
        bq.addProperty("home_image:8", "betterquesting:textures/gui/default_title.png");
        bq.addProperty("home_offset_x:3", -128);
        bq.addProperty("home_offset_y:3", 0);
        bq.addProperty("livesdef:3", 3);
        bq.addProperty("livesmax:3", 10);
        bq.addProperty("pack_name:8", "");
        bq.addProperty("pack_version:3", 0);
        bq.addProperty("party_enable:1", 1);

        settings.add("betterquesting:10", bq);
        return settings;
    }
}
