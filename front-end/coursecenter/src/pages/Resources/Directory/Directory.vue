<template>
  <div>
    <h2 style="display: inline">学生花名册</h2>
  <el-button id="downloadButton"type="primary"  @click="download()">下载花名册</el-button>
  <el-table
    :data="stuList"
    border
    stripe
    style="width: 100%">
    <el-table-column label="学号" >
      <template slot-scope="scope">
        <span>{{ scope.row.stuid }}</span>
      </template>
    </el-table-column>

    <el-table-column label="姓名">
      <template slot-scope="scope">
        <span >{{ scope.row.name }}</span>
      </template>
    </el-table-column>

    <el-table-column label="性别" width="50px">
      <template slot-scope="scope">
        <span >{{ scope.row.gender }}</span>
      </template>
    </el-table-column>

    <el-table-column label="学院" >
      <template slot-scope="scope">
        <span >{{ scope.row.college }}</span>
      </template>
    </el-table-column>

    <el-table-column label="专业" >
      <template slot-scope="scope">
        <span >{{ scope.row.major }}</span>
      </template>
    </el-table-column>

    <el-table-column label="班级" >
      <template slot-scope="scope">
        <span >{{ scope.row.class }}</span>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
  import {reqStudentList,
 reqxls} from '../../../api'
  export default {
    name :'directory',
    data() {
      return {
        file_path:'',
        stuList: []
      }
    },
    methods: {
        async download(){
          const result=await reqxls(this.$store.state.lessonid);
          this.file_path=result.file_path
          window.open(this.file_path)
        },
    },
   async mounted() {
      const result=await reqStudentList(this.$store.state.lessonid);
     this.stuList=result.stuList
     for(let temp in this.stuList){
       if(this.stuList[temp].gender===0)
         this.stuList[temp].gender="男";
       else
         this.stuList[temp].gender="女"
     };
    }
  }
</script>
<style>
  @import "../../../assets/style/style.css";
</style>
