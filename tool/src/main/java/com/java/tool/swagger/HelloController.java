package com.java.tool.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuweizhi
 * @date 2019/03/26 16:14
 */
@RestController
public class HelloController {

    @Autowired
    private BeanTest beanTest;

    @Autowired
    private People people1;

    /**
     * {@value}三目运算符
     */
    @RequestMapping("/index2")
    public String index(@Value("${name.three!='杨过'?'黄蓉':'小龙女'}") String nameThree) {

        return beanTest.getName1() + ":" + beanTest.getAge() + ":" + people1 + nameThree;
    }

    //{
    //    "name": "appleFarm", //文档项目名
    //        "title": "appleFarmAPI", //html标题
    //        "description":"appleFarmAPI接口文档", //文档描述
    //        "url" : "https: //xxx.com",//公共接口地址
    //        "version": "0.1.0" //文档版本
    //}
    @RequestMapping("/index")
    @ResponseBody
    @CrossOrigin
    public String index(@RequestParam("userName") String userName, @RequestParam("userAge") int userAge) {
        return "index userName is " + userName + "userAge " + userAge;
    }

    /**
     * @api {get} /index1
     * @apiDescription 这只是一个测试的接口描述
     * @apiName index
     * @apiParam {String} userName 用户的姓名
     * @apiParam {Number} userAge 用户的年龄
     * @apiParamExample {json} Request-Example:
     * {
     * "userName":"caojing",
     * "userAge":12
     * }
     * @apiGroup index
     * @apiError userNotFound  The <code>id</code>
     * @apiSampleRequest /index1
     */
    @RequestMapping("/index1")
    @CrossOrigin
    public String index2(@RequestParam("userName") String userName, @RequestParam("userAge") int userAge) {
        return "index userName is " + userName + "userAge " + userAge;
    }

    /**
     *
     * @apiDefine RkNotFoundException
     *
     * @apiError RkNotFoundException 找不到相关数据
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "error": {
     *           "code": 404,
     *           "msg": "",
     *           "path" ""
     *       }
     *     }
     *
     */

    /**
     *
     * @api {get} /v3.1/ues/:sn/rt-info 获取设备上报实时信息
     * @apiVersion 3.1.0
     * @apiName GetUeRealTimeInfo
     * @apiGroup UE
     *
     * @apiHeader {String} Authorization 用户授权token
     * @apiHeader {String} firm 厂商编码
     * @apiHeaderExample {json} Header-Example:
     *     {
     *       "Authorization": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjM2NzgsImF1ZGllbmNlIjoid2ViIiwib3BlbkFJZCI6MTM2NywiY3JlYXRlZCI6MTUzMzg3OTM2ODA0Nywicm9sZXMiOiJVU0VSIiwiZXhwIjoxNTM0NDg0MTY4fQ.Gl5L-NpuwhjuPXFuhPax8ak5c64skjDTCBC64N_QdKQ2VT-zZeceuzXB9TqaYJuhkwNYEhrV3pUx1zhMWG7Org",
     *       "firm": "cnE="
     *     }
     *
     * @apiParam {String} sn 设备序列号
     *
     * @apiSuccess {String} sn 设备序列号
     * @apiSuccess {Number} status 设备状态
     * @apiSuccess {Number} soc 电池电量百分比
     * @apiSuccess {Number} voltage 电池电压
     * @apiSuccess {Number} current 电池电流
     * @apiSuccess {Number} temperature 电池温度
     * @apiSuccess {String} reportTime 上报时间(yyyy-MM-dd HH:mm:ss)
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "sn": "P000000000",
     *       "status": 0,
     *       "soc": 80,
     *       "voltage": 60.0,
     *       "current": 10.0,
     *       "temperature": null,
     *       "reportTime": "2018-08-13 18:11:00"
     *     }
     *
     * @apiUse RkNotFoundException
     *
     */
    @RequestMapping(value = "/{sn}/rt-info", method = RequestMethod.GET)
   public void test(){

    }


    @RequestMapping("/nginx")
    public String nginx(){
        return "1111";
    }



}
