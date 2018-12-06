package com.ik.user.center.util;

import java.util.Random;

/**
 * 生成随机数
 *
 */
public class RandomUtil {
    // 随机数字符个数
    private static int codeCount = 8;

    private static char[] number = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private static char[] upperCaseLetter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static char[] lowerCaseLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    // private static char[] specialSymbol = {'~', '!', '@', '#', '$', '%', '^', '&', '*', '.'};

    // 生成随机数
    private static Random random = new Random();

    public static StringBuilder createCode() {
        StringBuilder code = new StringBuilder();
        // 随机产生验证码字符
        String randomCode = null;
        for (int i = 0; i < RandomUtil.codeCount; i++) {
            switch (i) {
                case 0:
                    randomCode = String.valueOf(
                            RandomUtil.upperCaseLetter[RandomUtil.random.nextInt(RandomUtil.upperCaseLetter.length)]);
                    code.append(randomCode);
                    break;
                case 1:
                    randomCode = String.valueOf(
                            RandomUtil.lowerCaseLetter[RandomUtil.random.nextInt(RandomUtil.lowerCaseLetter.length)]);
                    code.append(randomCode);
                    break;
                // case 5:
                // randomCode = String.valueOf(
                // RandomUtil.specialSymbol[RandomUtil.random.nextInt(RandomUtil.specialSymbol.length)]);
                // code.append(randomCode);
                // break;
                default:
                    randomCode = String.valueOf(RandomUtil.number[RandomUtil.random.nextInt(RandomUtil.number.length)]);
                    code.append(randomCode);
                    break;
            }
        }
        return code;
    }
}
