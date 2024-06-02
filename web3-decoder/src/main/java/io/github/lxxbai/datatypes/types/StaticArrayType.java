package io.github.lxxbai.datatypes.types;



import io.github.lxxbai.decoder.TypeDecoder;

import java.util.List;

/**
 * 静态数组类型
 *
 * @author xxbai
 */
public class StaticArrayType extends ArrayType<List> {

    private final int size;

    public StaticArrayType(String name) {
        super(name);
        int idx1 = name.lastIndexOf("[");
        int idx2 = name.lastIndexOf("]");
        String dim = name.substring(idx1 + 1, idx2);
        size = Integer.parseInt(dim);
    }

    @Override
    public String getCanonicalName() {
        return getElementType().getCanonicalName() + "[" + size + "]";
    }


    @Override
    public List decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeStaticArray(encoded, offset, elementType, size);
    }

    @Override
    public int getFixedSize() {
        if (isDynamicType()) {
            return INT_32_SIZE;
        } else {
            return elementType.getFixedSize() * size;
        }
    }

    @Override
    public boolean isDynamicType() {
        return getElementType().isDynamicType() && size > 0;
    }
}