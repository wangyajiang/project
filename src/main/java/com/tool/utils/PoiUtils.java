package com.tool.utils;
/**
 * poi2003
 * @author wyj
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.tool.constant.EnumConfig;
import com.tool.constant.EnumConfig.S_CODE;
import com.tool.exception.ServiceException;


public class PoiUtils {

	@SuppressWarnings({"deprecation" })
	private static HSSFCellStyle createHeadStyle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();// 创建样式对象  
		HSSFFont font = workBook.createFont();// 创建字体对象  
        font.setFontHeightInPoints((short) 13);// 设置字体大小  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体  
        font.setFontName("宋体");// 设置为"宋体"  
        style.setFont(font);// 将字体加入到样式对象  
  
        // 设置对齐方式  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中  
        // 设置边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 顶部边框粗线  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 底部边框双线  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边边框  
  
     // 背景色
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.THIN_FORWARD_DIAG); 
        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index); 
		return style;  
	}
	
	@SuppressWarnings({"deprecation" })
	private static HSSFCellStyle createBodyStyle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();// 创建样式对象  
		HSSFFont font = workBook.createFont();// 创建字体对象  
        font.setFontHeightInPoints((short) 12);// 设置字体大小  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 设置粗体  
        font.setFontName("宋体");// 设置为"宋体"  
        style.setFont(font);// 将字体加入到样式对象  
  
        // 设置对齐方式  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中  
        // 设置边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 顶部边框粗线  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 底部边框双线  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边边框  
  
        // 背景色
		return style;  
	}
	
	public static void createExcel2003(HttpServletResponse response, String fileName, String[] heads, List<String[]> list, Integer[] columnWidths) throws IOException {
		fileName = fileName + ".xls";
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象  
        HSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象 
        HSSFRow headRow = sheet.createRow(0);// 创建一个行对象
        headRow.setHeightInPoints(20);
        for (int i = 0; i < heads.length; i ++) {
        	 HSSFCell cell = headRow.createCell(i);
        	 cell.setCellValue(heads[i]);
        	 cell.setCellStyle(createHeadStyle(workBook));
        }
        
        for (int i = 0; list != null && i < list.size(); i ++) {
        	HSSFRow row = sheet.createRow(i + 1);
        	String[] columns = list.get(i);
        	for (int j = 0; j < heads.length; j ++) {
        		HSSFCell cell = row.createCell(j);
        		cell.setCellValue(columns[j]);
        		cell.setCellStyle(createBodyStyle(workBook));
        	}
        }
        if (null != columnWidths && columnWidths.length > 0) {
        	for (int i = 0; i < columnWidths.length; i ++) {
        		sheet.setColumnWidth(i, columnWidths[i]);
        	}
        }
        
        // 文件输出流  
        ServletOutputStream sos = response.getOutputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		workBook.write(buffer);
		response.reset();
		response.setContentLength(buffer.size());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0");
        sos.write(buffer.toByteArray());
        buffer.flush();
        sos.flush();
        System.out.println("创建成功 office 2003 excel");  
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	public static List<List<Object>> read2003Excel(File file)  throws IOException { 
        List<List<Object>> list = new LinkedList<List<Object>>();  
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));  
        HSSFSheet sheet = hwb.getSheetAt(0);  
        Object value = null;  
        HSSFRow row = null;  
        HSSFCell cell = null;  
        System.out.println("读取office 2003");  
        for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {  
            row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            }  
            List<Object> linked = new LinkedList<Object>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {  
                cell = row.getCell(j);  
                if (cell == null) {  
                    continue;  
                }  
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String  
                // 字符  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串  
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字  
                switch (cell.getCellType()) {  
	                case HSSFCell.CELL_TYPE_STRING:  
	                    value = cell.getStringCellValue();  
	                    break;  
	                case HSSFCell.CELL_TYPE_NUMERIC:  
	                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {  
	                        value = df.format(cell.getNumericCellValue());  
	  
	                    } else if ("General".equals(cell.getCellStyle()  
	                            .getDataFormatString())) {  
	                        value = nf.format(cell.getNumericCellValue());  
	                    } else {  
	                        value = sdf.format(HSSFDateUtil.getJavaDate(cell  
	                                .getNumericCellValue()));  
	                    }  
	                    break;  
	                case HSSFCell.CELL_TYPE_BOOLEAN:  
	                    value = cell.getBooleanCellValue();  
	                    break;  
	                case HSSFCell.CELL_TYPE_BLANK:  
	                    value = "";  
	                    break;  
	                default:  
	                    value = cell.toString();  
                }  
                if (value == null || "".equals(value)) {  
                    continue;  
                }  
                linked.add(value);  
  
            }  
            list.add(linked);  
        }  
        return list;  
    }
	
	@SuppressWarnings({"unchecked", "rawtypes", "resource", "deprecation" })
	public static <T> List<T> readExcel2003ToObj(InputStream input, Class clazz, String[] columns, int startLine, String dateFormat) throws ServiceException, IOException, InstantiationException, IllegalAccessException { 
        List<T> list = new LinkedList<T>();  
        HSSFWorkbook hwb = new HSSFWorkbook(input);  
        HSSFSheet sheet = hwb.getSheetAt(0);  
        Object value = null;  
        HSSFRow row = null;  
        HSSFCell cell = null;  
        System.out.println("读取office 2003 并转化为实体" );  
        int rowNum = sheet.getPhysicalNumberOfRows();
        if (rowNum < startLine) {
        	throw new ServiceException(EnumConfig.S_CODE.VALIDATE_CODE_ERROR, "file format error, not less than " + startLine + " lines");
        }
        for (int i = startLine; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            }  
            T item = (T) clazz.newInstance();
            if (row.getLastCellNum() < columns.length) {
            	throw new ServiceException(S_CODE.VALIDATE_CODE_ERROR, "file format error, not less than " + columns.length + " rows");
            }
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {  
                cell = row.getCell(j);  
                if (cell == null) {  
                    continue;  
                }  
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String  
                // 字符  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串  
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字  
                switch (cell.getCellType()) {  
	                case HSSFCell.CELL_TYPE_STRING:  
	                    value = cell.getStringCellValue();
	                    CheckUtils.setObjField(item, columns[j], value, dateFormat);
	                    break;  
	                case HSSFCell.CELL_TYPE_NUMERIC:  
	                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {  
	                        value = df.format(cell.getNumericCellValue());  
	  
	                    } else if ("General".equals(cell.getCellStyle()  
	                            .getDataFormatString())) {  
	                        value = nf.format(cell.getNumericCellValue());  
	                    } else {  
	                        value = sdf.format(HSSFDateUtil.getJavaDate(cell  
	                                .getNumericCellValue()));  
	                    }
	                    CheckUtils.setObjField(item, columns[j], value, dateFormat);
	                    break;  
	                case HSSFCell.CELL_TYPE_BOOLEAN:  
	                    value = cell.getBooleanCellValue();  
	                    CheckUtils.setObjField(item, columns[j], value, dateFormat);
	                    break;  
	                case HSSFCell.CELL_TYPE_BLANK:  
	                    value = "";
	                    CheckUtils.setObjField(item, columns[j], value, dateFormat);
	                    break;  
	                default:  
	                    value = cell.toString();
	                    CheckUtils.setObjField(item, columns[j], value, dateFormat);
                }  
                if (value == null || "".equals(value)) {  
                    continue;  
                }  
  
            }  
            list.add(item);  
        }  
        return list;  
    }
	
	@SuppressWarnings({"resource", "deprecation" })
	public static List<List<Object>> readExcel2003ByIO(InputStream input, int startLine) throws ServiceException, IOException, InstantiationException, IllegalAccessException { 
		 List<List<Object>> list = new LinkedList<List<Object>>();  
        HSSFWorkbook hwb = new HSSFWorkbook(input);  
        HSSFSheet sheet = hwb.getSheetAt(0);  
        Object value = null;  
        HSSFRow row = null;  
        HSSFCell cell = null;  
        System.out.println("读取office 2003 并转化为实体" );  
        int rowNum = sheet.getPhysicalNumberOfRows();
        if (rowNum < startLine) {
        	throw new ServiceException(S_CODE.VALIDATE_CODE_ERROR, "file format error, not less than " + startLine + " lines");
        }
        for (int i = startLine; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            }  
            List<Object> linked = new LinkedList<Object>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {  
                cell = row.getCell(j);  
                if (cell == null) {  
                    continue;  
                }  
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String  
                // 字符  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串  
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字  
                switch (cell.getCellType()) {  
	                case HSSFCell.CELL_TYPE_STRING:  
	                    value = cell.getStringCellValue();  
	                    break;  
	                case HSSFCell.CELL_TYPE_NUMERIC:  
	                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {  
	                        value = df.format(cell.getNumericCellValue());  
	  
	                    } else if ("General".equals(cell.getCellStyle()  
	                            .getDataFormatString())) {  
	                        value = nf.format(cell.getNumericCellValue());  
	                    } else {  
	                        value = sdf.format(HSSFDateUtil.getJavaDate(cell  
	                                .getNumericCellValue()));  
	                    }  
	                    break;  
	                case HSSFCell.CELL_TYPE_BOOLEAN:  
	                    value = cell.getBooleanCellValue();  
	                    break;  
	                case HSSFCell.CELL_TYPE_BLANK:  
	                    value = "";  
	                    break;  
	                default:  
	                    value = cell.toString();  
                }  
                if (value == null || "".equals(value)) {  
                    continue;  
                }  
                linked.add(value);  
  
            }  
            list.add(linked);  
        }  
        return list;  
    }
}
