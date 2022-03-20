package top.lingkang.sessioncore.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/26
 * 自定义id生成，默认生成类 FinalIdGenerate
 */
public interface IdGenerate {
    String generateId(HttpServletRequest request);
}
