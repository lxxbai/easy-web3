package io.github.lxxbai.datatypes;


import io.github.lxxbai.datatypes.types.SolidityType;
import io.github.lxxbai.utils.TypeUtil;

/**
 * 参数化
 *
 * @author xxbai
 */
public class Param {
    private Boolean indexed;
    private String name;
    private SolidityType<?> type;


    public Param(Boolean indexed, String name, String typeName) {
        this.indexed = indexed;
        this.name = name;
        this.type = TypeUtil.getType(typeName);
    }

    public Object decode(byte[] encoded, int offset) {
        return type.decodeResult(encoded, offset);
    }

    public Object decodeTopic(byte[] encoded) {
        return type.decodeTopic(encoded);
    }

    public Boolean getIndexed() {
        return indexed;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }

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
        return String.format("%s%s%s", type.getCanonicalName(), (indexed != null && indexed) ? " indexed " : " ", name);
    }
}
