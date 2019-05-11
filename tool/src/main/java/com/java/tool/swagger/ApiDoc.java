package com.java.tool.swagger;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试API生成接口文档
 *
 * @author xuweizhi
 * @date 2019/03/26 19:42
 */
@RestController
public class ApiDoc {

    /**
     * @apiDefine MyError 定义变量
     * @apiError This not Chinese
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "error": "UserNotFound"
     * }
     */
    /**
     * @apiDefine userApiStr 用户接口文档
     */

    // @apiIgnore 如果不想生成这个方法，祛除参数即可
    // @apiSampleRequest http://www.baidu.com 重置请求地址

    /**
     * @api {get} /user/:id 方法/url
     * @apiPermission none
     * @apiName GetUser
     * @apiGroup userApiStr
     * @apiDescription 方法说明
     * @apiHeader {String} Authorization 用户授权token
     * @apiHeader {String} firm 厂商编码
     * @apiHeaderExample {json} Header-Example:
     * {
     * "Authorization": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjM2NzgsImF1ZGllbmNlIjoid2ViIiwib3BlbkFJZCI6MTM2NywiY3JlYXRlZCI6MTUzMzg3OTM2ODA0Nywicm9sZXMiOiJVU0VSIiwiZXhwIjoxNTM0NDg0MTY4fQ.Gl5L-NpuwhjuPXFuhPax8ak5c64skjDTCBC64N_QdKQ2VT-zZeceuzXB9TqaYJuhkwNYEhrV3pUx1zhMWG7Org",
     * "firm": "cnE="
     * }
     * @apiParam {Number} id Users unique ID.
     * @apiParam {String} [firstName]  Optional Firstname of the User.
     * @apiParam {String} lastName     Mandatory Lastname.
     * @apiParam {String} country="DE" Mandatory with default value "DE".
     * @apiParam {Number} [age=18]     Optional Age with default 18.
     * @apiParam (Login) {String} pass Only logged in users can post this.
     * In generated documentation a separate
     * "Login" Block will be generated.
     * @apiSuccess {String} id FirstName of the User.
     * @apiSuccess {String} firstName FirstName of the User.
     * @apiSuccess {String} lastName  LastnNme of the User.
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "id":"id",
     * "firstName": "John",
     * "lastName": "Doe"
     * }
     * @apiUse MyError
     */
    @RequestMapping("/user/{id}")
    @CrossOrigin
    public String getUser(@PathVariable("id") String id, String firstName, String lastName) {
        User user = new User();
        Gson gson = new Gson();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return gson.toJson(user);
    }
    //apidoc -i url/common/ -o url

    /**
     * @api {POST} /register 注册用户
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription 用于注册用户
     * @apiParam {String} account 用户账户名
     * @apiParam {String} password 密码
     * @apiParam {String} mobile 手机号
     * @apiParam {int} vip = 0  是否注册Vip身份 0 普通用户 1 Vip用户
     * @apiParam {String} [recommend] 邀请码
     * @apiParamExample {json} 请求样例：
     *                ?account=sodlinken&password=11223344&mobile=13739554137&vip=0&recommend=
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccess (200) {int} code 0 代表无错误 1代表有错误
     * @apiSuccessExample {json} 返回样例:
     *                {"code":"0","msg":"注册成功"}
     */

    /**
     * @api {POST} /login 用户登录
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription 用于用户登录
     * @apiParam {String} userName 用户名
     * @apiParam {String} password 密码
     * @apiParamExample {json} 请求样例：
     *                ?userName=张三&password=11223344
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccess (200) {String} code 0 代表无错误 1代表有错误
     * @apiSuccess (200) {String} user 用户信息
     * @apiSuccess (200) {String} userId 用户id
     * @apiSuccessExample {json} 返回样例:
     *                {"code":"0","msg":"登录成功","userId":"fe6386d550bd434b8cd994b58c3f8075"}
     */

    /**
     * @api {GET} /users/:id 获取用户信息
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription 获取用户信息
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccess (200) {int} code 0 代表无错误 1代表有错误
     * @apiSuccess (200) {String} name 真实姓名
     * @apiSuccess (200) {String} mobile 手机号
     * @apiSuccess (200) {String} birthday 生日
     * @apiSuccess (200) {String} email 邮箱
     * @apiSuccess (200) {String} summary 简介
     * @apiSuccess (200) {String} recommendCode 我的推荐码
     * @apiSuccess (200) {String} idCardNo 身份证号
     * @apiSuccess (200) {String} memberState 会员状态 0普通用户 1VIP 2账户冻结
     * @apiSuccess (200) {String} address 家庭住址
     * @apiSuccess (200) {String} money 账户现金
     * @apiSuccessExample {json} 返回样例:
     * {
     *   "code": 0,
     *   "msg": "",
     *   "name": "真实姓名",
     *   "mobile": 15808544477,
     *   "birthday": "1990-03-05",
     *   "email": "slocn@gamil.com",
     *   "summary": "简介",
     *   "recommendCode": "我的推荐码",
     *   "idCardNo": "身份证号",
     *   "memberState": 1,
     *   "address": "家庭住址",
     *   "money": "30.65"
     * }
     */


    /**
     * @api {POST} /users/:id 修改(完善)用户信息
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription 修改(完善)用户信息
     * @apiParam (200) {String} [name] 真实姓名
     * @apiParam (200) {String} [mobile] 手机号
     * @apiParam (200) {String} [birthday] 生日
     * @apiParam (200) {String} [email] 邮箱
     * @apiParam (200) {String} [summary] 简介
     * @apiParam (200) {String} [idCardNo] 身份证号
     * @apiParam (200) {String} [address] 家庭住址
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccess (200) {int} code 0 代表无错误 1代表有错误
     * @apiSuccessExample {json} 返回样例:
     *                {"code":"0","msg":"修改成功"}
     */


    //(group)（可选）：分组
    //{type}（可选）：类型，例如：{Boolean}, {Number}, {String}, {Object}, {String[]}
    //{type{size}}（可选）：类型限定长度，例如：{string{..5}} 限定最大长度为5个字符
    //{string{2..5}} 限定最短2个字符，最长5个字符
    //{number{100-999}} 限定数字在100-999之间
    //{type=allowedValues}（可选）：类型限定值，例如：{string=”small”}限定只允许传递small字符，
    //{string=”small”,”huge”} 限定只允许传递small或huge字符，
    //{number=1,2,3,99} 限定只允许传1，2，3，99这四个数字
    //field：变量名
    //[field]：定义变量，并标记变量是可选项
    //=defaultValue（可选）：默认值
    //    description（optional）：变量说明

}




