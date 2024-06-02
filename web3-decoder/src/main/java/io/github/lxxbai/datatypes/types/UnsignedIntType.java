package io.github.lxxbai.datatypes.types;

import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;

import java.math.BigInteger;

/**
 * 未知类型
 *
 * @author xxbai
 */
public class UnsignedIntType extends NumericType {
    public UnsignedIntType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        if (getName().equals(SolidityTypeEnum.UINT.getType())) {
            return "uint256";
        }
        return super.getCanonicalName();
    }

    @Override
    public BigInteger decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeUint(encoded, offset);
    }
}