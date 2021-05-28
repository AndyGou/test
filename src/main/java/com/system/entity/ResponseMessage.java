package com.system.entity;

import com.system.enumeration.ReturnCodeEnum;
import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {

  /**
  * <p>返回code 20001:失败  20000:成功<p>
  */
  private Integer code;

  /**
  * <p>消息实体<p>
  */
  private Map<String,Object> data = new HashMap<String, Object>();

  private ResponseMessage() {}

  public static ResponseMessage success() {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setCode(ReturnCodeEnum.SUCCESS.getCode());

    return responseMessage;
  }

  public static ResponseMessage error() {

    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setCode(ReturnCodeEnum.ERROR.getCode());
    return responseMessage;
  }

  public ResponseMessage data(String key,Object value) {
    this.data.put(key,value);
    return this;
  }

  public ResponseMessage data(Map<String,Object> map) {
    this.setData(map);
    return this;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

}