package com.java.boot.system.config.other;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * 一般情况下我们和配置类（Configuration）一起使用，但是实际上，我们也可以添加到普通的 Bean 类上.
 * <p>
 * 主要用于 Bean 容器的注入，返回为 false 表示不注入
 *
 * @author xuweizhi
 * @since  2019/04/22 14:01
 */
public class ConditionSample implements Condition {

    /**
     * 通过调用 Condition#matches(...) 方法，判断该是否匹配。如果不匹配，内部所有方法，都无法创建 Bean 对象。
     * <p>
     * 通过调用 Condition#matches(...) 方法，判断是否匹配。如果不匹配，则不从该方法加载 BeanDefinition 。
     * 这样，就不会创建对应的 Bean 对象了。
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return true;
    }

    /**
     * 获得注解的是方法名还是类名。
     */
    private static String getClassOrMethodName(AnnotatedTypeMetadata metadata) {
        // 类
        if (metadata instanceof ClassMetadata) {
            ClassMetadata classMetadata = (ClassMetadata) metadata;
            return classMetadata.getClassName();
        }
        // 方法
        MethodMetadata methodMetadata = (MethodMetadata) metadata;
        return methodMetadata.getDeclaringClassName() + "#" + methodMetadata.getMethodName();
    }



    //ProfileCondition ，实现 Condition 接口，给 @Profile 使用的 Condition 实现类。
    //@Override
    //public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    //    // 获得 @Profile 注解的属性
    //    MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
    //    // 如果非空，进行判断
    //    if (attrs != null) {
    //        // 遍历所有 @Profile 的 value 属性
    //        for (Object value : attrs.get("value")) {
    //            // 判断 environment 有符合的 Profile ，则返回 true ，表示匹配
    //            if (context.getEnvironment().acceptsProfiles(Profiles.of((String[]) value))) {
    //                return true;
    //            }
    //        }
    //        // 如果没有，则返回 false
    //        return false;
    //    }
    //    // 如果为空，就表示满足条件
    //    return true;
    //}

    // SpringBootCondition ，实现 Condition 接口，Spring Boot Condition 的抽象基类，主要用于提供相应的日志，帮助开发者判断哪些被进行加载。
    //@Override
    //public final boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    //    // <1> 获得注解的是方法名还是类名
    //    String classOrMethodName = getClassOrMethodName(metadata);
    //    try {
    //        // <2> 条件匹配结果
    //        ConditionOutcome outcome = getMatchOutcome(context, metadata);
    //        // <3> 打印结果
    //        logOutcome(classOrMethodName, outcome);
    //        // <4> 记录
    //        recordEvaluation(context, classOrMethodName, outcome);
    //        // <5> 返回是否匹配
    //        return outcome.isMatch();
    //    } catch (NoClassDefFoundError ex) {
    //        throw new IllegalStateException(
    //                "Could not evaluate condition on " + classOrMethodName + " due to "
    //                        + ex.getMessage() + " not "
    //                        + "found. Make sure your own configuration does not rely on "
    //                        + "that class. This can also happen if you are "
    //                        + "@ComponentScanning a springframework package (e.g. if you "
    //                        + "put a @ComponentScan in the default package by mistake)",
    //                ex);
    //    } catch (RuntimeException ex) {
    //        throw new IllegalStateException("Error processing condition on " + getName(metadata), ex);
    //    }
    //}

    // anyMatches(ConditionContext context, AnnotatedTypeMetadata metadata, Condition... conditions) 方法，判断是否匹配指定的 Condition 们中的任一一个。
    //protected final boolean anyMatches(ConditionContext context, AnnotatedTypeMetadata metadata, Condition... conditions) {
    //    // 遍历 Condition
    //    for (Condition condition : conditions) {
    //        // 执行匹配
    //        if (matches(context, metadata, condition)) {
    //            return true;
    //        }
    //    }
    //    return false;
    //}

}
