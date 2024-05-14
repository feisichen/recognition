<template>
  <div>
    <div class="index-bg"></div>
    <Header></Header>
    <div style="display: flex;">
      <router-view style="flex: 1;"/>
    </div>
  </div>

</template>

<script>
import Header from "@/components/Header";


export default {
  name: "Manage",
  components: {
    Header,

  }
}
</script>

<style scoped>
.index-bg {
  width: 100%;
  height: 100%;
  background: url(../assets/123.jpg) no-repeat;
  background-color: darkgrey;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: 100% 100%;
  min-width: 1000px;
  /*background:*/
  /*    radial-gradient(black 15%, transparent 16%) 0 0,*/
  /*    radial-gradient(black 15%, transparent 16%) 8px 8px,*/
  /*    radial-gradient(rgba(255,255,255,.1) 15%, transparent 20%) 0 1px,*/
  /*    radial-gradient(rgba(255,255,255,.1) 15%, transparent 20%) 8px 9px;*/
  /*background-color:#282828;*/
  /*background-size:16px 16px;*/
  /*background:*/
  /*    linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%),*/
  /*    linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%);*/
  /*background-blend-mode: multiply;*/
}
</style>