package com.qingfeng.controller;

import com.qingfeng.pojo.Result;
import com.qingfeng.pojo.User;
import com.qingfeng.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

@Autowired
private UserService userService;



/*
* 输入地址测试http://localhost:9001/user/export
* */
    @GetMapping("/user/export")
    public void exportUser(HttpServletResponse response){
        Workbook workbook = userService.exportUser();
        try {
            //设置响应头
            response.setContentType("application/octet-stream");//所有文件都支持
            String fileName = "员工信息.xls";
            fileName = URLEncoder.encode(fileName,"utf-8");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            //文件下载
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * http://localhost:9001/upload.html
     * 导入
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/import/export")
    @ResponseBody
    public Result importExport(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        // 检查是否存在上传文件 > file.isEmpty()
        if (file.isEmpty()) {
            // 抛出异常：文件不允许为空
            return new Result(-1, "上传失败！没有选择上传的文件，或选中的文件为空！");
        }
        InputStream in = null;
        try {
            //读取文件内容的数据
            in = file.getInputStream();
        } catch (IOException e) {
            return new Result(-1, "读取数据失败!");
        }
        //获取文件名
        String filename = file.getOriginalFilename();
        try {
            //获取IO流中的数据，组装成List<User>对象
            List<User> userList = userService.readExport(in, filename);
            userService.importUser(userList);
            System.out.println("userList+"+userList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(200, "读取文件成功 ");
    }







}
