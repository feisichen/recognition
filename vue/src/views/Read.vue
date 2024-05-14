<template>
  <iframe
      :src="'/pdf/web/viewer.html?file=' + source"
      class="pdf-viewer"
  />
</template>

<script>

import request from "@/utils/request";

export default {
  components: {
  },
  data() {
    return {
      id: sessionStorage.getItem("id"),
      source: "",
    };
  },
  created() {
    this.load();
  },


  methods: {
    load() {
      request.get("/book/findpdf", {
        params: {
          id: this.id,
          // selectDep: this.selectDep
        }
      }).then(res => {
        console.log("1111111111111111111111111111")
        this.source=res.data
        console.log(this.source)
      })
    },

  },
};
</script>

<style scoped>
.pdf-viewer{
  height: 700px;
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
.pdf-preview{
  position: center;
  height: 742px;
  padding: 20px 0;
  box-sizing: border-box;
  background-color: #e9e9e9;
}
.pdf-wrap{

  overflow-y:auto ;
}
.vue-pdf-embed {
  text-align: center;
  width: 515px;
  border: 1px solid #e5e5e5;
  margin: 0 auto;
  box-sizing: border-box;
}

.page-tool {
  position: absolute;
  bottom: 35px;
  padding-left: 15px;
  padding-right: 15px;
  display: flex;
  align-items: center;
  background: rgb(66, 66, 66);
  color: white;
  border-radius: 19px;
  z-index: 100;
  cursor: pointer;
  margin-left: 50%;
  transform: translateX(-50%);
}
.page-tool-item {
  padding: 8px 15px;
  padding-left: 10px;
  cursor: pointer;
}
.div {
  top: 0;
  position: fixed;
  z-index: 999;
}
.vue-pdf-embed {
  text-align: center;
}
</style>