package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class Usercontroller {

  @Resource
    UserMapper userMapper;
    @Value("D:/code/python/CloserLookFewShot-master/dataset/")
    private String fileUploadPath;
    @Value("D:/code/python/CloserLookFewShot-master/")
    private String projectPath;
  @PostMapping
  public Result<?> save(@RequestBody User user) {
//      if (userMapper.isKeyRepeat(user.getId()).intValue() != 0) {
//          return Result.error("-1", "用户名已存在");
//      }
//      if (user.getPasswd() == null) {
//          user.setPasswd(user.getId());
//      }
      userMapper.insert(user);
      return Result.success();
  }
    @GetMapping("/get")
    public Result<?> get(@RequestParam Integer UserId) throws IOException {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, UserId));
        return Result.success(res);
    }
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()).eq(User::getPasswd, user.getPasswd()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        Integer UserId = res.getId();
        File uploadFile = new File(fileUploadPath + UserId);
        if(uploadFile.exists()){
            deleteFolder(uploadFile);
        }
        File featureFile = new File(projectPath + "features/" + UserId);
        if(uploadFile.exists()){
            deleteFolder(featureFile);
        }
        return Result.success(res);
    }
    @PostMapping("/recharge")
    public Result<?> recharge(@RequestBody Integer UserId) {
        userMapper.recharge(UserId, 1000);
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, UserId));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(res);
    }
    @GetMapping("/commit")
    public Result<?> commit(Integer id, String telephone, String institution, String sex, String address, String info) {
        Integer res = userMapper.commit(id, sex, telephone, institution, info, address);
        return Result.success(res);
    }
    @GetMapping("/getScore")
    public Result<?> commit(Integer UserId) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, UserId));
        return Result.success(res);
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
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
}
