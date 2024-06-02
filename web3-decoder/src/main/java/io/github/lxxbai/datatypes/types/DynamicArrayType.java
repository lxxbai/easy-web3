package io.github.lxxbai.datatypes.types;



import io.github.lxxbai.decoder.TypeDecoder;

import java.util.List;

/**
 * 动态数组类型
 *
 * @author xxbai
 */
public class DynamicArrayType extends ArrayType<List> {

    public DynamicArrayType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        return elementType.getCanonicalName() + "[]";
    }

    @Override
    public List decode(byte[] encoded, int origOffset) {
        return TypeDecoder.decodeDynamicArray(encoded, origOffset, elementType);
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}