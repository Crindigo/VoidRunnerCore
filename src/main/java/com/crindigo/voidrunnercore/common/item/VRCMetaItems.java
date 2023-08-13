package com.crindigo.voidrunnercore.common.item;

import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.common.items.behaviors.TooltipBehavior;
import net.minecraft.client.resources.I18n;

public class VRCMetaItems
{
    private static StandardMetaItem metaItem;

    // meme no idea if i keep it
    public static MetaValueItem INFINITE_LAVA_COVER;

    public static void initMetaItems()
    {
        metaItem = new StandardMetaItem();
        metaItem.setRegistryName("meta_item");
    }

    /**
     * Call from CommonProxy registerItems.
     */
    public static void initSubItems()
    {
        INFINITE_LAVA_COVER = metaItem.addItem(1, "cover.infinite_lava")
                .addComponents(new TooltipBehavior((lines) -> {
                    lines.add(I18n.format("metaitem.cover.infinite_lava.tooltip.1"));
                    lines.add(I18n.format("gregtech.universal.tooltip.produces_fluid", 100));
                }));
    }
}
