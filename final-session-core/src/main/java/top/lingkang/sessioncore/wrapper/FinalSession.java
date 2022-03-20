package top.lingkang.sessioncore.wrapper;

import top.lingkang.sessioncore.utils.Assert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.*;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalSession implements HttpSession, Serializable {
    private String id;
    private final long creationTime;
    private long lastAccessedTime;
    private transient final ServletContext servletContext;
    private final Map<String, Object> attributes;
    private boolean existsUpdate;

    public FinalSession(ServletContext servletContext, String id) {
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = System.currentTimeMillis();
        this.attributes = new LinkedHashMap();
        this.servletContext = servletContext;
        this.id = id;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public String getId() {
        return this.id;
    }

    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setMaxInactiveInterval(int interval) {
        existsUpdate = true;
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("getSessionContext");
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Object getValue(String name) {
        return this.getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(new LinkedHashSet(this.attributes.keySet()));
    }

    public String[] getValueNames() {
        Collection<String> collection = attributes.keySet();
        if (collection == null)
            return null;
        return collection.toArray(new String[0]);
    }

    public void setAttribute(String name, Object value) {
        Assert.notNull(name, "Attribute name must not be null");
        attributes.put(name, value);
        /*if (value != null) {
            Object oldValue = this.attributes.put(name, value);
            if (value != oldValue) {
                if (oldValue instanceof HttpSessionBindingListener) {
                    ((HttpSessionBindingListener) oldValue).valueUnbound(new HttpSessionBindingEvent(this, name, oldValue));
                }

                if (value instanceof HttpSessionBindingListener) {
                    ((HttpSessionBindingListener) value).valueBound(new HttpSessionBindingEvent(this, name, value));
                }
            }
        } else {
            this.removeAttribute(name);
        }*/
        existsUpdate = true;
    }

    public void putValue(String name, Object value) {
        this.setAttribute(name, value);
        existsUpdate = true;
    }

    public void removeAttribute(String name) {
        Assert.notNull(name, "Attribute name must not be null");
        Object value = this.attributes.remove(name);
        if (value instanceof HttpSessionBindingListener) {
            ((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
        }
        existsUpdate = true;
    }

    public void removeValue(String name) {
        existsUpdate = true;
    }

    public void clearAttributes() {
        Iterator it = this.attributes.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            it.remove();
            if (value instanceof HttpSessionBindingListener) {
                ((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
            }
        }
        existsUpdate = true;
    }

    public void invalidate() {
    }


    public boolean isNew() {
        return false;
    }

    public boolean isExistsUpdate() {
        return existsUpdate;
    }

    public void setExistsUpdate(boolean existsUpdate) {
        this.existsUpdate = existsUpdate;
    }

    public void updateAccessTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }



    /* get and set 用于继承扩展 */

    protected void setId(String id) {
        this.id = id;
    }

    protected void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    protected Map<String, Object> getAttributes() {
        return attributes;
    }
}
