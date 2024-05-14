<template>
  <div>
    <el-row :gutter="20" style="margin-top:300px;  justify-content: center;">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>个人中心</span>
            </div>
            <div class="name-role">
              <span class="sender">用户名 : {{dataForm.UserName}}</span>
            </div>
            <div class="registe-info">
            </div>
            <el-divider></el-divider>
            <div class="personal-relation">
              <div class="relation-item1">手机号:  <div style="float: right; padding-right:20px;">{{dataForm.Telephone}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item1">性别:  <div style="float: right; padding-right:20px;">{{dataForm.Sex}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item1">地址:  <div style="float: right; padding-right:20px;">{{dataForm.Address}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item1">所属单位:  <div style="float: right; padding-right:20px;">{{dataForm.Institution}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item1">个人简介:  <div style="float: right; padding-right:20px;">{{dataForm.Info}}</div></div>
            </div>
            <div style="  display: flex;justify-content: center;margin-top: 10px;">
              <el-button @click="info" color="#ffd04b" style="height: 40px; border-radius: 30px;">修改信息</el-button>
            </div>
          </el-card>
        </div>
      </el-col>
<!--      <el-col :span="16">-->
<!--        <div class="grid-content bg-purple">-->
<!--          <el-card class="box-card">-->
<!--            <div slot="header" class="clearfix">-->
<!--              <span>基本资料</span>-->
<!--            </div>-->
<!--            <div>-->
<!--              <el-form label-width="80px" v-model="dataFrom" size="small" label-position="right">-->
<!--                <el-form-item label="用户昵称" prop="nickName">-->
<!--                  <el-input  auto-complete="off" v-model="dataForm.nickName"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="手机号" prop="phone">-->
<!--                  <el-input auto-complete="off" v-model="dataForm.phone"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="首页链接" prop="homeUrl">-->
<!--                  <el-input  maxlength="18" v-model="dataForm.homeUrl"></el-input>-->
<!--                </el-form-item>-->
<!--              </el-form>-->
<!--              <div slot="footer" class="dialog-footer">-->
<!--                <el-button size="mini" type="primary">提交</el-button>-->
<!--                <el-button size="mini" type="warning" >关闭</el-button>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-card>-->
<!--        </div>-->
<!--      </el-col>-->

    </el-row>
  </div>
</template>
<script>
import request from "@/utils/request";

export default {
  data(){
    return{
      dataForm:{
        UserName: '',
        Telephone: '',
        Institution:'',
        Sex:'',
        Address:'',
        Info: ''
      }
    }
  },
  created(){
    request.get("/user/get", {
      params: {
        UserId:sessionStorage.getItem("currentId"),
      }}).then(res => {
      this.dataForm.UserName = res.data.name;
      this.dataForm.Telephone = res.data.telephone;
      this.dataForm.Institution = res.data.institution;
      this.dataForm.Sex = res.data.sex;
      this.dataForm.Address = res.data.address;
      this.dataForm.Info = res.data.info;
    })
  },
  methods: {
    info(){
      // if (window.history.length <= 1) {
      //   this.$router.push({path:'/'})
      //   return false
      // } else {
      //   this.$router.go(-1)
      // }
      this.$router.push({path:'/info'})
    },
  }
}
</script>

<style>

.person-bg {
  width: 100%;
  height: 580px;
  background-color: #ffffff;
  position: absolute;
  margin-top: 58px;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 522px;
  min-width: 1000px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}

.name-role {
  font-size: 16px;
  padding: 5px;
  text-align:center;
}
.sender{
  text-align:center;
}
.registe-info{
  text-align: center;
  padding-top:10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%
}

.relation-item1 {
  padding: 12px;

}
.dialog-footer{
  padding-top:10px ;
  padding-left: 10%;
}

/*.el-row {*/
/*  margin-bottom: 20px;*/
/*  &:last-child {*/
/*    margin-bottom: 0;*/
/*  }*/
/*}*/
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.index-b {
  background-color: darkgrey;
  width: 100%;
  height: 100%;
  background: url(../assets/123.jpg) no-repeat;
  /*background:*/
  /*    linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%),*/
  /*    linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%);*/
  /*background-blend-mode: multiply;*/
  /*color: #8c939d;*/
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 100%;
  min-width: 100px;
}
</style>
