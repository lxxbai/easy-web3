package io.github.lxxbai.parser;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.constants.Constants;
import io.github.lxxbai.datatypes.Param;
import io.github.lxxbai.utils.SplitUtil;

import java.util.List;
import java.util.Objects;

/**
 * param解析
 *
 * @author xxbai
 **/
public class ParamParser {

    private ParamParser() {
    }

    /**
     * 解析字符串参数并返回一个Param对象。
     * 这是parse方法的一个重载版本，它默认使用基数为1来解析参数。
     *
     * @param strParam 待解析的字符串参数。
     * @return 返回解析后的Param对象。
     */
    public static Param parse(String strParam) {
        return parse(strParam, 0);
    }


    /**
     * 解析字符串参数，将其拆分成类型、索引标识和名称三部分。
     *
     * @param strParam   待解析的字符串参数，格式为"类型 [索引标识] 名称"，其中索引标识和名称为可选部分。
     * @param paramIndex 参数的索引编号，用于生成默认参数名。
     * @return Param 参数对象，包含是否索引、名称和类型信息。
     * @throws RuntimeException 当参数字符串为空、格式错误或超过最大允许部分数时抛出。
     */
    public static Param parse(String strParam, Integer paramIndex) {
        // 去除输入字符串两端的空格
        strParam = StrUtil.trim(strParam);
        // 若字符串为空，则抛出异常
        if (StrUtil.isBlank(strParam)) {
            throw new RuntimeException("Expression Param null error !!!");
        }
        // 使用空格分割字符串，得到参数的各部分
        List<String> dataList = SplitUtil.splitSpace(strParam);
        // 检查分割后部分的数量，超过3部分则抛出异常
        if (dataList.size() > 3) {
            throw new RuntimeException("Expression Param error !!!");
        }
        // 第一部分为参数类型
        String type = dataList.get(0);
        String name = null;
        boolean indexed = false;
        // 根据部分数量，判断是否有索引和名称
        switch (dataList.size()) {
            case 2:
                // 第二部分判断是否为索引标识
                if (StrUtil.endWithAnyIgnoreCase(dataList.get(1), Constants.INDEXED_TOPIC)) {
                    indexed = true;
                } else {
                    name = dataList.get(1);
                }
                break;
            case 3:
                // 有三部分时，肯定包含索引标识，需判断第二部分是否为索引标识，否则抛出异常
                indexed = true;
                if (!StrUtil.endWithAnyIgnoreCase(dataList.get(1), Constants.INDEXED_TOPIC)) {
                    throw new RuntimeException("Expression Param error:" + strParam);
                }
                name = dataList.get(2);
            default:
                break;
        }
        // 生成或使用给定的参数名，默认加上参数索引编号作为后缀
        name = Objects.isNull(name) ? Constants.PARAM_PREFIX + (paramIndex + 1) : name;
        // 返回构建的参数对象
        return new Param(indexed, name, type);
    }
}
