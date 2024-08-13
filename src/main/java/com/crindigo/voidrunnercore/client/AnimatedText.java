package com.crindigo.voidrunnercore.client;

import gregtech.client.utils.TooltipHelper;
import net.minecraft.util.text.TextFormatting;

public class AnimatedText
{
    private static final TextFormatting[] ALL_COLORS = new TextFormatting[]{
            TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.AQUA,
            TextFormatting.DARK_AQUA, TextFormatting.DARK_BLUE, TextFormatting.BLUE, TextFormatting.DARK_PURPLE,
            TextFormatting.LIGHT_PURPLE};

    private static TextFormatting[] shift(TextFormatting[] colors, int amount) {
        amount = amount % colors.length;
        if ( amount == 0 ) {
            return colors;
        }
        TextFormatting[] shifted = new TextFormatting[colors.length];
        // copy so [1 2 3 4 5] looks like [null 1 2 3 4]
        System.arraycopy(colors, 0, shifted, amount, colors.length - amount);
        // now copy to fill the first part
        System.arraycopy(colors, colors.length - amount, shifted, 0, amount);
        return shifted;
    }

    // we need a way to create all the shifted arrays at compile time instead of wasting GC
    public static final TooltipHelper.GTFormatCode[] ORANGE_ANIM = new TooltipHelper.GTFormatCode[3];
    public static final TooltipHelper.GTFormatCode[] VOID_ANIM = new TooltipHelper.GTFormatCode[4];
    public static final TooltipHelper.GTFormatCode[] RAINBOW_ANIM = new TooltipHelper.GTFormatCode[10];
    static {
        final TextFormatting[] base = new TextFormatting[]{TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW};
        ORANGE_ANIM[0] = TooltipHelper.createNewCode(1, base);
        ORANGE_ANIM[1] = TooltipHelper.createNewCode(1, shift(base, 1));
        ORANGE_ANIM[2] = TooltipHelper.createNewCode(1, shift(base, 2));

        final TextFormatting[] voidBase = new TextFormatting[]{TextFormatting.DARK_PURPLE, TextFormatting.DARK_PURPLE, TextFormatting.DARK_PURPLE, TextFormatting.WHITE};
        VOID_ANIM[0] = TooltipHelper.createNewCode(20, voidBase);
        VOID_ANIM[1] = TooltipHelper.createNewCode(15, shift(voidBase, 1));
        VOID_ANIM[2] = TooltipHelper.createNewCode(10, shift(voidBase, 2));
        VOID_ANIM[3] = TooltipHelper.createNewCode(5, shift(voidBase, 3));

        for ( int i = 0; i < 10; i++ ) {
            RAINBOW_ANIM[i] = TooltipHelper.createNewCode(1, shift(ALL_COLORS, i));
        }
    }

    public static String animate(String text, TooltipHelper.GTFormatCode[] animation)
    {
        // formatCode[0] + "A" + formatCode[1] + "B" + formatCode[2] + "C"
        final int len = animation.length;
        final char[] chars = text.toCharArray();
        final StringBuilder builder = new StringBuilder(text.length() * 3);

        int j = 0;
        for (char aChar : chars) {
            if (aChar != ' ') {
                builder.append(animation[j % len]);
                j++;
            }
            builder.append(aChar);
        }

        return builder.toString();
    }
}
