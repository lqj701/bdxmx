package com.ik.user.center.util;

import com.ik.crm.commons.util.StringUtils;
import com.vdurmont.emoji.EmojiParser;

public class EmojiUtil {

    public static String filterEmoji(String source) {
        return StringUtils.isBlank(source) ? source : EmojiParser.removeAllEmojis(source);
    }

}
