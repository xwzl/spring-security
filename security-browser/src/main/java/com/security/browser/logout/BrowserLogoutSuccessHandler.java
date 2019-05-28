package com.security.browser.logout;

import com.security.core.support.SimpleResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出成功处理逻辑
 *
 * @author xuweizhi
 * @date 2019/05/28 22:30
 */
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BrowserLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("退出成功！");

        // 默认为空返回 json 数据
        if (StringUtils.isEmpty(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功！")));
            // 不为空返回退出页面
        } else {
            response.sendRedirect(signOutUrl);
        }

    }

}
