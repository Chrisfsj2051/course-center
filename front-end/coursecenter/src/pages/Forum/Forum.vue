<template>
  <el-container id="app">

    <el-aside >
      <forumside></forumside>
    </el-aside>
    <el-main>

      <router-view></router-view>

    </el-main>
  </el-container>
</template>



<script>
  import ActSidebar from "../../components/ActSidebar/ActSidebar";
  import Forumside from "../../components/ForumSIde/ForumSide";
  export default {
    name: "activities",
    components: {Forumside, ActSidebar}
  }
</script>

<style scoped>
  .el-container{
    background: white;
    height: 100%;
    width: 100%;
    min-height: 800px;
  }

</style>
