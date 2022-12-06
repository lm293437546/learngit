package com.xws.bootpro.utils.export;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtil {

    /**
     * 导出类
     * @param response 响应
     * @param columnList 每列的标题名
     * @param dataList 导出的数据
     */
    public static void uploadExcelAboutUser(HttpServletResponse response,List<String> columnList,List<List<String>> dataList,String baseurl){
        String fileName = "fileName";
        //声明输出流
        OutputStream os = null;
        try {
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            //获取该工作区的第一个sheet
            Sheet sheet1 = wb.createSheet("sheet1");

            Map<Integer,Integer> widthMap = new HashMap<>();

            int excelRow = 0;
            //创建标题行
            Row titleRow = sheet1.createRow(excelRow++);
            for(int i = 0;i<columnList.size();i++){
                //创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));

                //内容自适应
                sheet1.setColumnWidth(i,getStrLength(columnList.get(i))*256);
                widthMap.put(i,getStrLength(columnList.get(i))*256);

                //设置格式
                getStyle(wb,cell);
            }
            //设置内容行
            if(dataList!=null && dataList.size()>0){
                //序号是从1开始的
                int count = 1;
                //外层for循环创建行
                for(int i = 0;i<dataList.size();i++){
                    Row dataRow = sheet1.createRow(excelRow++);
                    //内层for循环创建每行对应的列，并赋值
                    for(int j = -1;j<dataList.get(0).size();j++){//由于多了一列序号列所以内层循环从-1开始
                        Cell cell = dataRow.createCell(j+1);
                        if(j==-1){//第一列是序号列，不是在数据库中读取的数据，因此手动递增赋值
                            cell.setCellValue(count++);
                        }else{//其余列是数据列，将数据库中读取到的数据依次赋值
                            cell.setCellValue(dataList.get(i).get(j));
                            //内容自适应
                            Integer widths = getStrLength(dataList.get(i).get(j))*256;
                            if(widthMap.get(j+1) > widths ){
                                widths = widthMap.get(j+1);
                            }
                            sheet1.setColumnWidth(j+1,widths);
                        }
                        //设置格式
                        getStyle(wb,cell);
                    }
                }
            }

            //获取输出流
            os = response.getOutputStream();
            //下载
            response.reset();
            // 设置输出流
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));//对文件名编码,防止文件名乱码

            //添加响应头的跨域信息
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Origin", baseurl);
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");

            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //设置表格样式
    public static void getStyle(SXSSFWorkbook wb,Cell cell){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);//增加水平居中样式
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//增加垂直居中样式
        //excle边框样式添加
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);// 自动换行
        cell.setCellStyle(style);//设置单元格样式
    }

    /**
     * 获取正确字符的长度
     * @param str
     * @return
     */
    private static int getStrLength(String str){
        if(StringUtils.isEmpty(str)){
            return 0;
        }
        //中文匹配
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        int lengthPtn = 0;
        int lengthNOtPtn = 0;
        char[] array = str.toCharArray();

        for (int i = 0; i < array.length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(array[i]));
            if (matcher.matches()){
                lengthPtn++;
            }
        }
        lengthNOtPtn = array.length-lengthPtn;
        return lengthPtn*3+lengthNOtPtn*2;
    }


}
