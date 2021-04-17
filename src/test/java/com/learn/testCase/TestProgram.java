package com.learn.testCase;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import  static  io.restassured.RestAssured.given;

public class TestProgram {
    //注册
    @Test
    public void testRegister1(){
        String jsonDate = "{\"mobile_phone\":\"13312342234\",\"pwd\":\"12345678\",\"tppe\":\"0\"}";
        Response res =
        given().
                body(jsonDate).
                header("Content-Type","application/json").
                header("X-Lemonban-Media-Type","lemonban.v1").
                when().
                post("http://api.lemonban.com/futureloan/member/register").
                then().log().all().extract().response();
        res.jsonPath().get("data.id");
    }
    @Test
    public void testRegister2(){
        String jsonDate = "{\"mobile_phone\":\"13912342239\",\"pwd\":\"12345678\",\"tppe\":\"1\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/register").
                        then().log().all().extract().response();
        res.jsonPath().get("data.id");
    }
    @Test
    public void testRegister3(){
        String jsonDate = "{\"mobile_phone\":\"13312342236\",\"pwd\":\"12345678\",\"tppe\":\"1\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/register").
                        then().log().all().extract().response();
        res.jsonPath().get("data.id");
    }
    //登录
    @Test
    public void testLogin(){
        String jsonDate = "{\"mobile_phone\":\"13312342234\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                        then().log().all().extract().response();
        res.jsonPath().get("data.id");
    }
    //充值
    @Test
    public void testRecharge(){
        String jsonDate = "{\"mobile_phone\":\"13312342235\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                        then().
                        extract().response();
        int memberId = res.jsonPath().get("data.id");
        System.out.println(memberId);
        String token = res.jsonPath().get("data.token_info.token");
        System.out.println(token);
        String jsonDate1 = "{\"member_id\":"+memberId+",\"amount\":\"100000.00\"}";
        Response res2 =
                given().
                        body(jsonDate1).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        header("Authorization","Bearer "+token).
                        when().
                        post("http://api.lemonban.com/futureloan/member/recharge").
                        then().log().all().extract().response();
    }
    //新增项目
    @Test
    public void testAdd(){
        String jsonDate = "{\"mobile_phone\":\"13312342234\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                        then().log().all().extract().response();
        int memberId = res.jsonPath().get("data.id");
        String jsonDate1 = "{\"member_id\":"+memberId+",\"title\":\"学习Java自动化\",\"amount\":\"100000.00\"," +
                "\"loan_rate\":\"8.0\",\"loan_term\":\"6\",\"loan_date_type\":\"1\",\"bidding_days\":\"5\"}";
        Response res2 =
                given().
                        body(jsonDate1).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/loan/add").
                        then().log().all().extract().response();
    }
    //审核项目
    @Test
    public void testAudit(){
        String jsonDate = "{\"mobile_phone\":\"13312342234\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                        then().extract().response();
        int memberId = res.jsonPath().get("data.id");
        String jsonDate1 = "{\"member_id\":"+memberId+",\"title\":\"学习Java自动化\",\"amount\":\"100000.00\"," +
                "\"loan_rate\":\"8.0\",\"loan_term\":\"6\",\"loan_date_type\":\"1\",\"bidding_days\":\"5\"}";
        Response res2 =
                given().
                        body(jsonDate1).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/loan/add").
                        then().log().all().extract().response();
        int loanId = res2.jsonPath().get("data.id");
        String jsonDate2 = "{\"loan_id\":"+loanId+",\"approved_or_not\":\"true\"}";
        Response res3 =
                given().
                        body(jsonDate2).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        patch("http://api.lemonban.com/futureloan/loan/audit").
                        then().log().all().extract().response();

    }
    //登录
    @Test
    public void testInvest(){
        String jsonDate = "{\"mobile_phone\":\"13312342235\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        body(jsonDate).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/login").
                        then().log().all().extract().response();
        int memberId = res.jsonPath().get("data.id");

        String jsonDate3 = "{\"member_id\":"+memberId+",\"loan_id\":\"2029813\",\"amount\":\"100000.00\"}";
        Response res3 =
                given().
                        body(jsonDate3).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v1").
                        when().
                        post("http://api.lemonban.com/futureloan/member/invest").
                        then().log().all().extract().response();
    }

}
