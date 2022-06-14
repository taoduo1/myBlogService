package com.example.shop_user.common.exception;

import com.example.shop_common.common.dto.CoreException;
import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.common.response.ErrorInfo;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duo.tao
 * @Description: 全局异常处理
 * @date 2022-06-13 23:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 系统异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ActionResult<String> error(Exception e) {
        e.printStackTrace();
        return ResultUtil.fail(new ErrorInfo(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    private ActionResult<String> handleIllegalArgumentException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (DataUtil.notNullOrEmpty(errors)) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorList.add(new ErrorInfo(fieldError.getDefaultMessage()));
                });
            }
        }
        return ResultUtil.fail(errorList);
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CoreException.class)
    @ResponseBody
    public ActionResult<String> divError(CoreException e) {
        return ResultUtil.fail(new ErrorInfo(e.getCode(), e.getMessage()));
    }

}
