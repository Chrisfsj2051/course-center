<template>
  <div style="width: 100%">
  <el-container>
    <el-header>
      <h2>我的作业</h2></el-header>
    <el-main>
      <el-table :data="tableData" width="100%">
        <el-table-column prop="title" label="作业列表" width="550px" >
          <template slot-scope="scope">
            <el-button type="text" @click="showDetail(scope.row)">{{ scope.row.title }}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="release_time" label="发布时间" width="150px" aign="left">
        </el-table-column>
        <el-table-column prop="deadline" label="截至时间" width="150px" align="left">
        </el-table-column>
        <el-table-column prop="status" label="提交状态" width="80px" align="center">
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
  </div>
</template>

<style scoped>

  .el-aside {
    background-color:white;
    color: white;
  }
</style>

<script>
  import {reqMyHomeworkList} from '../../api'
  export default {
    data() {
      return {
        tableData: [],

      }
    },
    methods:{
      showDetail(row){
        this.$router.push({
          path: '/center/activities/homeworkInfo',
          query: {
            hw_id: row.homework_id
          }
        })
      },
    },
    async mounted() {
      const result= await reqMyHomeworkList(this.$store.state.id,this.$store.state.lessonid)

      this.tableData=result.my_homework_list
    }
  };
</script>
