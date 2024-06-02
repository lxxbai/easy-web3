package io.github.lxxbai.datatypes;

import cn.hutool.core.util.StrUtil;

import io.github.lxxbai.decoder.TypeDecoder;
import io.github.lxxbai.utils.StrUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * abi 事件
 *
 * @author shupeng_liao
 */
public class Event extends Entry {

    public Event(String expression) {
        super(expression);
    }

    public List<Object> decode(String rowData, List<String> topics) {
        Map<String, Object> objectMap = decodeToMap(rowData, topics);
        return TypeDecoder.mapValueToList(objectMap);
    }


    /**
     * 将给定的字符串数据解码为一个映射表，其中键是参数的名称，值是解码后的数据。
     *
     * @param rowData 待解码的字符串数据。
     * @param topics  包含签名和其他主题数据的字符串列表。
     * @return 一个映射表，键是参数名称，值是解码后的数据对象。
     * @throws RuntimeException 如果签名验证失败。
     */
    public Map<String, Object> decodeToMap(String rowData, List<String> topics) {
        // 初始化结果映射表，其大小与输入参数数量相等
        Map<String, Object> resultMap = new LinkedHashMap<>(getInputs().size());
        Iterator<String> iterator = topics.iterator();

        // 提取并验证签名
        String signature = iterator.next();
        // 需要验签时进行验签
        if (getValidSignature() && !StrUtil.equals(signature, getSignature())) {
            throw new RuntimeException("invalid signature");
        }

        byte[] encoded = StrUtils.strToBytes(rowData);
        int offset = 0;
        // 遍历所有输入参数，进行解码
        for (int i = 0; i < super.getInputs().size(); i++) {
            Param param = getInputs().get(i);
            // 如果参数被标记为indexed，则从topics中解码对应的主题数据
            if (param.getIndexed()) {
                resultMap.put(param.getName(), param.decodeTopic(StrUtils.strToBytes(iterator.next())));
            } else {
                // 否则，从encoded数据中解码，并更新offset
                resultMap.put(param.getName(), param.decode(encoded, offset));
                offset += param.getType().getFixedSize();
            }
        }
        return resultMap;
    }


    @Override
    public String toString() {
        return String.format("event %s(%s);", getName(), StrUtil.join(",", getInputs()));
    }
}