package org.sang.bean;

import java.io.Serializable;

/**
 * Created by sang on 2017/12/30.
 */
public class MenuMeta implements Serializable {

    private static final long serialVersionUID = -792904470485954010L;

    private boolean keepAlive;
    private boolean requireAuth;

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
