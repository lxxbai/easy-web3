package io.github.lxxbai.datatypes;

import io.github.lxxbai.datatypes.types.SolidityType;

/**
 * 组件
 *
 * @author xxbai
 **/
public class Component {

    private String name;

    private SolidityType<?> type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SolidityType<?> getType() {
        return type;
    }

    public void setType(SolidityType<?> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.getCanonicalName(), name);
    }
}
