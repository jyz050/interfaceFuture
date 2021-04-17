package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExcelPojo {
    //用例编号	用例描述	接口名称	请求头	请求方式	url	参数	参数类型	期望结果
    @Excel(name = "用例编号")
    private int cassId;
    @Excel(name = "用例描述")
    private String caseDescribe;
    @Excel(name = "接口名称")
    private String apiName;
    @Excel(name = "请求头")
    private String requestHeader;
    @Excel(name = "请求方式")
    private String requestMethod;
    @Excel(name = "url")
    private String url;
    @Excel(name = "参数")
    private String params;
    @Excel(name = "参数类型")
    private String contentType;
    @Excel(name = "期望响应结果")
    private String expectResult;
    @Excel(name = "提取返回数据")
    private String extractData;
    @Excel(name = "数据库校验")
    private String dbAssert;

    public int getCassId() {
        return cassId;
    }

    public void setCassId(int cassId) {
        this.cassId = cassId;
    }

    public String getCaseDescribe() {
        return caseDescribe;
    }

    public void setCaseDescribe(String caseDescribe) {
        this.caseDescribe = caseDescribe;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }

    public String getExtractData() {
        return extractData;
    }

    public void setExtractData(String extractData) {
        this.extractData = extractData;
    }

    public String getDbAssert() {
        return dbAssert;
    }

    public void setDbAssert(String dbAssert) {
        this.dbAssert = dbAssert;
    }

    @Override
    public String toString() {
        return "ExcelPojo{" +
                "cassId=" + cassId +
                ", caseDescribe='" + caseDescribe + '\'' +
                ", apiName='" + apiName + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectResult='" + expectResult + '\'' +
                ", extractData='" + extractData + '\'' +
                ", dbAssert='" + dbAssert + '\'' +
                '}';
    }
}
