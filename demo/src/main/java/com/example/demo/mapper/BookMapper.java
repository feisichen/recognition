package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface BookMapper extends BaseMapper<Book> {


    @Select("select id from book")
    List<String> findAllId();

    @Select("select count(*) from book where id = #{id}")
    Integer isKeyRepeat(String id);

    @Select("select pdfpath from book where id = #{id}")
    String findpdf(String id);

}