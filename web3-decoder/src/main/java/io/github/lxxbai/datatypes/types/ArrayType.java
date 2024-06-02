package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.utils.TypeUtil;

/**
 * 数组类型
 *
 * @author xxbai
 */
public abstract class ArrayType<E> extends SolidityType<E> {

    /**
     * 元素类型
     */
    protected SolidityType<E> elementType;

    public ArrayType(String name) {
        super(name);
        elementType = (SolidityType<E>) TypeUtil.getType(name.substring(0, name.lastIndexOf("[")));
    }

    public SolidityType<E> getElementType() {
        return elementType;
    }
}
