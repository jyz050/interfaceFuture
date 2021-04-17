package com.learn.testCase;

import com.alibaba.fastjson.JSONObject;
import com.learn.base.BaseTest;
import com.learn.datas.Constants;
import com.learn.datas.Environment;
import com.learn.utils.PhoneRandomUtil;
import com.learn.utils.SqlUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.ExcelPojo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;

public class InvestLoadTest extends BaseTest {
    @BeforeTest
    public void setUp(){
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
        String borrowerPhone = PhoneRandomUtil.getUnRegisterPhone();
        String adminPhone = PhoneRandomUtil.getUnRegisterPhone();
        String investPhone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("borrower_phone",borrowerPhone);
        Environment.envData.put("admin_phone",adminPhone);
        Environment.envData.put("invest_phone",investPhone);
        //读取从一行到第九行的数据
        List<ExcelPojo> listData = readSpecifyExcelData(4,0,9);
        for(int i=0;i<listData.size();i++){
            ExcelPojo excelPojo = listData.get(i);
            excelPojo = paramsReplace(excelPojo);
            Response res = request(excelPojo,"invest");
            if(excelPojo.getExtractData() != null){
                extractToEnvironment(excelPojo,res);
            }
        }

    }
    @Test(dataProvider = "getInvestData")
    public void testInvest(ExcelPojo excelPojo){
        //参数替换
        excelPojo = paramsReplace(excelPojo);
        //发送请求并获取响应结果
        Response res = request(excelPojo,"invest");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSql(excelPojo);
    }
    @DataProvider
    public Object[] getInvestData(){
        List<ExcelPojo> list = readSpecifyExcelData(4,9);
        return list.toArray();
    }
    @AfterTest
    public void tearDown(){

    }

}
