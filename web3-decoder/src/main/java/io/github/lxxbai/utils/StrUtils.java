package io.github.lxxbai.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.constants.Constants;


/**
 * @author xxbai
 **/
public class StrUtils {


    /**
     * 将字符串转换为字节数组。
     * 此方法首先移除字符串前导的"0x"或"0X"（如果存在），然后将剩余的十六进制字符串解码为字节数组。
     *
     * @param input 待转换的字符串。该字符串可以以"0x"或"0X"开头，表示十六进制数据的十六进制表示形式。
     * @return 转换后的字节数组。
     */
    public static byte[] strToBytes(String input) {
        // 移除输入字符串可能存在的十六进制前缀
        String inputNoPrefix = cleanHexPrefix(input);
        // 解码移除前缀后的字符串为字节数组
        return HexUtil.decodeHex(inputNoPrefix);
    }


    /**
     * 清除字符串前导的十六进制标记
     *
     * @param input 待处理的字符串
     * @return 如果输入字符串以十六进制前缀开头，则移除该前缀后返回；否则直接返回原字符串。
     */
    public static String cleanHexPrefix(String input) {
        // 检查输入字符串是否包含十六进制前缀，包含则移除前缀
        return containsHexPrefix(input) ? input.substring(2) : input;
    }


    /**
     * 检查给定字符串是否以十六进制前缀开头。
     *
     * @param input 待检查的字符串。
     * @return 如果字符串以'0x'开头，则返回true；否则返回false。
     */
    public static boolean containsHexPrefix(String input) {
        // 检查字符串不为空且长度至少为2，并且第一个字符是'0'，第二个字符是'x'
        return !StrUtil.isEmpty(input) && input.length() > 1 && input.charAt(0) == '0' && input.charAt(1) == 'x';
    }


    /**
     * 将字节数组转换为十六进制字符串。
     *
     * @param input 待转换的字节数组。
     * @return 返回转换后的十六进制字符串，前面会附加一个常量前缀。
     */
    public static String toHexString(byte[] input) {
        // 利用HexUtil工具类将字节数组转为十六进制字符串，并在前面加上预定义的十六进制前缀
        return Constants.HEX_PREFIX + HexUtil.encodeHexStr(input);
    }

}
