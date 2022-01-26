package top.lingkang.base.impl;

import top.lingkang.base.IdGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalIdGenerate implements IdGenerate {
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
