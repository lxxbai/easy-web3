package io.github.lxxbai.datatypes.types;

import io.github.lxxbai.constants.Constants;
import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.datatypes.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 结构体类型
 * <p>
 * 类似:(uint a,address b)
 * ((address a,uint256 b) tu ,address c)
 *
 * @author xxbai
 */
public class TupleType extends SolidityType<Map<String, Object>> {

    /**
     * 组件集合
     */
    private final List<Component> components;

    private final List<SolidityType<?>> types;

    public TupleType(List<Component> components) {
        super(SolidityTypeEnum.TUPLE.getType());
        this.types = components.stream().map(Component::getType).collect(Collectors.toList());
        this.components = components;
    }

    @Override
    public Map<String, Object> decode(byte[] encoded, int origOffset) {
        int offset = origOffset;
        Map<String, Object> tupleMap = new LinkedHashMap<>(types.size());
        for (Component component : components) {
            SolidityType<?> elementType = component.getType();
            Object decode = elementType.decodeResult(encoded, offset);
            tupleMap.put(component.getName(), decode);
            offset += elementType.getFixedSize();
        }
        return tupleMap;
    }

    public List<SolidityType<?>> getTypes() {
        return types;
    }


    /**
     * 组件有动态类型则认为是动态的
     */
    @Override
    public boolean isDynamicType() {
        return components.stream().anyMatch(x -> x.getType().isDynamicType());
    }


    @Override
    public int getFixedSize() {
        return types.stream().mapToInt(SolidityType::getFixedSize).sum();
    }

    @Override
    public String getCanonicalName() {
        return Constants.OPENING_BRACKET + types.stream().map(SolidityType::getCanonicalName).collect(Collectors.joining(",")) + Constants.END_BRACKET;
    }

    @Override
    public boolean isTuple() {
        return true;
    }
}