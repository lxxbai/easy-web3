package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;

/**
 * Bytes类型
 *
 * @author xxbai
 */
public class BytesType extends SolidityType<byte[]> {

    public BytesType() {
        super(SolidityTypeEnum.BYTES.getType());
    }

    @Override
    public byte[] decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeBytes(encoded, offset);
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}