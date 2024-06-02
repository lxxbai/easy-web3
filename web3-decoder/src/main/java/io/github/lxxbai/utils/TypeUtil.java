package io.github.lxxbai.utils;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.constants.Constants;
import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.datatypes.Component;
import io.github.lxxbai.datatypes.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * type工具类
 *
 * @author xxbai
 **/
public class TypeUtil {

    private static final Map<String, SolidityType<?>> TYPE_MAP = new ConcurrentHashMap<>();

    static {
        // 初始化基础类型映射
        TYPE_MAP.put(SolidityTypeEnum.BOOL.getType(), new BoolType());
        TYPE_MAP.put(SolidityTypeEnum.ADDRESS.getType(), new AddressType());
        TYPE_MAP.put(SolidityTypeEnum.STRING.getType(), new StringType());
        TYPE_MAP.put(SolidityTypeEnum.BYTES.getType(), new BytesType());
        TYPE_MAP.put(SolidityTypeEnum.FUNCTION.getType(), new FunctionType());
    }

    /**
     * 根据类型名称获取对应的Solidity基础类型对象。
     *
     * @param typeName 不可为空的字符串，表示需要获取的Solidity类型的名称。
     * @return 返回一个SolidityType的实例，代表了对应的Solidity基础类型。
     * @throws RuntimeException 当传入的类型名称无法识别时抛出异常。
     */
    private static SolidityType<?> getBasicType(String typeName) {
        // 检查类型名称是否已经在类型映射中存在，如果存在直接返回对应的SolidityType对象
        if (TYPE_MAP.containsKey(typeName)) {
            return TYPE_MAP.get(typeName);
        }
        SolidityType<?> resultType;
        // 根据类型名称的前缀来决定是创建整数类型、无符号整数类型还是字节类型
        if (typeName.startsWith(SolidityTypeEnum.INT.getType())) {
            resultType = new IntType(typeName);
        } else if (typeName.startsWith(SolidityTypeEnum.UINT.getType())) {
            resultType = new UnsignedIntType(typeName);
        } else if (typeName.startsWith(SolidityTypeEnum.BYTES.getType())) {
            resultType = new Bytes32Type(typeName);
        } else {
            // 如果类型名称没有匹配到任何已知类型，抛出运行时异常
            throw new RuntimeException("Unknown type: " + typeName);
        }
        // 将新创建的SolidityType对象放入类型映射，以便后续可以复用
        TYPE_MAP.put(typeName, resultType);
        return resultType;
    }


    /**
     * 获取与给定 typeName 相对应的 Solidity 类型对象。
     *
     * @param typeName 待解析的 Solidity 类型名称。
     * @return 返回匹配到的 SolidityType 实例。若无法匹配到已知类型，则抛出 RuntimeException。
     */
    public static SolidityType<?> getType(String typeName) {
        // 验证输入
        if (typeName == null || typeName.trim().isEmpty()) {
            throw new IllegalArgumentException("typeName cannot be null or empty");
        }
        typeName = typeName.trim();
        // Handle array types
        if (typeName.endsWith(Constants.RIGHT_SQUARE_BRACKET)) {
            return getArrayType(typeName);
        }
        // Handle array types
        if (typeName.endsWith(Constants.END_BRACKET)) {
            //获取组件类型
            return getTupleType(typeName);
        }
        //下面都是基础类型
        return getBasicType(typeName);
    }


    /**
     * 根据提供的元组类型名称，获取对应的SolidityType对象。
     * (address a,uint256 b)
     *
     * @param tupleTypeName 元组类型的名称，包括类型和可选的参数名，用括号包围。
     * @return SolidityType 对象，代表解析后的元组类型。
     */
    public static SolidityType<?> getTupleType(String tupleTypeName) {
        // 剔除元组类型的前后括号
        tupleTypeName = StrUtil.strip(tupleTypeName, Constants.OPENING_BRACKET, Constants.END_BRACKET);
        List<Component> components = new ArrayList<>();
        // 解析元组类型名称，分成各个组件
        List<String> strParams = SplitUtil.splitComma(tupleTypeName);
        int index = 0;
        for (String strParam : strParams) {
            // 使用空格分割每个组件，以获取类型名和可选的参数名
            List<String> dataList = SplitUtil.splitSpace(strParam);
            Component component = new Component();
            // 设置组件的类型
            component.setType(getType(strParam));
            // 设置组件的名称，如果存在的话
            component.setName(dataList.size() > 1 ? dataList.get(1) : "param_" + index++);
            components.add(component);
        }
        // 创建并返回一个包含所有组件的TupleType对象
        return new TupleType(components);
    }


    /**
     * 获取具体数组类型
     *
     * @param typeName 名称
     * @return 类型
     */
    public static ArrayType<?> getArrayType(String typeName) {
        int idx1 = typeName.lastIndexOf(Constants.LEFT_SQUARE_BRACKET);
        int idx2 = typeName.lastIndexOf(Constants.RIGHT_SQUARE_BRACKET);
        if (idx1 + 1 == idx2) {
            return new DynamicArrayType(typeName);
        } else {
            return new StaticArrayType(typeName);
        }
    }
}
