<template>
  <div class="Header">
  <div id="nav">
    <el-menu router :default-active="$route.path" class="el-menu-demo" mode="horizontal"
             background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
      <el-menu-item v-if="!isManager" index="/center/index">首页</el-menu-item>
      <el-menu-item v-if="!isManager" index="/center/syllabus">课程信息</el-menu-item>
      <el-menu-item v-if="!isManager" index="/center/resources">教学资源</el-menu-item>
      <el-menu-item v-if="!isManager" index="/center/activities">教学活动</el-menu-item>
      <el-menu-item index="/center/forum">课程论坛</el-menu-item>
      <el-menu-item index="/center/personInfo">个人信息</el-menu-item>

    </el-menu>
    <router-view ></router-view>
  </div>
  </div>
</template>

<script>
    export default {
        name: "Nav",
      data(){
          return{
            isManager:false
          }
      },
      mounted() {
          if(this.$store.state.status===-1){
            this.isManager=true
          }
      }
    }
</script>

<style scoped>
  @import "../../assets/style/Header.css";
</style>
