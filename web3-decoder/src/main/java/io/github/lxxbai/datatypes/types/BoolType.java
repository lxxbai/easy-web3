package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;

/**
 * bool类型
 *
 * @author other
 * @date 2023-03-26
 */
public class BoolType extends SolidityType<Boolean> {

    public BoolType() {
        super(SolidityTypeEnum.BOOL.getType());
    }

    @Override
    public Boolean decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeBool(encoded, offset);
    }
}