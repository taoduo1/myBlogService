package com.example.shop_common.utils;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.common.response.ErrorInfo;
import com.example.shop_common.common.response.Status;

import java.util.List;

public class ResultUtil {


    private ResultUtil() {
    }


    /**
     * Ok.
     *
     * @param <T> the generic type
     * @return the action result
     */
    public static <T> ActionResult<T> ok() {
        ActionResult<T> actionResult = new ActionResult<>();
        actionResult.setSuccess(true);
        actionResult.setCode(Status.SUCCESS.code);
        return actionResult;
    }


    /**
     * Ok.
     *
     * @param <T> the generic type
     * @param data the data
     * @return the action result
     */
    public static <T> ActionResult<T> ok(T data) {
        ActionResult<T> actionResult = new ActionResult<>();
        actionResult.setSuccess(true);
        actionResult.setData(data);
        actionResult.setCode(Status.SUCCESS.code);
        return actionResult;
    }

    /**
     * Fail.
     *
     * @param <T> the generic type
     * @return the action result
     */
    public static <T> ActionResult<T> fail() {
        ActionResult<T> result = new ActionResult<>();
        result.setSuccess(false);
        return result;
    }

    /**
     * Fail.
     *
     * @param <T> the generic type
     * @param error the error
     * @return the action result
     */
    public static <T> ActionResult<T> fail(ErrorInfo error) {
        ActionResult<T> result = new ActionResult<>();
        result.setSuccess(false);
        result.setErrorInfo(List.of(error));
        result.setCode(Status.SYSTEM_ERROR.code);
        return result;
    }

    /**
     * Fail.
     *
     * @param <T> the generic type
     * @param errors the errors
     * @return the action result
     */
    public static <T> ActionResult<T> fail(List<ErrorInfo> errors) {
        ActionResult<T> result = new ActionResult<>();
        result.setSuccess(false);
        result.setErrorInfo(errors);
        result.setCode(Status.SYSTEM_ERROR.code);
        return result;
    }

}
