package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.decoder.TypeDecoder;

/**
 * SolidityType
 *
 * @author xxbai
 */
public abstract class SolidityType<T> {

    protected final static int INT_32_SIZE = 32;

    /**
     * The type name as it was specified in the interface description
     */
    protected String name;

    public SolidityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * The canonical type name (used for the method signature creation)
     * E.g. 'int' - canonical 'int256'
     */
    public String getCanonicalName() {
        return getName();
    }

    /**
     * 解析对应的数据
     *
     * @param encoded encoded
     * @param offset  offset
     * @return 解析后数据
     */
    public abstract T decode(byte[] encoded, int offset);


    /**
     * 解析对应的数据
     *
     * @param encoded encoded
     * @param offset  offset
     * @return 解析后数据
     */
    public T decodeResult(byte[] encoded, int offset) {
        return isDynamicType()
                // 对动态类型进行特殊解码处理
                ? decode(encoded, TypeDecoder.decodeInt(encoded, offset).intValue())
                // 对静态类型直接解码
                : decode(encoded, offset);
    }

    /**
     * 解析topic方法
     * <p>
     * If arrays (including string and bytes) are used as indexed arguments,
     * the Keccak-256 hash of it is stored as topic instead.
     *
     * @param encoded encoded
     * @return 解析后数据
     */
    public Object decodeTopic(byte[] encoded) {
        return isDynamicType() ? TypeDecoder.decodeBytes32(encoded, 0) : decode(encoded);
    }

    /**
     * 默认解析方法
     *
     * @param encoded encoded
     * @return 解析后数据
     */
    public T decode(byte[] encoded) {
        return decode(encoded, 0);
    }

    /**
     * @return fixed size in bytes. For the dynamic types returns IntType.getFixedSize()
     * which is effectively the int offset to dynamic data
     */
    public int getFixedSize() {
        return INT_32_SIZE;
    }

    public boolean isDynamicType() {
        return false;
    }

    public boolean isTuple() {
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}