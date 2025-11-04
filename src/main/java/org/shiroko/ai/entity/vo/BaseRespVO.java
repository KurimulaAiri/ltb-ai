package org.shiroko.ai.entity.vo;

import lombok.Data;
import org.shiroko.ai.util.TimeConvertUtils;

import java.time.LocalDateTime;

@Data
public class BaseRespVO<T> {

    // 是否成功
    private boolean success;

    // 响应码
    private Integer code;

    // 响应信息
    private String message;

    // 响应时间
    private String timestamp;

    // 响应数据
    private T data;

    /***
     * 创建成功响应
     * @param data 响应数据
     * @return BaseResponseDTO
     * @param <T> 响应数据类型
     */
    public static <T> BaseRespVO<T> succeed(T data) {
        return succeed("成功", data);
    }

    public static <T> BaseRespVO<T> succeed(String message) {
        return succeed(message, null);
    }

    public static <T> BaseRespVO<T> succeed(String message ,T data) {
        BaseRespVO<T> response = new BaseRespVO<>();
        response.setSuccess(true);
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(TimeConvertUtils.localDateTimeToString(LocalDateTime.now(), TimeConvertUtils.DEFAULT_DATETIME_FORMAT));
        return response;
    }

    /***
     * 创建失败响应
     * @param code 响应码
     * @param message 响应信息
     * @param data 响应数据
     * @return BaseResponseDTO
     * @param <T> 响应数据类型
     */
    public static <T> BaseRespVO<T> failed(Integer code, String message, T data) {
        BaseRespVO<T> response = new BaseRespVO<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(TimeConvertUtils.localDateTimeToString(LocalDateTime.now(), TimeConvertUtils.DEFAULT_DATETIME_FORMAT));
        return response;
    }

     /***
     * 创建失败响应
     * @param code 响应码
     * @param message 响应信息
     * @return BaseResponseDTO
     * @param <T> 响应数据类型
     */
    public static <T> BaseRespVO<T> failed(Integer code, String message) {
        return failed(code, message, null);
    }
}
