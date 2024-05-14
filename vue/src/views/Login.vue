<template>
  <div class="index-bg"></div>
<!--  <el-button @click="back"-->
<!--             color="#ffd04b"-->
<!--             style="margin:13px 10px; height: auto; border-radius:30px;"-->
<!--          type="text"-->
<!--          font-color="#fff"-->
<!--  >返回</el-button>-->
  <el-button @click="back" color="#ffd04b" v-if="flag == true||flag == null" style="margin:13px 10px; height: 40px; border-radius:30px;width: 100px;font-size: 18px;">返回</el-button>
  <div style="overflow: hidden;">
    <el-form ref="form" :model="form" class="login" :rules="rules">
      <div style="color: darkslategrey; font-size: 30px; text-align: center; padding: 40px 0;">登录</div>
      <el-form-item prop="id">
        <el-input :prefix-icon="Avatar" placeholder="请输入ID/用户名" v-model="form.name"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="form.passwd" show-password/>
      </el-form-item>
      <!--      <div style="text-align: center;">-->
      <!--        <el-button style="width: 80%;" type="info" @click="login">登 录</el-button>-->
      <!--        <div style="height: 50px; background-color: deepskyblue; width: 80%; margin: 0 auto;"></div>-->
      <!--      </div>-->
      <el-button style="width: 100%;" type="info" @click="login">登 录</el-button>
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
      form: {},
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        passwd: [
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
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.name.length >= 3) { // 登录
            request.post("/user/login", this.form).then(res => {
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "登录成功"
                })
                // request.get("/department/forHeader", {
                //   params: {
                //     dep: res.data.department
                //   }
                // }).then(res2 => {
                //   sessionStorage.setItem("currentDepName", "你好，" + res2.data[0].departmentName + "，")
                // })
                sessionStorage.setItem("currentId", res.data.id)
                sessionStorage.setItem("currentName", res.data.name)
                sessionStorage.setItem("currentScore", res.data.score)
                sessionStorage.setItem("flag", 'false')


                this.$router.push("/home"); // 登录成功之后进行页面跳转，跳转到主页
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else {
            this.$message({
              type: "error",
              message: "用户名不合法"
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
.login {
  position: absolute;
  top: 25%;
  left: 32%;
  background-color: wheat;
  width: 30%;
  height: 300px;
  opacity: 0.8;
  border-radius: 30px;
  /*margin: 220px 300px 220px 500px;*/


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
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 100%;
  min-width: 1000px;
}
</style>