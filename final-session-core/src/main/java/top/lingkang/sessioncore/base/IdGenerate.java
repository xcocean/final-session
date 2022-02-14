package top.lingkang.sessioncore.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public interface IdGenerate {
    String generateId(HttpServletRequest request);
}
