package top.lingkang.sessioncore.wrapper;

import top.lingkang.sessioncore.base.IdGenerate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/27
 */
public class DefaultGenerateSession implements FinalGenerateSession {
    @Override
    public FinalSession generateSession(HttpServletRequest request, IdGenerate idGenerate) {
        return new FinalSession(request.getServletContext(), idGenerate.generateId());
    }
}
