<template>
  <div>
    <el-row class="tac">
      <el-col :span="20">
        <h5>个人信息</h5>
        <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" >
          <el-menu-item index="/center/personInfo/myInfo">
            <template slot="title"><i class="el-icon-document"></i>我的资料</template>
          </el-menu-item>
          <el-menu-item index="/center/personInfo/myhomework" v-if="!this.$store.state.id">
            <template slot="title"><i class="el-icon-tickets"></i>我的作业</template>
          </el-menu-item>
          <el-menu-item index="/center/personInfo/download" v-if="!isManager">
            <template slot="title"><i class="el-icon-download"></i>文件记录</template>
          </el-menu-item>
          <el-menu-item index="/center/personInfo/editPassword">
            <template slot="title"><i class="el-icon-edit-outline"></i>修改密码</template>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "personside",
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

</style>
