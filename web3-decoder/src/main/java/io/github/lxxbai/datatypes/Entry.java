package io.github.lxxbai.datatypes;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.constants.Constants;
import io.github.lxxbai.parser.ParamParser;
import io.github.lxxbai.utils.Sha3Util;
import io.github.lxxbai.utils.SplitUtil;
import io.github.lxxbai.utils.StrUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础结构体
 *
 * @author xxbai
 */
public abstract class Entry {

    /**
     * 表达式
     */
    private final String expression;

    /**
     * 名称
     */
    private final String name;

    /**
     * 入参参数
     */
    private final List<Param> inputs;

    /**
     * 是否是匿名的
     */
    private final Boolean anonymous;

    /**
     * 是否验证签名
     */
    private final Boolean validSignature;

    private String signature;


    public Entry(String expression) {
        //默认不验证签名
        this(expression, false);
    }

    public Entry(String expression, Boolean validSignature) {
        //TODO 需要哪个大佬提供表达式正则校验
        this.expression = expression;
        this.validSignature = validSignature;
        //判断是否是匿名事件
        this.anonymous = StrUtil.endWith(expression, Constants.ANONYMOUS);
        //获取名称,第一个'('前的数据
        int leftBracket = expression.indexOf('(');
        //删除最后一个')'和后面的数据
        int rightBracket = expression.lastIndexOf(')');
        //没找到
        if (leftBracket == -1) {
            throw new RuntimeException("The expression is incorrect and does not contain'('");
        }
        if (rightBracket == -1) {
            throw new RuntimeException("The expression is incorrect and does not contain ')'");
        }
        //名称
        this.name = StrUtil.sub(expression, 0, leftBracket).trim();
        this.inputs = new ArrayList<>();
        if (StrUtil.isBlank(expression)) {
            throw new RuntimeException("Expression is error !!!");
        }
        //按照逗号分割,括号内的不分割
        List<String> paramsList = SplitUtil.splitComma(expression.substring(leftBracket + 1, rightBracket));
        for (int i = 0; i < paramsList.size(); i++) {
            String strParam = paramsList.get(i);
            //解析param
            Param param = ParamParser.parse(strParam, i);
            inputs.add(param);
        }
        this.signature = getSignature();
    }

    public String getExpression() {
        return expression;
    }

    public String getName() {
        return name;
    }

    public List<Param> getInputs() {
        return inputs;
    }

    public String formatSignature() {
        StringBuilder paramsTypes = new StringBuilder();
        if (inputs != null) {
            for (Param param : inputs) {
                String type = param.getType().getCanonicalName();
                paramsTypes.append(type).append(",");
            }
        }
        return String.format("%s(%s)", name, StrUtil.strip(paramsTypes.toString(), ","));
    }

    public byte[] encodeSignature() {
        return Sha3Util.hashAsKeccak(formatSignature().getBytes(StandardCharsets.UTF_8));
    }


    public Boolean getValidSignature() {
        return validSignature;
    }

    public String getSignature() {
        if (StrUtil.isBlank(signature)) {
            this.signature = StrUtils.toHexString(encodeSignature());
        }
        return signature;
    }
}