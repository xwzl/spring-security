package com.java.plus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.java.plus.old.MybatisHandy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author xuweizhi
 * @date 2019/04/03 18:15
 */
public class GeneratorUntil {


    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确de" + tip + "！");
    }


    public static void generatorCode(final MybatisHandy helper) {
        // 代码生成器
        final AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        String projectPath = setGlobalConfig(mpg, helper);

        // 数据源配置
        setDataSourece(mpg, helper);

        // 包配置
        PackageConfig pc = setPackageConfig(mpg, helper);

        // 自定义配置
        setCustomerConfig(mpg, helper, projectPath, pc);

        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        setStragegyConfig(mpg, helper, pc);

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    private static void setStragegyConfig(AutoGenerator mpg, MybatisHandy helper, PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        if (StringUtils.isNotEmpty(helper.getBaseEntitySimpleName())) {
            strategy.setSuperEntityClass("com.java.mybatis.entity.BaseEntity");
        }

        strategy.setEntityLombokModel(true);

        if (StringUtils.isNotEmpty(helper.getBaseControllerSimpleName())) {
            String servicePackageName = pc.getParent() + ".controller." + helper.getBaseControllerSimpleName();
            strategy.setSuperControllerClass("com.java.mybatis.controller.BaseController");
        }

        if (StringUtils.isNotEmpty(helper.getBaseServiceSimpleName())) {
            String servicePackageName = pc.getParent() + ".service." + helper.getBaseServiceSimpleName();
            strategy.setSuperServiceClass(servicePackageName);
        } else {
            strategy.setSuperServiceClass(pc.getParent() + ".service.BaseService");
        }

        if (StringUtils.isNotEmpty(helper.getBaseServiceImplSimpleName())) {
            String serviceImplPackageName = pc.getParent() + ".service.impl." + helper.getBaseServiceSimpleName();
        } else {
            strategy.setSuperServiceImplClass(pc.getParent() + ".service.impl.BaseServiceImpl");
        }

        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));

        //strategy.setSuperEntityColumns("id");

        strategy.setControllerMappingHyphenStyle(true);

        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
    }

    private static void setCustomerConfig(AutoGenerator mpg, MybatisHandy helper, String projectPath, PackageConfig pc) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                System.out.println("配置初始化");
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();

        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = "/src/main/java/";
                String basePackage = pc.getParent().replaceAll("\\.", "/");
                if (helper.isChildModule()) {
                    path = "/" + helper.getChildModuleName() + path + basePackage;
                } else {
                    path = path + basePackage;
                }

                return projectPath + path + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }

    /**
     * 配置包
     */
    private static PackageConfig setPackageConfig(AutoGenerator mpg, MybatisHandy helper) {
        PackageConfig pc = new PackageConfig();

        pc.setModuleName(scanner("模块名"));

        if (StringUtils.isNotEmpty(helper.getBasePackageName())) {
            pc.setParent(helper.getBasePackageName());
        } else {
            pc.setParent("com.java");
        }

        pc.setEntity("model");

        mpg.setPackageInfo(pc);

        return pc;
    }

    /**
     * 设置全局配置
     */
    private static String setGlobalConfig(AutoGenerator mpg, MybatisHandy helper) {
        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig();

        if (helper.isChildModule()) {
            gc.setOutputDir(projectPath + "/" + helper.getChildModuleName() + "/src/main/java");
        } else {
            gc.setOutputDir(projectPath + "/src/main/java");
        }
        if (StringUtils.isNotEmpty(helper.getAuthor())) {
            gc.setAuthor(helper.getAuthor());
        } else {
            gc.setAuthor("Admin");
        }

        gc.setOpen(false);
        //去除service报名前缀i
        gc.setServiceName("%sService");
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        return projectPath;
    }

    /**
     * 设置数据库配置信息
     */
    private static void setDataSourece(AutoGenerator mpg, MybatisHandy helper) {
        DataSourceConfig dsc = new DataSourceConfig();

        if (StringUtils.isNotEmpty(helper.getUrl())) {
            dsc.setUrl("jdbc:mysql://" + helper.getUrl() + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        } else {
            dsc.setUrl("jdbc:mysql://" + "192.168.26.20:3306/wtf" + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        }
        // dsc.setSchemaName("public");
        if (StringUtils.isNotEmpty(helper.getClassPath())) {
            dsc.setDriverName(helper.getClassPath());
        } else {
            dsc.setDriverName("com.mysql.jdbc.Driver");
        }
        if (StringUtils.isNotEmpty(helper.getUsername())) {
            dsc.setUsername(helper.getUsername());
        } else {
            dsc.setUsername("root");
        }
        if (StringUtils.isNotEmpty(helper.getPassword())) {
            dsc.setPassword(helper.getPassword());
        } else {
            dsc.setPassword("158262751");
        }
        mpg.setDataSource(dsc);
    }
}
