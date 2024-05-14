<template >
  <div style="width: 100%;display: flex; flex-direction: column;height: 800px;">
    <div style="flex: 1 0 50%;">
      <div style="display: flex; justify-content: space-between; align-items: center;margin-bottom: 10px">
        <div style="display: flex; align-items: center;">
          <input type="text" v-model="newClass" class="input-class" style="margin-left: 20px;margin-top: 10px;">
          <el-button @click="addClass" color="#ffd04b" style="font-size: 24px;height: auto; border-radius: 30px;margin-left: 20px;margin-top: 10px;">添加新类别</el-button>
        </div>
        <div style="display: flex; align-items: center;">
          <text style="color: white; font-family: Arial, sans-serif; padding: 5px;margin-top: 10px;font-size: 24px;">模型：</text>
          <select v-model="model" style="margin-left: 10px;margin-top: 10px;font-size: 24px;">
            <option disabled value="">Please select an model</option>
            <option>Conv4</option>
            <option>Conv6</option>
            <option>ResNet10</option>
            <option>ResNet101</option>
          </select>
          <text style="font-size: 24px;margin-left: 10px; color: white; font-family: Arial, sans-serif; padding: 5px;margin-top: 10px;">算法：</text>
          <select v-model="method" style="font-size: 24px;margin-left: 10px;margin-top: 10px;margin-right: 10px">
            <option disabled value="">Please select an method</option>
            <option>baseline</option>
            <option>baseline++</option>
          </select>
        </div>
      </div>
      <hr style="width: 100%">
      <div style="display: flex; align-items: flex-start; justify-content: flex-start;">
        <div v-for="(item, index) in classes" :key="index" style="margin-right: 10px;">
          <div style="color: white;font-size: 24px; text-align: center; padding: 10px;">{{ item.name }}：</div>
          <el-upload
              class="upload-demo"
              drag
              action="http://localhost:9090/file/upload"
              :on-success="handleAvatarSuccess"
              multiple
              accept=".jpg,.jpeg,.png,.JPG,.JPEG"
              :data="{ Index:index, ClassName: item.name, UserId:getUserId()}"
          >
            <el-icon class="el-icon--upload"><Plus/></el-icon>
            <div class="el-upload__text">
              拖到这里 /<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip" style="color: white">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </div>
      </div>
    </div>
    <div class="container">
      <hr style="width: 50%">
      <div class="result">
        <p class="result-text">请上传一张测试图片：</p>
      </div>
      <el-upload
          style = "margin-top: 20px;"
          class="upload-myclass"
          drag
          action="http://localhost:9090/file/upload_test"
          :on-success="handleSuccess"
          multiple
          accept=".jpg,.jpeg,.png,.JPG,.JPEG"
          :data="{ UserId:getUserId()}"
          list-type="text"
      >
        <el-icon class="el-icon--upload"><Plus/></el-icon>
        <div class="el-upload__text">
          拖到这里 /<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip" style="color: white">
            jpg/png files with a size less than 500kb
          </div>
        </template>
      </el-upload>
    </div>
    <!--  <div class="bg"></div>-->
    <div style="display: flex; justify-content: center; align-items: center;flex-direction: column;margin-top: -30px">
      <el-button v-show="!loading&&!over" @click="recognize" style="height: 40px; border-radius: 20px; width: 100px; background-color: #ffd04b;">识别</el-button>
      <div class="result">
        <div v-show="over" class="download-container">
          <p  class="result-text">点击下载分类结果：</p>
          <button @click="downloadFile" class="download-button" >下载</button>
        </div>
<!--        <p v-show="!loading" class="result-text"></p>-->
        <p v-show="loading" class="result-text">加载中...</p>
      </div>
    </div>
  </div>
</template>


<script>

import {Plus} from '@element-plus/icons-vue';
import request from "@/utils/request";
import { filesaver } from 'file-saver';
import axios from "axios";

export default {
  name: "recognition",
  components: {Plus},
  data() {
    return {
      test_Counts:0,
      over: false,
      imageCounts: [],
      classes: [],
      newClass: '',
      url: "",
      path:'',
      deskpath:'',
      word:[],
      number:1,
      downloadTxtButtonFlag:0,
      iftest:false,
      ifsupport:false,
      model: 'Conv4',
      method: 'baseline++',
      result:"",
      loading:false,
    }
  },
  // created() {
  //   window.addEventListener('unload', this.cleanup)
  // },
  // beforeDestroy() {
  //   window.removeEventListener('unload', this.cleanup)
  // },
  methods:{
    // downloadTxt() {
    //   let str = this.word;
    //   let strData = new Blob([str], { type: 'text/plain;charset=utf-8' });
    //   saveAs(strData, "翻译文件.txt");
    // },
    addClass() {
      console.log(this.newClass[0])
      if (this.newClass !== ''&& this.newClass[0] !== '_') {
        this.classes.push({
          name: this.newClass
        });
        this.imageCounts.push(1);
        this.newClass = '';
      }
      else if(this.newClass === ''){
        this.$message({
          type: "error",
          message: "新类别名称不能为空！"
        })
      }
      else if(this.newClass[0] === '_'){
        this.$message({
          type: "error",
          message: "新类别名称不能以'_'开头！"
        })
      }
    },
    async downloadFile() {
      try {
        const response = await axios.get('/file/download', {
          params: { filename: sessionStorage.getItem("currentId") },
          responseType: 'blob'
        });
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'result.zip');
        document.body.appendChild(link);
        link.click();
        request.get("/user/getScore", {
          params: {
            UserId:sessionStorage.getItem("currentId"),
          }}).then(res => {
          sessionStorage.setItem("currentScore", res.data.score)
          this.currentScore = sessionStorage.getItem("currentScore");
        })
      } catch (error) {
        if(error.response.headers['x-description'] === "积分不足！"){
          alert('积分不足！');
        }
        alert('文件下载失败');
      }
    },
    recognize() {
      // this.result = "";
      if(this.ifsupport&&this.iftest){
        this.loading = true

        request.get("/file/recognize", {
          params: {
            UserId:sessionStorage.getItem("currentId"),
            model:this.model,
            method:this.method
          }}).then(res => {
          this.loading = false;
          this.over = true;
        })
      }
      else{
        this.$message({
          type: "error",
          message: "请提交支撑集和测试集"
        })
      }
    },
    // getImageIndex(index) {
    //   // 返回当前类别已上传的图片数量
    //   // console.log('当前文件的 index:', index);
    //   console.log('当前文件的 imageCounts[index]:', this.imageCounts[index]);
    //   return this.imageCounts[index]
    // },
    // beforeAvatarUpload(file, index) {
    //   console.log('before的 imageCounts[index]:', this.imageCounts[index]);
    //   this.imageCounts[index]++;
    //   return this.imageCounts[index]
    // },
    getUserId() {
      // 返回当前类别已上传的图片数量
      return sessionStorage.getItem("currentId")
    },
    // gettestCount() {
    //   // 返回当前类别已上传的图片数量
    //   this.test_Counts++
    //   return this.test_Counts
    // },
    handleAvatarSuccess(res)
    {
      this.ifsupport = true
      console.log(res)
      // this.imageCounts[res]++
    },
    handleSuccess()
    {
      this.iftest = true
    },
    // cleanup() {
    //   request.get("/file/delete", {
    //     params: {
    //       UserId:sessionStorage.getItem("currentId")
    //     }}).then(res => {
    //     console.log(res)
    //   })
    // },
  }
}
</script>
<style>
.upload-demo .el-upload-list .el-upload-list__text {
  color: white;
}
.container {
  flex: 1 0 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /*height: 100vh; !* 让容器占据整个视窗高度 *!*/
}
.result {
  font-size: 24px;
  color: #fff;
}
.download-button {
  margin-top:20px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  align-self: center; /* 垂直方向上自身居中 */
}

.result-text {
  margin-top: 20px;
}
/*.container {*/
/*  flex:1;*/
/*  display: flex;*/
/*  flex-direction: column;*/
/*  align-items: center;*/
/*}*/

hr {
  width: 50%;
}

el-upload {
  margin-top: 10px;
}

select option[disabled] {
  color: #999; /*改变字体颜色*/
}
.download-container {
  display: flex;
  align-items: center; /* 垂直居中 */
}

select {
  border: 1px solid #ccc; /*添加边框*/
  padding: 6px 10px; /*添加内边距*/
  font-size: 16px; /*改变字体大小*/
  color: #333; /*改变字体颜色*/
  border-radius: 4px; /*添加圆角*/
}
.upcard{
  align-content: center;
  background-color: wheat;
  width: 85%;
  min-height: 550px;
  height: 100%;
  opacity: 0.8;
  border-radius: 30px;
  margin: 50px 0 60px 60px;


  /* 为其整体设置接近透明的效果*/
  background-color: #999999;
  /* 设置box-shadow使其有立体感 */
  /*box-shadow: 5px 5px 0 0 rgba(0, 0, 0, 1);*/

  padding: 0 50px;
}
.input-class {
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  transition: box-shadow 0.2s;
}

.input-class:focus {
  outline: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2), 0 0 0 2px rgba(0, 0, 0, 0.1);
}
.upload-myclass {
  max-width: 500px;
  max-height: 250px; /* 控制预览区域的最大高度 */
  overflow-y: auto; /* 启用垂直滚动条 */
}
/*.index-b {*/
/*  background-color: darkgrey;*/
/*  width: 100%;*/
/*  height: 100%;*/
/*  background: url(../assets/123.jpg) no-repeat;*/
/*  !*background:*!*/
/*  !*    linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%),*!*/
/*  !*    linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%);*!*/
/*  !*background-blend-mode: multiply;*!*/
/*  !*color: #8c939d;*!*/
/*  position: absolute;*/
/*  top: 0;*/
/*  left: 0;*/
/*  z-index: -1;*/
/*  background-size: 100% 100%;*/
/*  min-width: 1000px;*/
/*}*/
/*.bg {*/
/*  width: 100%;*/
/*  height: 100%;*/
/*  color: #999999;*/
/*  position: absolute;*/
/*  margin-top: 58px;*/
/*  top: 0;*/
/*  left: 0;*/
/*  z-index: -1;*/
/*  background-size: 100% 100%;*/
/*  min-width: 1000px;*/
/*}*/
</style>
