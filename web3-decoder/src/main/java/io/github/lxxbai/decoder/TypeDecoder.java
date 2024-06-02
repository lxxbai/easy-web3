package io.github.lxxbai.decoder;


import io.github.lxxbai.constants.Constants;
import io.github.lxxbai.datatypes.Param;
import io.github.lxxbai.datatypes.types.SolidityType;
import io.github.lxxbai.utils.StrUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * 类型decode工具
 *
 * @author xxbai
 **/
public class TypeDecoder {

    private TypeDecoder() {
    }

    /**
     * 解码整数。将字节序列中的特定区间（从指定偏移量开始的32位）转换为BigInteger类型。
     *
     * @param encoded 待解码的字节序列。
     * @param offset  解码的起始偏移量。
     * @return 从字节序列指定位置开始的32位字节所代表的BigInteger值。
     */
    public static BigInteger decodeInt(byte[] encoded, int offset) {
        // 从指定偏移量开始，复制出32位的字节序列，并转换为BigInteger
        return new BigInteger(Arrays.copyOfRange(encoded, offset, offset + Constants.INT_32_SIZE));
    }

    /**
     * 解码为无符号整数的字节数据。
     *
     * @param encoded 编码后的字节数据。
     * @param offset  数据开始的偏移量。
     * @return 解码后的无符号整数。
     */
    public static BigInteger decodeUint(byte[] encoded, int offset) {
        // 从指定偏移量开始，复制出32个字节的数据，并以1为底数创建BigInteger对象
        return new BigInteger(1, Arrays.copyOfRange(encoded, offset, offset + Constants.INT_32_SIZE));
    }


    /**
     * 解码地址。
     * 该方法将给定的编码字节序列（encoded）中指定偏移量开始的部分解码为BigInteger，然后将这个BigInteger转换为一个地址字符串。
     *
     * @param encoded 编码后的字节序列，包含地址信息。
     * @param offset  解码的起始偏移量。
     * @return 转换后的地址字符串。
     */
    public static String decodeAddress(byte[] encoded, int offset) {
        //BigInteger value = decodeInt(encoded, offset);
        // 将BigInteger转换为地址字符串
        //byte[] bytes = ByteUtil.bigIntegerToBytes(value, 20);
        byte[] bytes = Arrays.copyOfRange(encoded, offset + 12, offset + Constants.INT_32_SIZE);
        return StrUtils.toHexString(bytes);
    }

    /**
     * 将编码后的字节数组解码为布尔值。
     *
     * @param encoded 编码后的字节数组。
     * @param offset  解码的起始偏移量。
     * @return 解码后的布尔值，如果解码的整数值不为0，则返回true；否则返回false。
     */
    public static Boolean decodeBool(byte[] encoded, int offset) {
        // 将字节数组根据指定偏移量解码为BigInteger类型
        BigInteger value = decodeInt(encoded, offset);
        // 判断解码后的整数值是否不为0，并返回相应的布尔值
        return value.intValue() != 0;
    }

    /**
     * 解码字节数组中的数据。
     * 该方法首先从给定的字节数组和偏移量处解码出一个整数，表示后续数据的长度。
     * 如果解码出的长度为0，则返回一个空的字节数组。
     * 否则，将从偏移量之后的位置开始，取出指定长度的字节数据，并返回这部分数据。
     *
     * @param encoded 待解码的字节数组。
     * @param offset  解码的起始偏移量。
     * @return 解码后的字节数据。如果解码出的长度为0，则返回一个空的字节数组。
     */
    public static byte[] decodeBytes(byte[] encoded, int offset) {
        // 解码出第一个整数，获取后续数据长度
        int len = decodeInt(encoded, offset).intValue();
        if (len == 0) {
            // 如果长度为0，直接返回空数组
            return new byte[0];
        }
        // 调整偏移量，准备读取实际数据
        offset += Constants.INT_32_SIZE;
        // 根据解码出的长度，从指定偏移量开始复制数据，并返回
        return Arrays.copyOfRange(encoded, offset, offset + len);
    }


    /**
     * 从给定的编码字节数组中解码出长度为32的字节序列。
     *
     * @param encoded 给定的编码字节数组。
     * @param offset  解码开始的偏移量。
     * @return 从指定偏移量开始，长度为32的字节数组。
     */
    public static byte[] decodeBytes32(byte[] encoded, int offset) {
        // 根据偏移量和INT_32_SIZE的长度，复制出需要的字节序列
        return Arrays.copyOfRange(encoded, offset, offset + Constants.INT_32_SIZE);
    }


    /**
     * 解码动态数组。
     * 该方法用于将以字节形式编码的动态数组解码为Java列表。首先，它从给定的字节数组和起始偏移量处解码出数组的长度，
     * 然后调用另一个方法以解码出具体元素类型的静态数组。
     *
     * @param encoded     编码后的字节数组，包含动态数组的数据。
     * @param origOffset  原始字节数据的起始偏移量，在此数组中寻找动态数组的长度。
     * @param elementType 固定的元素类型，用于指导解码过程。
     * @return 解码后的元素列表，列表中的元素类型由elementType参数指定。
     */
    public static <E> List<E> decodeDynamicArray(byte[] encoded, int origOffset, SolidityType<E> elementType) {
        // 解码动态数组的长度
        int len = TypeDecoder.decodeInt(encoded, origOffset).intValue();
        // 根据解码出的长度，解码整个动态数组并返回
        return decodeStaticArray(encoded, origOffset, elementType, len);
    }

    /**
     * 解码静态数组。
     * 该方法用于将经过编码的字节数组解码为指定类型的元素列表。支持静态类型和动态类型的元素。
     *
     * @param encoded     经过编码的字节数组。
     * @param origOffset  原始偏移量，即解码的起始位置。
     * @param elementType 元素类型，指定要解码的元素类型，必须是SolidityType的实例。
     * @param len         数组的长度。如果解码动态类型数组，这个参数指定动态数组中元素的数量。
     * @return 解码后的元素列表。
     */
    public static <E> List<E> decodeStaticArray(byte[] encoded, int origOffset, SolidityType<E> elementType, Integer len) {
        int offset = origOffset;
        // 初始化返回的元素列表，容量为len
        List<E> retList = new ArrayList<>(len);
        // 遍历数组中的每个元素进行解码
        for (int i = 0; i < len; i++) {
            retList.add(elementType.decodeResult(encoded, offset));
            // 更新偏移量，为下一个元素的解码做准备
            offset += elementType.getFixedSize();
        }
        // 返回解码后的元素列表
        return retList;
    }

    /**
     * 将编码后的参数解码为一个Map对象。
     *
     * @param encoded 编码后的参数数据数组。
     * @param params  参数列表，包含参数的类型和名称。
     * @return 一个LinkedHashMap，键为参数名称，值为解码后的参数值。
     */
    public static Map<String, Object> decodeParamsToMap(byte[] encoded, List<Param> params) {
        // 初始化结果映射，保证参数的顺序性
        Map<String, Object> resultMap = new LinkedHashMap<>();
        // 用于追踪当前解码位置的偏移量
        int offset = 0;
        // 遍历参数列表进行解码
        for (Param param : params) {
            // 获取参数的类型
            SolidityType<?> type = param.getType();
            // 根据类型解码参数值
            Object decoded = type.decodeResult(encoded, offset);
            // 将解码后的值放入结果映射中
            resultMap.put(param.getName(), decoded);
            // 更新偏移量，为下一个参数解码做准备
            offset += type.getFixedSize();
        }
        return resultMap;
    }


    /**
     * 将编码后的参数数组解码为列表对象。
     *
     * @param encoded 编码后的参数数组。
     * @param params  参数信息列表，包含需要解码的参数详情。
     * @return 解码后的参数值列表。
     */
    public static List<Object> decodeParamsToList(byte[] encoded, List<Param> params) {
        // 将编码后的参数数组解码为键值对映射表
        Map<String, Object> objectMap = decodeParamsToMap(encoded, params);
        // 将映射表中的值转换为列表对象
        return mapValueToList(objectMap);
    }

    /**
     * 将给定的Map对象的值转换为List形式。
     * 如果Map的值是另一个Map（LinkedHashMap），则会递归地将这个Map的值转换为List，
     * 否则，直接将值添加到结果List中。
     *
     * @param objectMap 待转换的Map对象，其值可以是任意类型，包括其他Map。
     * @return 返回一个List对象，包含所有给定Map的值，如果值是Map，则包含其递归转换后的结果。
     */
    public static List<Object> mapValueToList(Map<String, Object> objectMap) {
        List<Object> resultList = new ArrayList<>();
        // 遍历Map的所有值，对每个值进行处理
        for (Object value : objectMap.values()) {
            // 如果值是LinkedHashMap，则递归调用本函数转换该Map的值
            if (value instanceof LinkedHashMap) {
                resultList.add(mapValueToList((Map<String, Object>) value));
            } else {
                // 如果值不是Map，则直接添加到结果List中
                resultList.add(value);
            }
        }
        return resultList;
    }
}
