package com.crindigo.voidrunnercore.gq;

import com.crindigo.voidrunnercore.VRCLog;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Book
{
    public Set<Quest> quests = Sets.newHashSet();
    public List<Chapter> chapters = Lists.newArrayList();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void add(Chapter chapter) {
        this.chapters.add(chapter);
        chapter.quests.forEach(this::add);
    }

    private void add(Quest quest) {
        this.quests.add(quest);
    }

    public void save()
    {
        final Map<Integer, Map<Integer, int[]>> positions = readPositions();
        final File bqFile = new File("config/betterquesting/saved_quests/",  "GroovyQuesting.json");
        try {
            Files.createParentDirs(bqFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try ( FileWriter writer = new FileWriter(bqFile, false) ) {
            gson.toJson(toJson(positions), writer);
        } catch (IOException e) {
            VRCLog.log.error(e);
        }
    }

    public JsonObject toJson(Map<Integer, Map<Integer, int[]>> positions) {
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
            questLines.add(i + ":10", chapter.toJson(i, positions.get(chapter.id)));
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

    private Map<Integer, Map<Integer, int[]>> readPositions() {
        final File bqFile = new File("config/betterquesting/saved_quests/",  "GroovyQuesting.json");
        try ( FileReader reader = new FileReader(bqFile) ) {
            Map<Integer, Map<Integer, int[]>> outputMap = new HashMap<>();
            final JsonObject data = (JsonObject) new JsonParser().parse(reader);
            // map of chapter_id => (quest_id => [x, y])
            final JsonObject lines = data.getAsJsonObject("questLines:9");
            lines.entrySet().forEach((Map.Entry<String, JsonElement> e) -> {
                final JsonObject ch = (JsonObject) e.getValue();
                final int chapterId = ch.get("lineID:3").getAsInt();
                outputMap.put(chapterId, new HashMap<>());

                final JsonObject quests = ch.getAsJsonObject("quests:9");
                quests.entrySet().forEach((Map.Entry<String, JsonElement> e2) -> {
                    final JsonObject q = (JsonObject) e2.getValue();
                    final int x = q.get("x:3").getAsInt();
                    final int y = q.get("y:3").getAsInt();
                    final int sx = q.get("sizeX:3").getAsInt();
                    final int sy = q.get("sizeY:3").getAsInt();
                    final int questId = q.get("id:3").getAsInt();
                    outputMap.get(chapterId).put(questId, new int[] { x, y, sx, sy });
                });
            });
            return outputMap;
        } catch (IOException | ClassCastException e) {
            VRCLog.log.error("bq load error", e);
            return Collections.emptyMap();
        }
    }
}
