package com.hrd;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 文件处理器
 *
 * @author ${author}
 * @since ${currentDate}
 */
public class FileProcessor {


    protected File templateFile;

    protected File projectFile;

    protected Dict dict;

    private String[] ignoreFiles = new String[]{"logback.xml", "bootstrap.yml"};

    public FileProcessor(File templateFile, File projectFile, Dict dict) {
        this.templateFile = templateFile;
        this.projectFile = projectFile;
        this.dict = dict;
    }


    public static final TemplateEngine ENGINE = TemplateUtil.createEngine(new TemplateConfig());

    public void process() {
        if (this.templateFile.isFile()) {
            rendFile();
        } else {
            //文件夹 递归处理
            File newProjectFile = rendPath();
            File[] files = this.templateFile.listFiles();
            if (files != null) {
                for (File subTemplateFile : files) {
                    new FileProcessor(subTemplateFile, newProjectFile, dict).process();
                }
            }
        }
    }

    private File rendPath() {
        String parentPath = this.templateFile.getName();
        String content = rend(parentPath);
        String relatePath = content.replace(".", File.separator);
        String path = FileUtil.getAbsolutePath(this.projectFile) + File.separator + relatePath;
        return FileUtil.mkdir(new File(path));

    }


    private void rendFile() {
        String fileName = this.templateFile.getName();
        fileName = rend(fileName);
        String templateStr = FileUtil.readString(this.templateFile, StandardCharsets.UTF_8);
        String content;
        if (!ArrayUtil.contains(ignoreFiles, fileName)) {
            content = rend(templateStr);
        } else {
            content = templateStr;
        }
        FileUtil.writeString(content, projectFile.getAbsolutePath() + File.separator + fileName, StandardCharsets.UTF_8);
    }

    private String rend(String templateStr) {
        Template template = ENGINE.getTemplate(templateStr);
        return template.render(dict);
    }


}
