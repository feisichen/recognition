<template>
  <div class="bg"></div>

  <el-card class="loader">
    <div class="edit">
      <div class="edit-item">
        <span class="label">上传封面</span>
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            accept=".jpeg,.png,.jpg"
            :before-upload="beforeimgUpload"
        >
          <img v-if="form.imagepath" :src="form.imagepath" class="avatar"/>
          <el-icon v-else class="avatar-uploader-icon">
            <Plus/>
          </el-icon>
        </el-upload>
      </div>
      <div class="edit-item">
        <span class="label">ID</span>
        <div>
          <!--          //用v-model来绑定数据-->
          <el-input v-model="form.id" class="create-input" placeholder="请输入内容"></el-input>
        </div>
      </div>
      <div class="edit-item">
        <span class="label">书名</span>
        <div>
          <!--          //用v-model来绑定数据-->
          <el-input v-model="form.bookname" class="create-input" placeholder="请输入内容"></el-input>
        </div>
      </div>
<!--      <div class="edit-item">-->
<!--        <span class="label">上传者</span>-->
<!--        <div>-->
<!--          &lt;!&ndash;          //用v-model来绑定数据&ndash;&gt;-->
<!--          <el-input v-model="form.uploader" class="create-input" placeholder="请输入内容"></el-input>-->
<!--        </div>-->
<!--      </div>-->
      <!--      <div class="edit-item">-->
      <!--        <span class="label">个人简介</span>-->
      <!--        <div>-->
      <!--          <el-input  v-model="sign"  type="textarea" class="create-input" placeholder="请输入内容"></el-input>-->
      <!--        </div>-->
      <!--      </div>-->
      <el-upload
          v-model:file-list="fileList"
          class="upload-demo"
          action="http://localhost:9090/file/uploadpdf"
          accept=".pdf"
          :before-upload="beforepdfUpload"
          :on-success="pdfhandleAvatarSuccess"
          :limit="1"
      >
        <el-button type="primary">Click to upload</el-button>
        <template #tip>
          <div class="el-upload__tip">
            pdf files with a size less than 2MB and just one.
          </div>
        </template>
      </el-upload>
      <div>
        <el-button class="send" type="primary" size="medium" @click="upload"
        >上传
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script >
import {Avatar, Lock} from '@element-plus/icons-vue';
import {Plus} from '@element-plus/icons-vue';
import request from "@/utils/request";


export default {
  name: "bookupload",

  data() {

    return {

      Avatar,
      Lock,
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
    beforeimgUpload(file) {
      const fileName = file.name
      const fileType = fileName.substring(fileName.lastIndexOf('.'))
      // jpeg,.png,.jpg,.bmp,.gif
      if (
          fileType === '.jpg' ||
          fileType === '.png' ||
          fileType === '.jpeg'
      ) {
        // 不处理
      } else {
        this.$message.error(
            '不是,jpeg,.png,.jpg文件,请上传正确的图片类型'
        )
        return false
      }
    },
    beforepdfUpload(file) {
      const fileName = file.name
      const fileType = fileName.substring(fileName.lastIndexOf('.'))
      // pdf
      if (
          fileType === '.pdf'

      ) {
        // 不处理
      } else {
        this.$message.error(
            '不是.pdf文件,请上传正确的文件类型'
        )
        return false
      }
    },
    upload() {
      if (sessionStorage.getItem("flag") == 'false') {
        if (this.form.bookname != null) {
          this.form.uploader=sessionStorage.getItem("currentName"),
          request.post("/book", this.form).then(res => {
            console.log(res);
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "上传成功"
              })
              this.$router.replace('/book')
            } else {
              this.$message({
                type: "warning",
                message: res.msg
              })
            }
          })
        } else {
          this.$message({
            type: "error",
            message: "书名不合法"
          })
        }
      } else {
        this.$message({
          type: "error",
          message: "请先登陆"
        })
      }
    },
    handleAvatarSuccess(res) {
      this.form.imagepath = res;
    },
    pdfhandleAvatarSuccess(res) {
      this.form.pdfpath = res;
    },


  }

}
</script>

<style>
.edit {
  padding: 80px 0 20px 20px
}

.edit-item {
  display: flex;
  margin-bottom: 20px
}

.label {
  margin-right: 10px
}

.avatar-uploader .avatar {
  width: 178px;
  height: 228px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.loader {
  background-color: wheat;
  width: 50%;
  height: 750px;
  opacity: 0.8;
  border-radius: 30px;
  margin: 100px 0 220px 360px;


  /* 为其整体设置接近透明的效果*/
  background-color: #ffffff;
  /* 设置box-shadow使其有立体感 */
  /*box-shadow: 5px 5px 0 0 rgba(0, 0, 0, 1);*/

  padding: 0 50px;
}

* {
  margin: 0;
}

/*背景图*/
.bg {
  width: 100%;
  height: 762px;
  background-color: #ffffff;
  position: absolute;
  margin-top: 58px;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 762px;
  min-width: 1000px;
}
</style>