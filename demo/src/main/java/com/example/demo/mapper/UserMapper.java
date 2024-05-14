package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    @Update("update user set sex = #{sex}, telephone = #{telephone}, institution = #{institution}, info = #{info} ,address = #{address} where id = #{id}")
    Integer commit(Integer id, String sex, String telephone ,String institution, String info ,String address);
    @Update("update user set score = score + #{score} where id = #{id}")
    Integer recharge(Integer id, Integer score);
    @Select("select id from student")
    List<String> findAllId();

    @Select("select count(*) from user where id = #{id}")
    Integer isKeyRepeat(String id);

}