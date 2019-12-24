<template>
  <el-container id="app">

    <el-aside>

      <courseside></courseside>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>



<script>
  import Courseside from "../../components/CourseSide/CourseSide";
  export default {
    name: "syllabus",
    components: {Courseside}
  }
</script>

<style scoped>
  .el-container{
    background: white;
    height: 100%;
    min-height: 800px;
  }
</style>
