package com.crindigo.voidrunnercore.gq;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;

import java.util.Set;
import java.util.TreeSet;

// https://github.com/CleanroomMC/BetterQuesting/blob/1.12/src/main/java/betterquesting/api/utils/NBTConverter.java
public class NBTConverter
{
    public static JsonObject NBTtoJSON_Compound(NBTTagCompound parent, JsonObject jObj, boolean format) {
        if (parent == null) {
            return jObj;
        }

        Set<String> keySet = new TreeSet<>(parent.getKeySet());
        for (String key : keySet) {
            NBTBase tag = parent.getTag(key);

            if (format) {
                jObj.add(key + ":" + tag.getId(), NBTtoJSON_Base(tag, true));
            } else {
                jObj.add(key, NBTtoJSON_Base(tag, false));
            }
        }

        return jObj;
    }

    private static JsonElement NBTtoJSON_Base(NBTBase tag, boolean format) {
        if (tag == null) {
            return new JsonObject();
        }

        if (tag.getId() >= 1 && tag.getId() <= 6) {
            return new JsonPrimitive(getNumber(tag));
        }
        if (tag instanceof NBTTagString) {
            return new JsonPrimitive(((NBTTagString) tag).getString());
        } else if (tag instanceof NBTTagCompound) {
            return NBTtoJSON_Compound((NBTTagCompound) tag, new JsonObject(), format);
        } else if (tag instanceof NBTTagList) {
            if (format) {
                JsonObject jAry = new JsonObject();

                NBTTagList tagList = (NBTTagList) tag;

                for (int i = 0; i < tagList.tagCount(); i++) {
                    jAry.add(i + ":" + tagList.get(i).getId(), NBTtoJSON_Base(tagList.get(i), true));
                }

                return jAry;
            } else {
                JsonArray jAry = new JsonArray();

                NBTTagList tagList = (NBTTagList) tag;

                for (NBTBase t : tagList) {
                    jAry.add(NBTtoJSON_Base(t, false));
                }

                return jAry;
            }
        } else if (tag instanceof NBTTagByteArray) {
            JsonArray jAry = new JsonArray();

            for (byte b : ((NBTTagByteArray) tag).getByteArray()) {
                jAry.add(new JsonPrimitive(b));
            }

            return jAry;
        } else if (tag instanceof NBTTagIntArray) {
            JsonArray jAry = new JsonArray();

            for (int i : ((NBTTagIntArray) tag).getIntArray()) {
                jAry.add(new JsonPrimitive(i));
            }

            return jAry;
        } else if (tag instanceof NBTTagLongArray) {
            JsonArray jAry = new JsonArray();

            for (long l : readLongArray((NBTTagLongArray) tag)) {
                jAry.add(new JsonPrimitive(l));
            }

            return jAry;
        } else {
            return new JsonObject(); // No valid types found. We'll just return this to prevent a NPE
        }
    }

    public static long[] readLongArray(NBTTagLongArray tag) {
        if (tag == null) return new long[0];

        String[] entry = tag.toString().replaceAll("[\\[\\]L;]", "").split(","); // Cut off square braces and "L;" before splitting elements
        final long[] ary = new long[entry.length];
        for (int i = 0; i < entry.length; i++) {
            try {
                ary[i] = Long.parseLong(entry[i]);
            } catch (Exception ignored) {
            }
        }

        return ary;
    }

    public static Number getNumber(NBTBase tag) {
        if (tag instanceof NBTTagByte) {
            return ((NBTTagByte) tag).getByte();
        } else if (tag instanceof NBTTagShort) {
            return ((NBTTagShort) tag).getShort();
        } else if (tag instanceof NBTTagInt) {
            return ((NBTTagInt) tag).getInt();
        } else if (tag instanceof NBTTagFloat) {
            return ((NBTTagFloat) tag).getFloat();
        } else if (tag instanceof NBTTagDouble) {
            return ((NBTTagDouble) tag).getDouble();
        } else if (tag instanceof NBTTagLong) {
            return ((NBTTagLong) tag).getLong();
        } else {
            return 0;
        }
    }
}
