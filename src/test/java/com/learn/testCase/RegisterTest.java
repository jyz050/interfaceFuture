package com.learn.testCase;

import com.alibaba.fastjson.JSONObject;
import com.learn.base.BaseTest;
import com.learn.datas.Constants;
import com.learn.datas.Environment;
import com.learn.utils.PhoneRandomUtil;
import com.learn.utils.SqlUtils;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.ExcelPojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;

public class RegisterTest extends BaseTest {
    @BeforeTest
    public void setUp(){
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
        String phone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("phone",phone);
    }
    @Test(dataProvider = "datas")
    public void testRegister(ExcelPojo excelPojo) throws Exception{
        //参数替换
        excelPojo = paramsReplace(excelPojo);
        //发送请求并获取响应结果
        Response res = request(excelPojo,"register");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSql(excelPojo);
    }
    @DataProvider
    public Object[] datas() throws Exception{
        List<ExcelPojo> listDatas = readSpecifyExcelData(1, 0);
        return listDatas.toArray();
    }
}
