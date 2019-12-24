<template>
    <el-container id="app">
      <el-aside >
        <Sidebar></Sidebar>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
</template>

<script>
    import Sidebar from "../../components/Sidebar/Sidebar";
    export default {
        name: "resources",
      components: {Sidebar}
    }
</script>

<style scoped>
   .el-container{
     background: white;
     min-height:800px;
     height: 100%;
   }

</style>
