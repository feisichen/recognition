package com.example.demo.controller;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.*;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Filecontroller {
    @javax.annotation.Resource
    UserMapper userMapper;
    private final Object up_lock = new Object();
    private final Object uptest_lock = new Object();
    @Value("D:/code/python/CloserLookFewShot-master/dataset/")
    private String fileUploadPath;
    @Value("D:/code/python/CloserLookFewShot-master/")
    private String projectPath;

    @PostMapping("/upload")
    public Integer upload(@RequestParam MultipartFile file, Integer Index,Integer UserId, String ClassName) throws IOException {
        //先存储到磁盘
        File uploadpppFile = new File(fileUploadPath + UserId);
        if(!uploadpppFile.exists()){
            uploadpppFile.mkdirs();
        }
        File uploadppFile = new File(fileUploadPath + UserId + '/' + "images_origin");
        if(!uploadppFile.exists()){
            uploadppFile.mkdirs();
        }
        //先存储到磁盘
        File uploadpFile = new File(fileUploadPath + UserId + '/' + "images_origin/" + ClassName);
        if(!uploadpFile.exists()){
            uploadpFile.mkdirs();
        }
        synchronized (up_lock) {
        File[] files = uploadpFile.listFiles();

        // 统计文件数量
        int fileCount = 0;
        if (files != null) {
            for (File m_file : files) {
                if (m_file.isFile()) {
                    fileCount++;
                }
            }
        }
        File uploadFile = new File(fileUploadPath +  UserId + '/' + "images_origin/" + ClassName + '/' + ClassName + String.format("%03d", fileCount+1) + ".jpg");
        //存到磁盘
        file.transferTo(uploadFile);
        //存到数据库
        }
        return Index;
    }

    @PostMapping("/upload_test")
    public String upload_test(@RequestParam MultipartFile file ,Integer UserId) throws IOException {
        //先存储到磁盘
        File uploadpppFile = new File(fileUploadPath + UserId);
        if(!uploadpppFile.exists()){
            uploadpppFile.mkdirs();
        }
        File uploadppFile = new File(fileUploadPath + UserId + '/' + "images");
        if(!uploadppFile.exists()){
            uploadppFile.mkdirs();
        }
        File uploadpFile = new File(fileUploadPath +  UserId + '/' + "images/" + "_test");
        if(!uploadpFile.exists()){
            uploadpFile.mkdirs();
        }
        synchronized (uptest_lock) {
        File[] files = uploadpFile.listFiles();

        // 统计文件数量
        int fileCount = 0;
        if (files != null) {
            for (File m_file : files) {
                if (m_file.isFile()) {
                    fileCount++;
                }
            }
        }
        File uploadFile = new File(fileUploadPath +  UserId + '/' + "images/" + "_test" + '/' + "_test" + String.format("%03d", fileCount+1) + ".jpg");
        //存到磁盘
        file.transferTo(uploadFile);
        //存到数据库
        }
        return "success";
    }
    @GetMapping("/delete")
    public boolean delete(@RequestParam Integer UserId) throws IOException {
        //先存储到磁盘
        File uploadFile = new File(fileUploadPath + UserId);
        if(uploadFile.exists()){
            deleteFolder(uploadFile);
        }
        File featureFile = new File(projectPath + "features/" + UserId);
        if(uploadFile.exists()){
            deleteFolder(featureFile);
        }
        //存到数据库
        return true;
    }
    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) { // 如果是文件夹
            File[] files = folder.listFiles(); // 获取文件夹下的所有文件和子文件夹
            for (File file : files) {
                deleteFolder(file); // 递归删除子文件夹和文件
            }
        }
        folder.delete(); // 删除文件夹本身
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") Integer filename) {
        try {
            Path filePath = Paths.get(fileUploadPath + filename + '/' + "result.zip");
            Resource resource = new UrlResource(filePath.toUri());
            User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, filename));
            if (resource.exists()) {
                Integer length = 0;
                try {
                    length = (int) Files.size(filePath) / 1000;
                }
                catch (IOException e) {
                }
                if(length >= res.getScore()){
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Description", "积分不足！");
                    return ResponseEntity
                            .badRequest()
                            .headers(headers)
                            .build();
                }
                userMapper.recharge(filename, -length);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
//    @GetMapping("/mkcls")
//    public String mkcls(@RequestParam Integer UserId,String newclass) throws IOException {
//        File uploadppFile = new File(fileUploadPath + UserId);
//        if(!uploadppFile.exists()){
//            uploadppFile.mkdirs();
//        }
//        File uploadpFile = new File(fileUploadPath + UserId + '/' + "images");
//        if(!uploadpFile.exists()){
//            uploadpFile.mkdirs();
//        }
//        //先存储到磁盘
//        File uploadFile = new File(fileUploadPath + UserId + '/' + "images/" + newclass);
//        if(!uploadFile.exists()){
//            uploadFile.mkdirs();
//        }
//        //存到数据库
//        return(newclass);
//    }

    @GetMapping("/recognize")
    public Result<?> recognize(@RequestParam Integer UserId, String model, String method) throws IOException {
        ProcessBuilder pretreatment = new ProcessBuilder("D:\\tools\\Anaconda3\\envs\\graduate\\python.exe", "D:\\code\\python\\CloserLookFewShot-master\\dataset\\pretreatment.py" , UserId.toString());
        pretreatment.directory(new File("D:\\code\\python\\CloserLookFewShot-master"));
        Process process0 = pretreatment.start();
        boolean exitCode = true;
        try {
            exitCode = process0.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ProcessBuilder getcsv = new ProcessBuilder("D:\\tools\\Anaconda3\\envs\\graduate\\python.exe", "D:\\code\\python\\CloserLookFewShot-master\\dataset\\getcsv.py" , UserId.toString());
        getcsv.directory(new File("D:\\code\\python\\CloserLookFewShot-master"));
        Process process1 = getcsv.start();
        exitCode = true;
        try {
            exitCode = process1.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ProcessBuilder writenovel = new ProcessBuilder("D:\\tools\\Anaconda3\\envs\\graduate\\python.exe", "D:\\code\\python\\CloserLookFewShot-master\\dataset\\writenovel.py" , UserId.toString());
        writenovel.directory(new File("D:\\code\\python\\CloserLookFewShot-master"));
        Process process2 = writenovel.start();
        try {
            exitCode = process2.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ProcessBuilder save_features = new ProcessBuilder("D:\\tools\\Anaconda3\\envs\\graduate\\python.exe", "D:\\code\\python\\CloserLookFewShot-master\\save_features.py" , UserId.toString(), model, method);
        save_features.directory(new File("D:\\code\\python\\CloserLookFewShot-master"));
        Process process3 = save_features.start();
        try {
            exitCode = process3.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ProcessBuilder classify = new ProcessBuilder("D:\\tools\\Anaconda3\\envs\\graduate\\python.exe", "D:\\code\\python\\CloserLookFewShot-master\\classify.py" , UserId.toString(), model, method);
        classify.directory(new File("D:\\code\\python\\CloserLookFewShot-master"));
        Process process4 = classify.start();
        try {
            exitCode = process4.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
//    @Value("E:/code/springboot+vue/image_recognition/vue/public/pdfs/")
//    private String pdfUploadPath;

//    @PostMapping("/uploadpdf")
//    public String uploadpdf(@RequestParam MultipartFile file) throws IOException {
//        String originalFilename = file.getOriginalFilename();
//        String type = FileUtil.extName(originalFilename);
//        long size = file.getSize();
//        //先存储到磁盘
//        File uploadParentFile = new File(pdfUploadPath);
//        if(!uploadParentFile.exists()){
//            uploadParentFile.mkdirs();
//        }
//        //定义唯一标识符
//        String uuid = IdUtil.fastSimpleUUID();
//        String Fileuuid = uuid + StrUtil.DOT +type;
//        File uploadFile = new File(pdfUploadPath + Fileuuid);
//        //存到磁盘
//        file.transferTo(uploadFile);
//        String url = "http://localhost:9090/file/pdf/" + Fileuuid;
//        //存到数据库
//        return(url);
//    }
//
////    @GetMapping("/{fileUUID}")
////    public void download(@PathVariable String fileUUID , HttpServletResponse response) throws IOException {
////        File uploadFile = new File(fileUploadPath + fileUUID);
////        //设置输出流格式
////        ServletOutputStream os = response.getOutputStream();
////        response.addHeader("Content-Disposition","attchment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
////        response.setContentType("application/octet-stream");
////
////        os.write(FileUtil.readBytes(uploadFile));
////        os.flush();
////        os.close();
////    }
//
//    @GetMapping("/pdf/{fileUUID}")
//    public void downloadpdf(@PathVariable String fileUUID , HttpServletResponse response) throws IOException {
//        File uploadFile = new File(pdfUploadPath + fileUUID);
//        //设置输出流格式
//        ServletOutputStream os = response.getOutputStream();
//        response.addHeader("Content-Disposition","attchment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
//        response.setContentType("application/octet-stream");
//
//        os.write(FileUtil.readBytes(uploadFile));
//        os.flush();
//        os.close();
//    }
}
