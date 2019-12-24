<template>
  <el-container id="app">

    <el-aside>

      <ActSidebar></ActSidebar>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>



<script>
  import ActSidebar from "../../components/ActSidebar/ActSidebar";
  export default {
    name: "activities",
    components: {ActSidebar}
  }
</script>

<style scoped>
  .el-container{
    background: white;
  min-height: 800px;
   height:100%;
  }
</style>
