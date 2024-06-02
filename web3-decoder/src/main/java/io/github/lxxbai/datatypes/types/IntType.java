package io.github.lxxbai.datatypes.types;



import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;

import java.math.BigInteger;

/**
 * Int
 *
 * @author xxbai
 */
public class IntType extends NumericType {

    public IntType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        if (getName().equals(SolidityTypeEnum.INT.getType())) {
            return "int256";
        }
        return super.getCanonicalName();
    }

    @Override
    public BigInteger decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeInt(encoded, offset);
    }
}