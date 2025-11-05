package org.shiroko.ai.exception;

import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.FieldErrorRespVO;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // 全局异常处理 RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRespVO<List<FieldErrorRespVO>> handleValidationException(MethodArgumentNotValidException e) {
        // 提取所有校验失败的字段和message
        List<FieldErrorRespVO> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorRespVO(error.getField(), error.getDefaultMessage())) // 转换为自定义错误DTO
                .collect(Collectors.toList());
        // 返回自定义响应DTO（code可设为400，也可根据需求设为其他，如200）
        return BaseRespVO.failed(400, "参数校验失败", errors);
    }

    // 处理请求体解析失败（如JSON格式错误）
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseRespVO<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        String errorMsg = "请求体格式错误：";
        return BaseRespVO.failed(400, errorMsg, e.getLocalizedMessage());
    }

}
