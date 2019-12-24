<template>
  <div style="width: 100%">
  <el-container>
    <el-header>
      <h2>我的资料</h2></el-header>
    <el-main>
      <div v-if="!this.$store.state.status">
      <el-container>
        <el-aside width="240px">
          <img :src="student.pho_url" width="200px" style="padding-top: 20px">
          <div class="showInfoleft">
            <label>学号 ：</label>  {{student.no}}<br />
            <label>姓名 ：</label>{{ student.name}}<br />
          </div>
        </el-aside>
        <el-main>
          <div class="showInforight" v-if="!isManager">
            <label>用户类型 ：</label>{{student.user_type}}<br />
            <label>学院 ：</label>  {{student.college}}<br />
            <label>专业 ：</label>{{student.major}}<br />
            <label>性别 ：</label>  {{student.gender}}<br />
            <label>电话 ：  </label>{{student.phone}}<br />
            <label>班级 ：</label>{{student.classid}}<br />
          </div>
        </el-main>
      </el-container>
      </div>
      <div v-if="this.$store.state.status">
        <el-container>
          <el-aside width="240px">
            <img :src="student.pho_url" width="200px" style="padding-top: 50px">
            <div class="showInfoleft">

            </div>
          </el-aside>
          <el-main>
          <div class="showInforight">
            <label>用户类型 ：</label>{{student.user_type}}<br />
            <label>职工号 ：</label>  {{student.no}}<br />
            <label>姓名 ：</label>{{ student.name}}
            <div v-if="!isManager">
            <label >性别 ：</label>  {{student.gender}}<br />
            <label >学院 ：</label>  {{student.college}}<br />
            <label >联系电话 ：  </label>{{student.phone}}<br />
            <label>办公室地址 ：</label>  {{student.office_address}}<br />
            </div>
          </div>
    </el-main>
        </el-container>
      </div>
      <div style="padding-top: 30px">
        <el-button type="primary" icon="el-icon-edit" size="small" @click="editInfo">编辑</el-button>
      </div>
    </el-main>
  </el-container>
  </div>
</template>

<style scoped>

  .showInfoleft{
    text-align: left;
    line-height: 40px;
    color: #333333;
    padding-left: 50px;
    padding-top: 30px;
  }
  .showInforight{
    text-align: left;
    line-height: 40px;
    color: #333333;
    padding-left: 150px;
    padding-top: 10px;
  }
</style>

<script>
  import {reqPersonInfo,BASE_URL} from '../../api'
  export default {
    data() {
      return {
        student: {
        },
        isManager:null
      }
    },
    methods: {
      editInfo() {
        this.$router.push({path: '/center/personInfo/editInfo'})
      },
    },
    async mounted() {
      const result= await reqPersonInfo(this.$store.state.id)
      this.student=result
      if(this.$store.state.status===-1){
        this.isManager=true
      }
    },

  };
</script>

