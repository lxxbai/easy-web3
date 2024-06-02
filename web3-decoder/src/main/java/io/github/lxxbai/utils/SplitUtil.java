package io.github.lxxbai.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxbai
 **/
public class SplitUtil {


    /**
     * 按照给定数据切割,括号内不切割
     *
     * @param input     输入字符串
     * @param delimiter 切割字段
     * @return 切割后的集合
     * @throws IllegalArgumentException 如果括号不匹配、输入为null/空或分隔符为空
     */
    public static List<String> split(String input, char delimiter) throws IllegalArgumentException {
        // 验证输入
        if (input == null) {
            throw new IllegalArgumentException("输入不能为空");
        }
        // 左括号的数量
        int leftNum = 0;
        int start = 0;
        List<String> result = new ArrayList<>();
        char[] chars = input.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            if (chars[i] == delimiter && leftNum == 0) {
                result.add(input.substring(start, i));
                start = i + 1;
            } else if (chars[i] == '(') {
                leftNum++;
            } else if (chars[i] == ')') {
                if (leftNum <= 0) {
                    throw new IllegalArgumentException("右括号')'多于左括号'('");
                }
                leftNum--;
            }
        }
        // 检查括号是否匹配
        if (leftNum != 0) {
            throw new IllegalArgumentException("左括号'('多于右括号')'");
        }
        // 添加剩余部分
        String substring = input.substring(start).trim();
        if (!substring.isEmpty()) {
            result.add(substring);
        }
        return result;
    }

    /**
     * 按照逗号切割,括号内不切割
     *
     * @param input 参数
     * @return 切割后的集合
     */
    public static List<String> splitComma(String input) {
        return split(input, ',');
    }

    /**
     * 按照空格切割,括号内不切割
     *
     * @param input 参数
     * @return 切割后的集合
     */
    public static List<String> splitSpace(String input) {
        return split(input, ' ');
    }
}
