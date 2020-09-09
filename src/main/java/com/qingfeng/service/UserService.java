package com.qingfeng.service;

import com.qingfeng.mapper.UserMapper;
import com.qingfeng.pojo.Result;
import com.qingfeng.pojo.User;
import com.qingfeng.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Workbook exportUser() {
        //1，创建了一个空的excel文件
        Workbook workbook = new HSSFWorkbook();
        //2,填充数据：创建sheet
        Sheet sheet = workbook.createSheet("某某公司的员工信息");
        //标题数组
        String titles[] ={"用户id","用户名","密码","电话","创建时间"};
        //数据库表的字段
        String colums[] ={"id", "username", "password", "phone","create_time"};
        List<Map<String, Object>> maps = userMapper.exportUser();

        // 创建第一行
        Row rowTile = sheet.createRow(0);

        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<colums.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建两种单元格格式
        CellStyle cs = workbook.createCellStyle();
        CellStyle cs2 = workbook.createCellStyle();

        // 创建两种字体
        Font f = workbook.createFont();
        Font f2 = workbook.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);


        //标题行,设置列名
        for (int i = 0; i <titles.length ; i++) {
            Cell cell = rowTile.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(cs);
        }
        //遍历数据填充到单元格
        for (int i = 0; i <maps.size() ; i++) {
            //一条记录应该创建一个Row对象 这里从第二行开始所以+1
            Row row = sheet.createRow(i+1);//这个是空的，需要填充数据
            //填充单元格
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                //获取用户id的值
                Map<String, Object> rowValue = maps.get(i);
                //循环动态设置多个字段的值
                Object o = rowValue.get(colums[j]);//这里获取的值可以是"userId"..
                //这里也就是为什么查询数据库使用map封装的原因。
                cell.setCellValue(o+"");
                cell.setCellStyle(cs2);
            }
        }
        return workbook;
    }

    public  List<Map<String,Object>> getUser(){
      return   userMapper.exportUser();
    }


    @Transactional
    public Result importUser(List<User> userList) {
        for (User user:userList) {
            int count = userMapper.save(user);
            if (count<1){
                return  new Result(-1,"插入数据失败");
            }
        }
        return new Result(200,"插入数据成功");
    }

    /**
     * 获取IO流中的数据，组装成List<User>对象
     * @param in  IO流中的数据
     * @param fileName 文件名称
     */
    public  List<User> readExport(InputStream in, String fileName) throws Exception {

        List<User> listUser = new ArrayList<User>();
        //1、获取Excel工作薄
        Workbook work = WorkbookFactory.create(in);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        //2、获取工作表
        Sheet sheet = work.getSheetAt(0);
        //工作表中的数据第一行，是不要的
        //获取最后一行的索引
        int lastRowNum = sheet.getLastRowNum();
        //遍历当前sheet中的所有行
        for (int i = 1; i <= lastRowNum; i++) {
            //通过索引的方式获取每一行
            Row row = sheet.getRow(i);
            if (row == null || row.getFirstCellNum() == i) {
                continue;
            }
            List<String> list = new ArrayList<String>();
            //遍历当前sheet中的一行中的所有的列，也就是一行中的每个单元格
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                Cell cell = row.getCell(y);
                if (cell == null) {
                    //如果为空，这个单元为空字符串
                    list.add("");
                } else {
                    //在获取的时候，设置文本的格式为字符串类型的
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    //获取单元格的值
                    String cellValue = cell.getStringCellValue();
                    //将单元格的值添加到list里面
                    list.add(cellValue);
                }
            }
            if(list.size()>0) {
                //将每一行的的数据封装到User对象中
                //获取list的里面的时间list.get(4)就是2020-09-09 16:28:28
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(list.get(4));
                User user = new User(Integer.parseInt(list.get(0)), list.get(1), list.get(2), list.get(3),time);
                listUser.add(user);
            }
        }
        return listUser ;

    }


}
