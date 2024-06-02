package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;

/**
 * Address
 *
 * @author xxbai
 */
public class AddressType extends SolidityType<String> {
    public AddressType() {
        super(SolidityTypeEnum.ADDRESS.getType());
    }

    @Override
    public String decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeAddress(encoded, offset);
    }
}