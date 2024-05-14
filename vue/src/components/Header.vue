<template>
  <el-menu
      router
      default-active="/home"
      class="el-menu-demo"
      mode="horizontal"
      background-color="rgba(255,255,255,0)"
      font-size="x-large"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleSelect"
      style="border:0!important;"
  >
    <div style="margin: auto"></div>
    <el-menu-item  style="margin-left:-50px;color:#ffd04b;font-size: 36px;">遥感图像小样本分类平台</el-menu-item>
    <el-menu-item index="/home" style="font-size: 24px;" >首页</el-menu-item>
<!--          <el-sub-menu index="2"  >-->
<!--            <template #title  >图书</template>-->
<!--            <el-menu-item index="book" style="color:black">书库</el-menu-item>-->
<!--            <el-menu-item index="bookupload" style="color:black">上传图书</el-menu-item>-->
<!--          </el-sub-menu>-->
<!--    <el-menu-item index="/book" style="font-size: large;">书库</el-menu-item>-->
    <el-menu-item :index="recIndex" style="font-size: 24px;" @click="recwarning">
      识别
    </el-menu-item>
<!--    <el-menu-item index="/AboutView">-->
<!--      资讯-->
<!--    </el-menu-item>-->
    <el-menu-item :index="infoIndex"  style="font-size: 24px;" @click="infowarning">
      个人中心
    </el-menu-item>
    <!--      <el-sub-menu index="2">-->
    <!--        <template #title >图书</template>-->
    <!--        <el-menu-item index="book">书库</el-menu-item>-->
    <!--        <el-menu-item index="bookupload">上传图书</el-menu-item>-->
    <!--      </el-sub-menu>-->
<!--    <el-menu-item index="/aboutview" >关于我们</el-menu-item>-->
    <div style="margin: auto"></div>
<!--      <el-input style="margin:13px 10px 10px 80px;color:rgba(255,255,255,0)" v-model="search" placeholder="Please input" class="searchinput" />-->
<!--    <el-button-->
<!--        style="margin:13px 100px 10px 1px;border-radius:30px;"-->
<!--        type="text"-->
<!--        font-color="#fff"-->
<!--    >搜索</el-button>-->

<!--    <el-button style="margin:1px 10px;height: auto; " @click="search">搜索</el-button>-->
    <el-icon v-if="flag == false" style="margin-top: 1px;margin-left: 500px;height: auto;font-size: xx-large;color: #ffffff;">
      <User/>
    </el-icon>

    <el-dropdown :disabled="flag" style="margin: 18px 10px; height: auto;">
  <span class="el-dropdown-link" style="color: #fffffb;">
    <el-button
        style="height: 12px;font-size: 24px;margin-top: 3px"
        type="text"
        font-color="#fff"
    >{{ currentUser }}</el-button>
  </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="changePwd" style="font-size: 20px;">修改密码</el-dropdown-item>
          <el-dropdown-item @click="logout" style="font-size: 20px;">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <text v-if="flag === false" style="height: 12px;margin-top: 12px; font-size: 24px; color:#ffd04b; margin-left: 50px;">积分：{{ currentScore }}</text>
    <el-button v-if="flag === false" type="primary" @click="recharge" style=" margin-top: 12px;margin-left: 20px; width: 60px; font-size: 24px; background-color: #FFA500; color: #fffffb;">充值</el-button>





    <el-button @click="gotolinklogin" color="#ffd04b" v-if="flag == true||flag == null" style="margin:13px 10px; height: auto; border-radius:30px;font-size: 18px;">登录</el-button>
    <el-button @click="gotolinkregister" color="#ffd04b" v-if="flag == true||flag == null" style="margin:13px 10px; height: auto; border-radius:30px;font-size: 18px;">注册</el-button>
    <!--      <el-sub-menu index="8" style="margin-left:auto;">-->
    <!--        <template #title>-->
    <!--          <el-icon>-->
    <!--          <User/>-->
    <!--        </el-icon>-->
    <!--        </template>-->
    <!--        <el-menu-item index="2-3" style="background-color:#ffd04b">个人中心</el-menu-item>-->
    <!--        <el-menu-item index="2-1" style="background-color:#ffd04b">修改密码</el-menu-item>-->
    <!--        <el-menu-item index="2-2" style="background-color:#ffd04b">退出登陆</el-menu-item>-->
    <!--      </el-sub-menu>-->
    <div style="margin: auto"></div>

  </el-menu>

  <el-dialog v-model="dialogVisible" title="修改密码" width="30%">
    <el-form :model="form" label-width="120px">
<!--      <el-form-item label="ID">-->
<!--        <el-input v-model="form.id" style="width: 80%;" readonly/>-->
<!--      </el-form-item>-->
      <el-form-item label="昵称">
        <el-input v-model="form.name" style="width: 80%;" readonly/>
      </el-form-item>
      <el-form-item label="输入密码">
        <el-input v-model="form.password" style="width: 80%;" type="password"/>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.confirm" style="width: 80%;" type="password"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>


<script>
import {ArrowDown} from '@element-plus/icons-vue'
//图标而已
import {User} from '@element-plus/icons-vue'
import {Search} from '@element-plus/icons-vue'

import request from "@/utils/request";

export default {
  name: "Header",
  components: {
    ArrowDown,
    User,
    Search
  },
  data() {
    return {
      currentUser: sessionStorage.getItem("currentName"),
      departmentName: sessionStorage.getItem("currentDepName"),
      flag:JSON.parse(sessionStorage.getItem("flag")),
      dialogVisible: false,
      form: {},
      search:'',
      recIndex: '/recognition',
      infoIndex: '/person',
      currentScore:sessionStorage.getItem("currentScore")
    }
  },
  created(){
    if (sessionStorage.getItem('currentId') == null||sessionStorage.getItem('currentId') == "null") {
      this.recIndex = '/login';
      this.infoIndex = '/login'
    }
  },
  methods: {
    recwarning(){
      if (sessionStorage.getItem('currentId') == null||sessionStorage.getItem('currentId') == "null") {
        this.recIndex = '/login'
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
      else{
        this.recIndex = '/recognition'
      }
    },
    recharge(){
      request.post("/user/recharge", sessionStorage.getItem("currentId")).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "充值成功"
          })
        }
        sessionStorage.setItem("currentScore", res.data.score)
        this.currentScore = sessionStorage.getItem("currentScore");
      })
    },
    infowarning(){
      if (sessionStorage.getItem('currentId') == null||sessionStorage.getItem('currentId') == "null") {
        this.infoIndex = '/login'
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
      else{
        this.infoIndex = '/person'
      }
    },
    logout(){
      this.currentUser = '';
      this.flag = true;
      sessionStorage.setItem("flag", 'true')
      sessionStorage.setItem("currentName", '')
      sessionStorage.setItem("currentId", "null")
      this.menuIndex = '/login'
      this.$router.replace('/home')
    },
    gotolinklogin(){
      //指定跳转地址
      this.$router.replace('/login')
    },
    gotolinkregister(){
      //指定跳转地址
      this.$router.replace('/register')
    },
    changePwd() {
      this.form = {}
      this.form.id = sessionStorage.getItem("currentId");
      this.form.name = sessionStorage.getItem("currentName");
      this.dialogVisible = true;
    },
    update() {
      if (this.form.password != this.form.confirm) {
        this.$message({
          type: "error",
          message: "两次密码不一致，请重新输入"
        })
        this.form.password = ""
        this.form.confirm = ""
        return;
      }
        request.put("/user", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "修改成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>

.searchinput{
  width:170px;
  border-radius:30px;
}
.el-button--text {
  border-color: transparent;
  color:#fff;
  background: 0 0;
  padding-left: 0;
  padding-right: 0;
}

</style>