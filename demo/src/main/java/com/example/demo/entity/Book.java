package com.example.demo.entity;


        import com.baomidou.mybatisplus.annotation.IdType;
        import com.baomidou.mybatisplus.annotation.TableId;
        import com.baomidou.mybatisplus.annotation.TableName;
        import lombok.Data;

@TableName("book")
@Data
public class Book {
 @TableId(type = IdType.AUTO)
 private String id;
 private String bookname;
 private String imagepath;
 private String pdfpath;
 private String uploader;
}
