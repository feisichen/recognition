<template>
  <div style="text-align: center;">
    <h1 style="color:#ffd04b; font-size: 80px; height: 60px;text-align: center; padding: 260px 0;letter-spacing: 15px;">欢迎使用遥感图像小样本分类平台</h1>
    <el-button  @click="gotolinkrecognize" color="" style="height:150px;width: 350px; border-radius:75px;font-size: 60px;margin-top: 50px">开始识别</el-button>
  </div>
</template>

<script>

export default {
  data() {

  },
  load(){

  },
  methods: {
    gotolinkrecognize() {
      //指定跳转地址
      if(sessionStorage.getItem('currentId') == null||sessionStorage.getItem('currentId') == "null") {
        this.$router.replace('/Login')
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
      else{
        this.$router.replace('/Recognition')
      }
    }
  }
}
</script>

<style>
</style>