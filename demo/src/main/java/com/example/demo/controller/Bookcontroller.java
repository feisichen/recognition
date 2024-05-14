package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.mapper.BookMapper;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/book")

public class Bookcontroller {

    @Resource
    BookMapper bookMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getBookname,search);
        }
        Page<Book> BookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }

    @GetMapping("/findpdf")
    public Result<?> findpdf(@RequestParam String id){
        String path = bookMapper.findpdf(id);
        return Result.success(path);

    }

    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        if (bookMapper.isKeyRepeat(book.getId()).intValue() != 0) {
            return Result.error("-1", "ID已存在");
        }
        bookMapper.insert(book);
        return Result.success();
    }
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam String id) {
        Book book = bookMapper.selectById(id);
        String coverpath = book.getImagepath();
        String pdfpath = book.getPdfpath();
        String cover = "";
        String pdf = "";
        int i = 27;
        while(i < coverpath.length()){
            cover += coverpath.charAt(i);
            i++;
        }
        int j = 31;
        while(j < pdfpath.length()){
            pdf += pdfpath.charAt(j);
            j++;
        }
        System.out.println(pdf);
        FileSystemUtils.deleteRecursively(new File("E:/code/springboot+vue/image_recognition/vue/public/image/bookcover/" + cover));
        FileSystemUtils.deleteRecursively(new File("E:/code/springboot+vue/image_recognition/vue/public/pdfs/" + pdf));
        return Result.success(bookMapper.deleteById(id));
    }
    @GetMapping("/recognize")
    public boolean recognize(@RequestParam String path) {
        System.out.println(path);
        return true;
//        String APP_ID = "26840295";
//        String API_KEY = "HYZQNurMf0IUvBPxXVyTL7zs";
//        String SECRET_KEY = "GS1rfH9maih5wvG88xUTKXv6cekW5dQD";
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//// 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//
//        // 调用接口
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        String text = res.toString(2);
//        System.out.println(text);
//        String result = "";
//        Integer i = 0 ;
//        Integer line = 0 ;
//        while(text.charAt(i) != '['){
//            ++i;
//        }
//        while(text.charAt(i) != ']'){
//            if(text.charAt(i) != '{'){
//                ++i;
//            }
//            else{
//                i += 11;
//                while(text.charAt(i) != '"'){
//                    result += text.substring(i,i+1);
//                    ++i;
//                }
//                result += "\n";
//            }
//        }
////        return Result.success(res.toString());
//        return Result.success(result);
    }


}

