package io.github.lxxbai.datatypes.types;


import java.math.BigInteger;

/**
 * NumericType
 *
 * @author xxbai
 */
public abstract class NumericType extends SolidityType<BigInteger> {

    public NumericType(String name) {
        super(name);
    }
}

