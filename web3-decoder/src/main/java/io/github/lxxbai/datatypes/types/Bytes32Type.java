package io.github.lxxbai.datatypes.types;


import io.github.lxxbai.decoder.TypeDecoder;

/**
 * Bytes32类型
 *
 * @author other
 * @date 2023-03-26
 */
public class Bytes32Type extends SolidityType<byte[]> {
    public Bytes32Type(String s) {
        super(s);
    }

    @Override
    public byte[] decode(byte[] encoded, int offset) {
        return TypeDecoder.decodeBytes32(encoded, offset);
    }
}