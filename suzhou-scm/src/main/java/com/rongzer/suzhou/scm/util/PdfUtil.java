package com.rongzer.suzhou.scm.util;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.Style;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * pdf生成工具类
 *
 * @author xujun
 * @date 2022/1/7
 */
@Slf4j
public class PdfUtil {

    private static final int MAX_HEIGHT = 900;

    private static final int MAX_WIDTH = 560;

    private static final String FONT_FOLDER = "/usr/share/fonts";

    /**
     * 填充word模板转pdf，返回字符串
     * 【校验水印】->【加载模板】->【poi-tl填充】->【aspose-words生成】
     * -------------------------
     * data参数中value类型及模板格式：
     * {{template}}  普通文本，渲染数据为：String或TextRenderData
     * {{@template}} 图片，渲染数据为：PictureRenderData
     * {{#template}} 表格，渲染数据为：TableRenderData
     * {{*template}} 列表，渲染数据为：NumbericRenderData
     * -------------------------
     *
     * @param templatePath word模板路径，example：template/scm-2.docx
     * @param data         模板填充键值对
     * @return String
     */
    public static String word2pdf(String templatePath, Map<String, Object> data) {
        if (!getLicense()) {
            throw new RuntimeException("pdf去水印校验异常");
        }
        // 加载模板
        Resource resource = new ClassPathResource(templatePath);
        if (!resource.exists()) {
            throw new RuntimeException("模板文件不存在，" + templatePath);
        }
        try (ByteArrayOutputStream wordByteArrayOutputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream pdfByteArrayOutputStream = new ByteArrayOutputStream()) {
            // poi-tl渲染模板
            XWPFTemplate xwpfTemplate = XWPFTemplate.compile(resource.getInputStream()).render(data);
            xwpfTemplate.write(wordByteArrayOutputStream);
            // aspose-words加载
            Document document = new Document(new ByteArrayInputStream(wordByteArrayOutputStream.toByteArray()));
            // 设置字体源，此处为macos，生产须变更
            FontSettings.setFontsFolder(FONT_FOLDER, true);
            // 生成pdf
            document.save(pdfByteArrayOutputStream, SaveFormat.PDF);
            return Base64.getEncoder().encodeToString(pdfByteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            return "生成过程出错，" + e.getMessage();
        }
    }

    /**
     * 填充word模板转pdf，存储指定目录
     * 【校验水印】->【加载模板】->【poi-tl填充】->【aspose-words生成】
     * -------------------------
     * data参数中value类型及模板格式：
     * {{template}}  普通文本，渲染数据为：String或TextRenderData
     * {{@template}} 图片，渲染数据为：PictureRenderData
     * {{#template}} 表格，渲染数据为：TableRenderData
     * {{*template}} 列表，渲染数据为：NumbericRenderData
     * -------------------------
     *
     * @param templatePath word模板路径，example：template/scm-2.docx
     * @param targetPath   目标路径
     * @param data         模板填充键值对
     */
    public static boolean word2pdf(String templatePath, String targetPath, Map<String, Object> data) {
        if (!getLicense()) {
            throw new RuntimeException("pdf去水印校验异常");
        }
        // 加载模板
        Resource resource = new ClassPathResource(templatePath);
        if (!resource.exists()) {
            throw new RuntimeException("模板文件不存在，" + templatePath);
        }
        try (ByteArrayOutputStream wordByteArrayOutputStream = new ByteArrayOutputStream()) {
            // poi-tl渲染模板
            XWPFTemplate xwpfTemplate = XWPFTemplate.compile(resource.getInputStream()).render(data);
            xwpfTemplate.write(wordByteArrayOutputStream);
            // aspose-words加载
            Document document = new Document(new ByteArrayInputStream(wordByteArrayOutputStream.toByteArray()));
            // 设置字体源，此处为macos，生产须变更
            FontSettings.setFontsFolder(FONT_FOLDER, true);
            // 生成pdf
            document.save(targetPath, SaveFormat.PDF);
            return true;
        } catch (Exception e) {
            log.error("生成pdf失败", e);
        }
        return false;
    }

    /**
     * 去水印校验
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            Resource resource = new ClassPathResource("license.xml");
            InputStream is = resource.getInputStream();
            License license = new License();
            license.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置图片
     *
     * @param url      网络路径或本地路径
     * @param location 0居中1居左2居右
     * @return PictureRenderData
     */
    public static PictureRenderData getPictureRenderData(String url, int location) throws IOException {
        int oldHeight;
        int oldWidth;
        int outWidth;
        int outHeight;
        if (url.startsWith("http")) {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            BufferedImage img = ImageIO.read(is);
            oldHeight = img.getHeight(null);
            oldWidth = img.getWidth(null);
        } else {
            File file = new File(url);
            BufferedImage img = ImageIO.read(file);
            oldHeight = img.getHeight(null);
            oldWidth = img.getWidth(null);
        }

        // 小于指定宽高则不处理
        if (oldHeight <= MAX_HEIGHT && oldWidth <= MAX_WIDTH) {
            Pictures.PictureBuilder builder = Pictures.of(url).size(oldWidth, oldHeight);
            if (location == 0) {
                return builder.center().create();
            }
            if (location == 1) {
                return builder.left().create();
            }
            if (location == 2) {
                return builder.right().create();
            }
            return null;
        }

        // 计算比例
        if (oldWidth > MAX_WIDTH) {
            outWidth = MAX_WIDTH;
            outHeight = (int) Math.floor((oldHeight / ((double) oldWidth / MAX_WIDTH)));
            if (outHeight > MAX_HEIGHT) {
                outWidth = (int) Math.floor(outWidth / ((double) outHeight / MAX_HEIGHT));
                outHeight = MAX_HEIGHT;
            }
        } else {
            outHeight = MAX_HEIGHT;
            outWidth = (int) Math.floor(oldWidth / ((double) oldHeight / MAX_HEIGHT));
        }
        Pictures.PictureBuilder builder = Pictures.of(url).size(outWidth, outHeight);
        if (location == 0) {
            return builder.center().create();
        }
        if (location == 1) {
            return builder.left().create();
        }
        if (location == 2) {
            return builder.right().create();
        }
        return null;
    }

    /**
     * 设置下划线
     *
     * @param text 文本参数
     * @return TextRenderData
     */
    public static TextRenderData getUnderlineText(String text) {
        Style style = Style.builder().build();
        style.setUnderlinePatterns(UnderlinePatterns.SINGLE);
        return Texts.of(text).style(style).create();
    }

    /**
     * 初始化表格，默认传入表头
     *
     * @return TableRenderData
     */
    public static TableRenderData ofTable(String... headers) {
        BorderStyle borderStyle = new BorderStyle();
        borderStyle.setColor("FFFFFF");
        borderStyle.setSize(5);
        borderStyle.setType(XWPFTable.XWPFBorderType.SINGLE);
        TableRenderData table = Tables.ofA4MediumWidth().center().border(borderStyle).create();
        table.addRow(Rows.of(headers).bgColor("2E75B5").textColor("FFFFFF").verticalCenter().center().create());
        return table;
    }

    /**
     * 初始化行
     *
     * @return RowRenderData
     */
    public static RowRenderData ofRow(Object... columns) {
        RowRenderData row = Rows.of().verticalCenter().center().bgColor("D9D9D9").textColor("000000").create();
        for (Object o : columns) {
            CellRenderData cellRenderData = new CellRenderData();
            ParagraphRenderData paragraphRenderData = new ParagraphRenderData();
            if (o != null) {
                try {
                    paragraphRenderData.addText(String.valueOf(o));
                    cellRenderData.addParagraph(paragraphRenderData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            row.addCell(cellRenderData);
        }
        return row;
    }

}
