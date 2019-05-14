package com.security.browser.support;

/**
 * @author xuweizhi
 * @date 2019/05/15 0:54
 */
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
