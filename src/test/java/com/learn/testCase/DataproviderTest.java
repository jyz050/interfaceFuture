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
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.ExcelPojo;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import  static  io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;

public class DataproviderTest extends BaseTest {
    @BeforeTest
    public void setUp(){
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
        String phone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("phone",phone);
        List<ExcelPojo> listDatas = readSpecifyExcelData(2, 0, 1);
        ExcelPojo excelPojo = paramsReplace(listDatas.get(0));
        //Map<String,Object> requestHeaderMap = JSON.parseObject(listDatas.get(0).getRequestHeader(),Map.class);
        Response res = request(excelPojo,"login");
        extractToEnvironment(listDatas.get(0),res);
    }
    @Test(dataProvider = "datas")
    public void testLogin(ExcelPojo excelPojo){
        excelPojo = paramsReplace(excelPojo);
        Response res = request(excelPojo,"login");
        assertResponse(excelPojo,res);
        assertSql(excelPojo);
    }
    @DataProvider
    public Object[] datas() throws Exception{
        List<ExcelPojo> listDatas = readSpecifyExcelData(2, 1);
        return listDatas.toArray();
    }
}
