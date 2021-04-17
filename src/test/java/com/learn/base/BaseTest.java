package com.learn.base;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.learn.datas.Constants;
import com.learn.datas.Environment;
import com.learn.utils.SqlUtils;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.ExcelPojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseTest {
    //对get post patch等请求方法发送请求
    public Response request(ExcelPojo excelPojo,String ApiTestName){
        String logFilePath;
        if(Constants.LOG_TO_FILE) {
            File file = new File(System.getProperty("user.dir") + "\\log\\"+ApiTestName);
            if (!file.exists()) {
                file.mkdirs();
            }
            logFilePath = System.getProperty("user.dir") + "\\log\\"+excelPojo.getApiName()+"\\test"+ excelPojo.getCassId() + ".log";
            PrintStream fileOutPutStream = null;
            try {
                fileOutPutStream = new PrintStream(new File(logFilePath));
                RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String url = excelPojo.getUrl();
        String method = excelPojo.getRequestMethod();
        String headers = excelPojo.getRequestHeader();
        String params = excelPojo.getParams();
        Map<String,Object> headerMap = JSONObject.parseObject(headers,Map.class);
        Response res = null;
        if("get".equalsIgnoreCase(method)){
            res = given().headers(headerMap).when().get(url).then().log().all().extract().response();
        }else if("post".equalsIgnoreCase(method)){
            res = given().headers(headerMap).body(params).when().post(url).then().log().all().extract().response();
        }else if("patch".equalsIgnoreCase(method)){
            res = given().headers(headerMap).body(params).when().patch(url).then().log().all().extract().response();
        }
        if(Constants.LOG_TO_FILE) {
            try {
                Allure.addAttachment("接口响应信息是：", new FileInputStream(logFilePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    //响应断言
    public void assertResponse(ExcelPojo excelPojo,Response res){
        if(excelPojo.getExpectResult() != null) {
            Map<String, Object> expectedMap = JSONObject.parseObject(excelPojo.getExpectResult(), Map.class);
            for (String expectExpression : expectedMap.keySet()) {
                //取出期望结果
                Object expectValue = expectedMap.get(expectExpression);
                //取出实际结果
                Object actualValue = res.jsonPath().get(expectExpression);
                //拿实际结果和期望结果进行比较
                Assert.assertEquals(actualValue, expectValue);
            }
        }
    }
    //数据库断言
    public void assertSql(ExcelPojo excelPojo){
        String dbAssert = excelPojo.getDbAssert();
        if(dbAssert != null){
            Map<String,Object> map = JSONObject.parseObject(dbAssert, Map.class);
            Set<String> keys = map.keySet();
            for (String key:keys){
                Object expectValue = map.get(key);
                if(expectValue instanceof BigDecimal){
                    Object actualValue = SqlUtils.scarlarHandler(key);
                    Assert.assertEquals(actualValue,expectValue);
                }else if (expectValue instanceof Integer){
                    Object actualValue = SqlUtils.scarlarHandler(key);
                    Long expectValue2 = ((Integer)expectValue).longValue();
                    Assert.assertEquals(actualValue,expectValue2);
                }
            }
        }
    }
    //读取指定表格的数据
    public List<ExcelPojo> readExcelDate(int sheetNum){
        File file = new File(Constants.EXCEL_FILE_PATH);
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum-1);
        return ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
    }
    //读取指定行的数据
    public List<ExcelPojo> readSpecifyExcelData(int sheetNum,int startRow,int readRow){
        File file = new File(Constants.EXCEL_FILE_PATH);
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum-1);
        importParams.setStartRows(startRow);
        importParams.setReadRows(readRow);
        return ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
    }
    //读取指定行开始到最后一条的数据
    public List<ExcelPojo> readSpecifyExcelData(int sheetNum,int startRow){
        File file = new File(Constants.EXCEL_FILE_PATH);
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum-1);
        importParams.setStartRows(startRow);
        return  ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
    }
    //将接口返回值提取到环境变量中
    public void extractToEnvironment(ExcelPojo excelPojo,Response res){
        Map<String,Object> extractMap = JSONObject.parseObject(excelPojo.getExtractData(),Map.class);
        //循环遍历extractMa
        for(String key:extractMap.keySet()){
            Object path = extractMap.get(key);
            Object value = res.jsonPath().get(path.toString());
            Environment.envData.put(key,value);
        }
    }
    //正则替换
    //orgStr 表示原始字符串 replaceStr 表示替换的字符串
    public  static String regexReplace(String orgStr){
        if(orgStr != null) {
            //正则表达式配置
            Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
            Matcher matcher = pattern.matcher(orgStr);
            String result = orgStr;
            while (matcher.find()) {
                //group(0)表示获取整个配置的内容
                String outerStr = matcher.group(0);
                //group(1)表示获取{{}}包裹的内容
                String innerStr = matcher.group(1);
                Object replaceStr = Environment.envData.get(innerStr);
                result = result.replace(outerStr, replaceStr + "");
            }
            return result;
        }
        return orgStr;
    }
    //参数化替换
    public ExcelPojo paramsReplace(ExcelPojo excelPojo){
        String inputParams = regexReplace(excelPojo.getParams());
        excelPojo.setParams(inputParams);
        String requestHeader = regexReplace(excelPojo.getRequestHeader());
        excelPojo.setRequestHeader(requestHeader);
        String url = regexReplace(excelPojo.getUrl());
        excelPojo.setUrl(url);
        String expect = regexReplace(excelPojo.getExpectResult());
        excelPojo.setExpectResult(expect);
        String dbAssert = regexReplace(excelPojo.getDbAssert());
        excelPojo.setDbAssert(dbAssert);
        return excelPojo;
    }
}
