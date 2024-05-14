<template>
  <div class="bg"></div>
  <div style="width: 100%">
    <div class="Bookinput">
      <el-input v-model="search"
                placeholder="请输入书名"
                clearable/>
      <el-button style="margin-left: 30px;border-radius:30px;" @click="load">搜索</el-button>
    </div>
    <el-row class="Book">
      <el-col
          v-for="(id) in tableData"
          :key="id"
          :span="6"
      >
        <el-card :body-style="{ padding: '15px' }" style="margin: 5px;width: auto; ">
          <img
              :src="id.imagepath"
              class="image"
          />
          <div style="padding: 4px">
            <span >{{id.bookname}}</span>
            <div class="bottom">
              <div class="uploader">{{id.uploader}}</div>
              <el-button text class="button" @click="goToread(id.id)">阅读</el-button>
              <el-button v-if="type" text class="button" @click="todelete(id.id)">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
        style="margin:10px 10px 50px 200px ;"
        :currentPage="currentPage"
        :page-size="pageSize"
        :page-sizes="[8, 12, 16]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import request from "@/utils/request";
// import java.util.Base64;

export default {
  name:"book",
  components:{

  },
  data(){
    return{
      form: {},
      search:'',
      tableData: [],
      currentPage: 1,
      pageSize: 12,
      total: 0,
      user: "",
      type : false
    }
  },
  created() {
    this.load();
  },
  methods:{
    load() {
      this.user = sessionStorage.getItem("currentId")
      if (this.user === "admin"){
        this.type = true
      }
      request.get("/book", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          // selectDep: this.selectDep
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        console.log(this.tableData)
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    },
    todelete(id){
      request.get("/book/delete", {
        params: {
          id: id,
        }
      }).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功",
          })
          window.location.reload()
        } else {
          this.$message({
            type: "warning",
            message: res.msg
          })
        }
      })
    },
    goToread(id){
      sessionStorage.setItem("id", id);
      console.log(id);
      // 跳转路由
      this.$router.push("/read");
    },
  }
}

</script>


<style>
.uploader {
  font-size: 12px;
  color: #999;
}
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

.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.Book {
  margin: 50px 210px 100px 210px;
}

.Bookinput {
  margin: 30px 220px;
  width: 500px;
  display: flex;

}

.image {
  width: 100%;
  /*width: 228px;*/
  height: 278px;
  display: block;

}
</style>