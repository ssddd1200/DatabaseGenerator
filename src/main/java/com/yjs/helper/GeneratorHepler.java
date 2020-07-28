package com.yjs.helper;


import com.alibaba.fastjson.JSONObject;
import com.yjs.entity.Column;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GeneratorHepler {

    public void generateCommonFile(Map<String, String> config) throws Exception {
        String suffix = GeneratorConstant.MAPPER_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.COMMON_MAPPER_PATH, "My", suffix);
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("正在生成mymapper.......");
            String templateName = GeneratorConstant.COMMON_TENPLATE;
            JSONObject data = toJsonObject(config);
            this.generateFileByTemplate(templateName, f, data);
        }else {
            System.out.println("已存在mymapper，无需生成");
        }
    }

    public void generateEntityFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成实体......");
        String suffix = GeneratorConstant.ENTITY_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.ENTITY_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.ENTITY_TEMPLATE;
        File entityFile = new File(path);
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, entityFile, data);
        String suffix2 = GeneratorConstant.PAGE_FILE_SUFFIX;
        String path2 = getFilePath(GeneratorConstant.ENTITY_PATH, config.get("className"), suffix2);
        File entityFile2 = new File(path2);
        JSONObject data2 = toJsonObject(config);
        data.put("columns", "");
        this.generateFileByTemplate(templateName, entityFile2, data2);
    }

    public void generateMapperFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成Mapper......");
        String suffix = GeneratorConstant.MAPPER_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.MAPPER_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.MAPPER_TEMPLATE;
        File mapperFile = new File(path);
        generateFileByTemplate(templateName, mapperFile, toJsonObject(config));
    }

    public void generateServiceFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成service......");
        String suffix = GeneratorConstant.SERVICE_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.SERVICE_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.SERVICE_TEMPLATE;
        File serviceFile = new File(path);
        generateFileByTemplate(templateName, serviceFile, toJsonObject(config));
    }

    public void generateServiceImplFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成seriveimpl......");
        String suffix = GeneratorConstant.SERVICEIMPL_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.SERVICE_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.SERVICEIMPL_TEMPLATE;
        File serviceImplFile = new File(path);
        generateFileByTemplate(templateName, serviceImplFile, toJsonObject(config));
    }

    public void generateControllerFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成controller......");
        String suffix = GeneratorConstant.CONTROLLER_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.CONTROLLOR_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.CONTROLLER_TEMPLATE;
        File controllerFile = new File(path);
        generateFileByTemplate(templateName, controllerFile, toJsonObject(config));
    }

    public void generateMapperXmlFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成xml......");
        String suffix = GeneratorConstant.MAPPERXML_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.MAPPERXML_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.MAPPERXML_TEMPLATE;
        File mapperXmlFile = new File(path);
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        generateFileByTemplate(templateName, mapperXmlFile, data);
    }

    public void generateHtmlFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成Html......");
        String suffix = GeneratorConstant.HTML_FILE_SUFFIX;
        String path = getFilePath(GeneratorConstant.HTML_PATH, config.get("className")+"2", suffix);
        String templateName = GeneratorConstant.HTML_TEMPLATE;
        File htmlFile = new File(path);
        // 表单
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, htmlFile, data);
        String path2 = getFilePath(GeneratorConstant.HTML_PATH, config.get("className")+"1", suffix);
        File htmlFile2 = new File(path2);
        JSONObject data2 = toJsonObject(config);
        data.put("columns", "");
        this.generateFileByTemplate(templateName, htmlFile2, data2);
    }

    public void generateJSFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成JS......");
        String suffix = GeneratorConstant.JS_FILE_SUFFLX;
        String path = getFilePath(GeneratorConstant.JS_PATH, "grid_"+config.get("className"), suffix);
        String templateName = GeneratorConstant.JS_TEMPLATE;
        File jsFile = new File(path);
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, jsFile, data);
        // edit_XXX.js
        String path2 = getFilePath(GeneratorConstant.JS_PATH, "edit_"+config.get("className"), suffix);
        File jsFile2 = new File(path2);
        JSONObject data2 = toJsonObject(config);
        data.put("columns", "");
        this.generateFileByTemplate(templateName, jsFile2, data2);
    }

    public void generateOracleFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成Orcle实体对象......");
        String suffix = GeneratorConstant.ORACLE_SAVE_SUFFLX;
        String path = getFilePath(GeneratorConstant.ORACLE_JAVA_PATH, config.get("className"), suffix);
        String templateName = GeneratorConstant.ORACLE_JAVA_TEMPLATE;
        File jsFile = new File(path);
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, jsFile, data);
        // page
        String suffix2 = GeneratorConstant.ORACLE_PAGE_SUFFLX;
        String path2 = getFilePath(GeneratorConstant.ORACLE_JAVA_PATH, config.get("className"), suffix2);
        File jsFile2 = new File(path2);
        JSONObject data2 = toJsonObject(config);
        data.put("columns", "");
        this.generateFileByTemplate(templateName, jsFile2, data2);
    }

    public void generateOraleDataFile(List<Column> columns, Map<String, String> config) throws Exception {
        System.out.println("正在生成Oracle存储过程......");
        String path = getFilePath(GeneratorConstant.ORACLE_DATA_PATH, config.get("className"), ".txt");
        String templateName = GeneratorConstant.ORACLE_DATA_TEMPLATE;
        File jsFile = new File(path);
        JSONObject data = toJsonObject(config);
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, jsFile, data);
    }

    @SuppressWarnings("all")
    private void generateFileByTemplate(String templateName, File file, Object data) throws Exception {

        Template template = getTemplate(templateName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), 10240);
        try {
            template.process(data, out);
        } catch (Exception e) {
            String message = "代码生成异常";
            throw new Exception(message);
        }
    }

    private String getFilePath(String filePath, String packagePath, String suffix) {
        String path = System.getProperty("user.dir")+filePath;
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return path + packagePath + suffix;
    }

    private JSONObject toJsonObject(Object o) {
        return JSONObject.parseObject(JSONObject.toJSON(o).toString());
    }


    private Template getTemplate(String templateName) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        String filePath = "";
        String templatePath = GeneratorHepler.class.getResource("/generator/templates/").toString();
        if (templatePath.startsWith("jar:file")){
            filePath = "./templates/";
        }else {
            filePath = GeneratorHepler.class.getResource("/generator/templates/").getPath();
        }
        configuration.setDirectoryForTemplateLoading(new File(filePath));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return configuration.getTemplate(templateName);
    }
}
