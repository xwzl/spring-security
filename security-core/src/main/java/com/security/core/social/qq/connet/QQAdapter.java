package com.security.core.social.qq.connet;

import com.security.core.social.qq.api.QQ;
import com.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Api 适配器，适配类型 qq
 *
 * @author xuweizhi
 * @date 2019/05/15 15:08
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试服务是否通畅,需要测试
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 适配用户信息
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        // 用户姓名
        values.setDisplayName(userInfo.getNickname());
        // 头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页
        values.setProfileUrl(null);
        // openid
        values.setProviderUserId(userInfo.getOpenId());

    }

    /**
     * 解绑是采用这个
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 时间线
     */
    @Override
    public void updateStatus(QQ api, String message) {

    }
}
