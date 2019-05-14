package com.security.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * WireMock 伪造 Result 服务请求
 * @author xuweizhi
 * @since 2019/05/13 1:29
 */
public class MockServer {

    public static void main(String[] args) throws IOException {
        //配置端口
        WireMock.configureFor(9999);
        // 移除所有的配置信息
        WireMock.removeAllMappings();
        mock("/user/1","01");
    }

    /**
     *
     */
    private static void mock(String url,String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+file+".txt");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "utf-8").toArray(), "\n");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url))
                .willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
