/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple utils for reading and writing excel.
 * <br>
 * We agree that the excel content should conform to <code>Map<String, List<Map<String, Object>>></code>.
 * Out map key is the sheet's name of excel, list value is the content. And inner map represent the row
 * of the sheet in excel, inner map key is the index column(0,1,2,3 ...), value represent the real value a cell
 *
 * @author lazycece
 * @date 2018/4/5
 */
public class ExcelUtils {

    /**
     * Read excel data from file path.
     *
     * @param excel excel file path
     * @return data
     * @throws IOException IOException
     */
    public static Map<String, List<Map<String, Object>>> read(String excel) throws IOException {
        int index = excel.lastIndexOf(".");
        String format = index < 0 ? "" : excel.substring(index + 1).toLowerCase();
        return read(new FileInputStream(excel), Format.valueOf(format));
    }

    /**
     * Read excel data from file path.
     *
     * @param excel  excel file path
     * @param format ${@link Format}
     * @return data
     * @throws IOException IOException
     */
    public static Map<String, List<Map<String, Object>>> read(String excel, Format format) throws IOException {
        return read(new FileInputStream(excel), format);
    }

    /**
     * Read excel data from file path.
     *
     * @param excel  excel URI
     * @param format ${@link Format}
     * @return data
     * @throws IOException IOException
     */
    public static Map<String, List<Map<String, Object>>> read(URL excel, Format format) throws IOException {
        return read(new BufferedInputStream(excel.openStream()), format);
    }

    /**
     * Read excel data from file path.
     *
     * @param inputStream excel stream
     * @param format      ${@link Format}
     * @return data
     * @throws IOException IOException
     */
    public static Map<String, List<Map<String, Object>>> read(InputStream inputStream, Format format) throws IOException {
        switch (format) {
            case xls:
                return read(new HSSFWorkbook(inputStream));
            case xlsx:
                return read(new SXSSFWorkbook(new XSSFWorkbook(inputStream)));
            default:
                throw new IllegalArgumentException("file's format error");
        }
    }

    private static Map<String, List<Map<String, Object>>> read(Workbook workbook) throws IOException {
        Map<String, List<Map<String, Object>>> mapExcel = new HashMap<>();
        // scan sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); ++numSheet) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            List<Map<String, Object>> listSheet = new ArrayList<>();
            //scan row
            for (int i = 0; i <= sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                // scan colum
                for (int col = 0; col <= row.getLastCellNum(); ++col) {
                    Cell cell = row.getCell(col);
                    if (cell == null) {
                        continue;
                    }
                    Object value = getValue(cell);
                    if (value == null) {
                        continue;
                    }
                    map.put(String.valueOf(col), value);
                }
                listSheet.add(map);
            }
            mapExcel.put(sheet.getSheetName(), listSheet);
        }
        return mapExcel;
    }

    private static Object getValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    /**
     * Write excel data.
     *
     * @param excel excel file path;
     * @param map   content
     * @throws IOException IOException
     */
    public static void writeXlsx(String excel, Map<String, List<Map<String, Object>>> map) throws IOException {
        File file = new File(excel);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        writeXlsx(new FileOutputStream(excel), map);
    }

    /**
     * Write excel data.
     *
     * @param outputStream outputStream
     * @param map          content
     * @throws IOException IOException
     */
    public static void writeXlsx(OutputStream outputStream, Map<String, List<Map<String, Object>>> map) throws IOException {
        Workbook workbook = writeXlsx(map);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    private static Workbook writeXlsx(Map<String, List<Map<String, Object>>> map) {
        Workbook workbook = new XSSFWorkbook();
        for (Map.Entry<String, List<Map<String, Object>>> sheetEntry : map.entrySet()) {
            Sheet sheet = workbook.createSheet(sheetEntry.getKey());
            int index = 0;
            for (Map<String, Object> rowData : sheetEntry.getValue()) {
                Row row = sheet.createRow(index);
                for (Map.Entry<String, Object> cellEntry : rowData.entrySet()) {
                    Cell cell = row.createCell(Integer.valueOf(cellEntry.getKey()));
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    Font font = workbook.createFont();
                    font.setFontHeightInPoints((short) 14);
                    font.setFontName("宋体");
                    cellStyle.setFont(font);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(String.valueOf(cellEntry.getValue()));
                }
                ++index;
            }
        }
        return workbook;
    }

    public enum Format {
        xls, xlsx
    }
}
