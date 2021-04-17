package com.learn.testCase;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learn.base.BaseTest;
import com.learn.datas.Constants;
import com.learn.datas.Environment;
import com.learn.utils.PhoneRandomUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.ExcelPojo;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;

public class RechargeTest extends BaseTest {
    int memberId;
    String token;
    //注册的前置操作，发起注册和登录的请求
    @BeforeTest
    public void setUp(){
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
        String phone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("phone",phone);
        List<ExcelPojo> listDatas = readSpecifyExcelData(3,0,2);
        for(int i=0;i<listDatas.size();i++){
            ExcelPojo excelPojo = listDatas.get(i);
            excelPojo = paramsReplace(excelPojo);
            Response res = request(excelPojo,"recharge");
            if(excelPojo.getExtractData() != null){
                extractToEnvironment(excelPojo,res);
            }
        }
    }
    @Test(dataProvider = "getRechargeDatas")
    public void testRecharge(ExcelPojo excelPojo){
        excelPojo = paramsReplace(excelPojo);
        Response res = request(excelPojo,"recharge");
        assertResponse(excelPojo,res);
        assertSql(excelPojo);
    }
    @DataProvider
    public Object[] getRechargeDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData(3,2);
        return listDatas.toArray();
    }

}
