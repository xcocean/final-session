package top.lingkang.sessioncore.wrapper;

import top.lingkang.sessioncore.base.IdGenerate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/27
 */
public interface FinalGenerateSession {
    FinalSession generateSession(HttpServletRequest request, IdGenerate idGenerate);
}
