为了统一api的返回格式，方便前端接受返回数据，全局异常处理是个比较重要的功能，一般在项目里都会用到。
 
## 1. Springboot中的异常捕获通常分为三个阶段。

### 1.1 在进入Controller之前，如果请求一个不存在的地址，404错误等 

详情查看 [FinalExceptionHandler](https://github.com/xwzl/Notes/blob/master/spring-boot/src/main/java/com/xwz/boot/configure/exception/FinalExceptionHandler.java) 类 .

### 1.2 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误。 

详情查看 [GlobalExceptionHandler](https://github.com/xwzl/Notes/blob/master/spring-boot/src/main/java/com/xwz/boot/configure/exception/GlobalExceptionHandler.java) 类 .

### 1.3 以上都正常时，在controller里执行逻辑代码时出的异常。譬如NullPointerException

查看 [GlobalDefaultExceptionHandler](https://github.com/xwzl/Notes/blob/master/spring-boot/src/main/java/com/xwz/boot/configure/exception/GlobalDefaultExceptionHandler.java) 类 .



