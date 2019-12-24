<template>
  <el-container id="app">

    <el-aside>

      <personside></personside>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>



<script>
  import Personside from "../../components/PersonSide/PersonSide";
  export default {
    name: "syllabus",
    components: {Personside}
  }
</script>

<style scoped>
  .el-container{
    background: white;
    height: 600px;
  }
</style>
