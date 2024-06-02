package io.github.lxxbai.datatypes;

import cn.hutool.core.util.ArrayUtil;
import io.github.lxxbai.decoder.TypeDecoder;
import io.github.lxxbai.utils.StrUtils;


import java.util.List;
import java.util.Map;

/**
 * 方法
 *
 * @author shupeng_liao
 */
public class Function extends Entry {

    private static final int ENCODED_SIGN_LENGTH = 4;

    public Function(String expression) {
        super(expression);
    }

    /**
     * 解码给定的输入字符串=>list类型
     *
     * @param input 待解码的字符串。
     * @return 解码后的对象列表。
     */
    public List<Object> decode(String input) {
        // 将输入字符串转换为编码数据
        byte[] encoded = toEncodedData(input);
        // 使用TypeDecoder解码编码数据，并转换为对象列表
        return TypeDecoder.decodeParamsToList(encoded, super.getInputs());
    }

    /**
     * 解码给定的字节数组=>list类型
     *
     * @param encoded 加密字节数组
     * @return 解码后的对象列表。
     */
    public List<Object> decode(byte[] encoded) {
        // 使用TypeDecoder解码编码数据，并转换为对象列表
        return TypeDecoder.decodeParamsToList(encoded, super.getInputs());
    }

    /**
     * 将输入的字符串转换为编码后的数据字节数组。
     * 这个方法首先将字符串转换为字节数组，然后返回去除签名长度后的字节数组。
     *
     * @param input 需要转换的字符串。
     * @return 编码后的数据字节数组，不包括签名长度部分。
     */
    private byte[] toEncodedData(String input) {
        // 将输入字符串转换为字节数组
        byte[] encoded = StrUtils.strToBytes(input);
        // 返回去除签名长度后的字节数组
        return ArrayUtil.sub(encoded, ENCODED_SIGN_LENGTH, encoded.length);
    }


    /**
     * 将输入的字符串解码为一个Map对象。
     *
     * @param input 待解码的字符串。
     * @return 解码后的参数映射，其中键值对的形式反映了原始输入的数据结构。
     */
    public Map<String, Object> decodeToMap(String input) {
        // 将输入字符串转换为编码后的数据
        byte[] encoded = toEncodedData(input);
        // 使用TypeDecoder将编码后的数据解码为Map对象，解码过程依据super.getInputs()提供的输入格式
        return TypeDecoder.decodeParamsToMap(encoded, super.getInputs());
    }


    /**
     * 将输入的字符串解码为一个Map对象。
     *
     * @param encoded 待解码加密字节数组
     * @return 解码后的参数映射，其中键值对的形式反映了原始输入的数据结构。
     */
    public Map<String, Object> decodeToMap(byte[] encoded) {
        // 使用TypeDecoder将编码后的数据解码为Map对象，解码过程依据super.getInputs()提供的输入格式
        return TypeDecoder.decodeParamsToMap(encoded, super.getInputs());
    }


    /**
     * 从数据字节数组中提取签名。
     * <p>该方法会从数据数组的开始位置提取指定长度的字节作为签名，并返回这部分字节。</p>
     *
     * @param data 包含签名数据的字节数组。
     * @return 提取出来的签名字节数组。
     */
    public static byte[] extractSignature(byte[] data) {
        // 提取签名，从数组开始位置提取ENCODED_SIGN_LENGTH长度的字节
        return ArrayUtil.sub(data, 0, ENCODED_SIGN_LENGTH);
    }


    @Override
    public byte[] encodeSignature() {
        return extractSignature(super.encodeSignature());
    }

}
