package top.lingkang.sessioncore.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpSession session;

    public FinalServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session){
        this.session=session;
    }
}
