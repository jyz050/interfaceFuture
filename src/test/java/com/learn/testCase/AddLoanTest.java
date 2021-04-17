package com.learn.testCase;

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

import java.util.List;
import java.util.Map;

import static io.restassured.config.JsonConfig.jsonConfig;

public class AddLoanTest extends BaseTest {
    @BeforeTest
    public void setUp(){
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
        String phone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("phone",phone);
        String adminPhone = PhoneRandomUtil.getUnRegisterPhone();
        Environment.envData.put("admin_phone",adminPhone);
        List<ExcelPojo> listDatas = readSpecifyExcelData(5,0,4);
        for(int i=0;i<listDatas.size();i++){
            ExcelPojo excelPojo = listDatas.get(i);
            excelPojo = paramsReplace(excelPojo);
            Response res = request(excelPojo,"addLoan");
            if(excelPojo.getExtractData() != null){
                extractToEnvironment(excelPojo,res);
            }
        }
    }
    @Test(dataProvider = "getAddLoanDatas")
    public void addLoanTest(ExcelPojo excelPojo){
        excelPojo = paramsReplace(excelPojo);
        Response res = request(excelPojo,"addLoan");
        assertResponse(excelPojo,res);
        assertSql(excelPojo);
    }

    @DataProvider
    public Object[] getAddLoanDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData(5,4);
        return listDatas.toArray();
    }
}
