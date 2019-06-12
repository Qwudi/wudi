package com.hwq.wudi.config;

import com.hwq.wudi.util.Constant;
import com.hwq.wudi.util.DateUtil;
import com.hwq.wudi.util.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestControllerAdvice
@Slf4j
public class GlobleExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespEntity<String> runtimeExceptionHandler(Exception e){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String error = null;
        if (e.getStackTrace().length > 0) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];// 得到异常棧的首个元素
            error = "Class：" + stackTraceElement.getFileName() + ", Method：" + stackTraceElement.getMethodName() + ", Line：" + stackTraceElement.getLineNumber() + ", error：" + e.getMessage() + ", time：" + DateUtil.getNowTime_EN();
        }
        e.printStackTrace();
        return RespEntity.fail(Constant.ERROR, Constant.ERROR_MSG, error);
    }
    //继承的ResponseEntityExceptionHandler已经拦截了MethodArgumentNotValidException
    //不能重复拦截，如果必须拦截，重写父类方法
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    @ResponseBody
//    public RespEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//
//        // 异常信息
//        String err = extractErrorMsg(e.getBindingResult());
//
//        return RespEntity.fail(Constant.ERROR, Constant.ERROR_MSG, null);
//    }
//
//    private String extractErrorMsg(BindingResult result) {
//        List<FieldError> errors = result.getFieldErrors();
//
//        return errors.stream().map(e -> e.getField()+ ":" + e.getDefaultMessage())
//                .reduce((s1, s2) -> s1 + " ; " +s2).orElseGet( ()->"参数非法");
//    }
}
