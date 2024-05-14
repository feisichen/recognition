<template>
  <div class="index-bg"></div>
<!--  <el-button @click="back"-->
<!--             style="margin:13px 100px 10px 15px;color: #ebeef5"-->
<!--             type="text"-->
<!--             font-color="#fff"-->
<!--  >返回</el-button>-->
  <el-button @click="back" color="#ffd04b" v-if="flag == true||flag == null" style="margin:13px 10px; height: 40px; border-radius:30px;width: 100px;font-size: 18px;">返回</el-button>
  <div style="overflow: hidden;">
    <el-form ref="form" :model="form" class="register" :rules="rules">
      <div style="color: darkslategrey; font-size: 30px; text-align: center; padding: 40px 0;">注册</div>
      <el-form-item prop="name">
        <el-input :prefix-icon="Avatar" placeholder="用户名" v-model="form.name"/>
      </el-form-item>
      <el-form-item prop="passwd">
        <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="form.passwd" show-password/>
      </el-form-item>
      <el-form-item prop="passwd">
        <el-input :prefix-icon="Lock" placeholder="请再次输入密码" v-model="passwd2" show-password @blur="test"/>
      </el-form-item>
<!--      <el-form-item prop="name">-->
<!--        <el-input :prefix-icon="Avatar" placeholder="请输入昵称" v-model="form.name" />-->
<!--      </el-form-item>-->
<!--      <el-form-item   prop="sex">-->
<!--        <div>性别：</div>-->
<!--          <el-radio-group v-model="form.sex" >-->
<!--            <el-radio label="男" size="large">男</el-radio>-->
<!--            <el-radio label="女" size="large">女</el-radio>-->
<!--            <el-radio label="未知" size="large">未知</el-radio>-->
<!--          </el-radio-group>-->
<!--      </el-form-item>-->
      <!--      <div style="text-align: center;">-->
      <!--        <el-button style="width: 80%;" type="info" @click="login">登 录</el-button>-->
      <!--        <div style="height: 50px; background-color: deepskyblue; width: 80%; margin: 0 auto;"></div>-->
      <!--      </div>-->
      <el-button :disabled="dis" style="width: 100%;" type="info" @click="register">注册</el-button>
    </el-form>
  </div>
</template>

<script>
import {Avatar, Lock} from '@element-plus/icons-vue';
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      Avatar,
      Lock,
      passwd2: '',
      dis: true,
      form: {},
      rules: {
        id: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    back(){
      // if (window.history.length <= 1) {
      //   this.$router.push({path:'/'})
      //   return false
      // } else {
      //   this.$router.go(-1)
      // }
      this.$router.push({path:'/home'})
    },
    //判断两次密码是否一致
    test() {
      if (this.form.passwd !== this.passwd2) {
        this.$message({
          type: "error",
          message: "两次密码不同"
        })
        this.dis = true
      } else {
        this.dis = false
      }
    },
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.passwd === this.passwd2) {
              if(this.form.name.length >= 3) {// 登录
                request.post("/user", this.form).then(res => {
                  console.log(res);
                  if (res.code === '0') {
                    this.$message({
                      type: "success",
                      message: "注册成功"
                    })

                    this.$router.push("/home"); // 登录成功之后进行页面跳转，跳转到主页
                  } else {
                    this.$message({
                      type: "warning",
                      message: res.msg
                    })
                  }
                })
              }else{
                this.$message({
                  type: "error",
                  message: "用户名不合法,昵称不能为空"
                })
              }
          }else{
            this.$message({
              type: "error",
              message: "两次密码不同"
            })
          }
        }
      })
    }
  }
}
// 注意，登录后sessionShort存储情况如下：
// key=currentId,value=学号/工号
// key=currentName,value=姓名
// key=currentDep,value=院系号，管理员为0
// key=currentDepName,value="你好，xxx学院，某某"，管理员为空字符串
</script>

<style>
.register {
  position: absolute;
  top: 25%;
  left: 32%;
  /*transform: translate(-50%, -50%);*/
  background-color: wheat;
  width: 30%;
  height: 440px;
  /*opacity: 0.8;*/
  border-radius: 30px;
  /*margin: 160px 300px 180px 500px;*/


  /* 为其整体设置接近透明的效果*/
  background-color: rgba(255, 255, 255, 1);
  /* 设置box-shadow使其有立体感 */
  box-shadow: 5px 5px 0 0 rgba(0, 0, 0, 1);

  padding: 0 50px;
}

* {
  margin: 0;
}

/*背景图*/
.index-bg {
  width: 100%;
  height: 100%;
  background: url(../assets/123.jpg) no-repeat;
  /*background:*/
  /*    linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%),*/
  /*    linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%);*/
  /*background-blend-mode: multiply;*/
  color: #8c939d;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 100%;
  min-width: 1000px;
}
</style>