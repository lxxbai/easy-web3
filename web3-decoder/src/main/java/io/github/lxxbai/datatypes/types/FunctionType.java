package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.constants.SolidityTypeEnum;

/**
 * 方法类型
 *
 * @author xxbai
 */
public class FunctionType extends Bytes32Type {
    public FunctionType() {
        super(SolidityTypeEnum.FUNCTION.getType());
    }
}