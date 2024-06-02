package io.github.lxxbai.utils;


import org.bouncycastle.jcajce.provider.digest.Keccak;

/**
 * @author 王大锤
 * @date 2024/4/17 10:20
 **/
public class Sha3Util {

    /**
     * 使用Keccak算法对输入数据进行哈希处理。
     *
     * @param input 待哈希处理的数据。
     * @return 返回经过Keccak哈希算法处理后的数据。
     */
    public static byte[] hashAsKeccak(byte[] input) {
        // 创建一个Keccak-256哈希对象
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        // 对输入数据进行哈希处理，并返回结果
        return digest256.digest(input);
    }
}
