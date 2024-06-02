package io.github.lxxbai.constants;

/**
 * @author xxbai
 **/
public enum SolidityTypeEnum {

    INT("int"),
    BOOL("bool"),
    UINT("uint"),
    ADDRESS("address"),
    STRING("string"),
    BYTES("bytes"),
    FUNCTION("function"),
    TUPLE("tuple");

    private final String type;

    SolidityTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean equals(String type) {
        return this.type.equals(type);
    }
}
