package io.github.lxxbai.datatypes.types;

import io.github.lxxbai.constants.SolidityTypeEnum;
import io.github.lxxbai.decoder.TypeDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 字符串类型
 *
 * @author other
 * @date 2023-03-26
 */
public class StringType extends SolidityType<String> {
    public StringType() {
        super(SolidityTypeEnum.STRING.getType());
    }

    @Override
    public String decode(byte[] encoded, int offset) {
        return new String(TypeDecoder.decodeBytes(encoded, offset), StandardCharsets.UTF_8);
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}