package com.ssp.higher.base.utils.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:zouyu
 * @Date:2019/3/12 15:38
 */
public class ExcelUtils {

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	public static void writeExcel(List<Map> dataList, int cloumnCount, String finalXlsxPath) {
		OutputStream out = null;
		try {
			// 获取总列数
			int columnNumCount = cloumnCount;
			// 读取Excel文档
			File finalXlsxFile = new File(finalXlsxPath);
			Workbook workBook = getWorkbook(finalXlsxFile);
			// sheet 对应一个工作页
			Sheet sheet = workBook.getSheetAt(0);
			/**
			 * 删除原有数据，除了属性列
			 */
			int rowNumber = sheet.getLastRowNum(); // 第一行从0开始算
			System.out.println("原始数据总行数，除属性列：" + rowNumber);
			for (int i = 1; i <= rowNumber; i++) {
				Row row = sheet.getRow(i);
				sheet.removeRow(row);
			}
			// 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(finalXlsxPath);
			workBook.write(out);
			/**
			 * 往Excel中写新数据
			 */
			for (int j = 0; j < dataList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				Row row = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				Map dataMap = dataList.get(j);
				String name = dataMap.get("BankName").toString();
				String address = dataMap.get("Addr").toString();
				String phone = dataMap.get("Phone").toString();
				for (int k = 0; k <= columnNumCount; k++) {
					// 在一行内循环
					Cell first = row.createCell(0);
					first.setCellValue(name);

					Cell second = row.createCell(1);
					second.setCellValue(address);

					Cell third = row.createCell(2);
					third.setCellValue(phone);
				}
			}
			// 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(finalXlsxPath);
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取Workbook
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbook(File file) throws IOException {
		Workbook wb = null;
		FileInputStream in = new FileInputStream(file);
		if (file.getName().endsWith(EXCEL_XLS)) { // Excel&nbsp;2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

	/**
	 * 以String的形式读取xlsx文件
	 * 
	 * @param file
	 * @return
	 */
	public static List<List<String>> readExcel(File file) {
		try {

			List<List<String>> result = new ArrayList<>();
			// 创建输入流，读取Excel
			InputStream is = new FileInputStream(file.getAbsolutePath());
			// jxl提供的Workbook类
			XSSFWorkbook wb = new XSSFWorkbook(is);
			// Excel的页签数量
			int sheet_size = wb.getNumberOfSheets();
			for (int index = 0; index < sheet_size; index++) {
				// List<List> outerList=new ArrayList<List>();
				// 每个页签创建一个Sheet对象
				XSSFSheet sheet = wb.getSheetAt(index);
				int columnNum = 0;
				if (sheet.getRow(0) != null) {
					columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
				}
				// sheet.getRows()返回该页的总行数
				for (Row row : sheet) {
					List<String> tem = new ArrayList<>();
					for (int i = 0; i < columnNum; i++) {
						Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						// cell.setCellType(CellType.STRING);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String value = cell.getStringCellValue();
						tem.add(value);
					}
					result.add(tem);
				}
			}
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// Map<String, String> dataMap=new HashMap<String, String>();
		// dataMap.put("BankName", "BankName");
		// dataMap.put("Addr", "Addr");
		// dataMap.put("Phone", "Phone");
		// List<Map> list=new ArrayList<Map>();
		// list.add(dataMap);
		// writeExcel(list, 3, "D:/writeExcel.xlsx");
		File file = new File("D:/water.xlsx");
		List<List<String>> lists = readExcel(file);
		for (List list : lists) {
			System.out.println(list);
			System.out.println("====");
		}
	}
}
