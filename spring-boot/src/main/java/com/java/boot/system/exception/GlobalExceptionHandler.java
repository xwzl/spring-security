package com.java.boot.system.exception;

import com.java.boot.base.until.LogUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * 从名字就可以看出来，这是在controller做环切（这里面还可以全局异常捕获），在参数进入handler之前进行转换；转换为我们相应的对象。
 * <p>
 * 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误。
 *
 * @author xuweizhi
 */
@RestControllerAdvice(value = "com.java.boot")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LogUtils.getExceptionLogger();

   /* private static Map<String, Object> cache = new HashMap<>();

    static {
        cache.put("result", new ResultVO<ErrorInfo>());
        cache.put("error", new ErrorInfo<>());
    }*/

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    /*@NotNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NotNull Exception ex, Object body, HttpHeaders headers, HttpStatus status, @NotNull WebRequest request) {
        logger.error(ex.getMessage());

        ResultVO<ErrorInfo> resultVO = (ResultVO<ErrorInfo>) cache.get("result");

        resultVO.setCode(ResultEnum.FAILURE.getCode());
        resultVO.setSuccess(ResultEnum.FAILURE.getStatus());
        resultVO.setMessage(ResultEnum.FAILURE.getMessage());

        ErrorInfo<Object> errorInfo = (ErrorInfo<Object>) cache.get("error");

        errorInfo.setCode(HttpStatusEnum.NOT_ACCEPTABLE.getCode());
        errorInfo.setMessage(HttpStatusEnum.NOT_ACCEPTABLE.getMessage());
        errorInfo.setUrl(request.getContextPath());
        errorInfo.setData(ex.getMessage());

        return new ResponseEntity<Object>(resultVO, status);
    }*/

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        });
    }

}