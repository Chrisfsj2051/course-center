<template>
  <div style="width: 100%">
    <el-container>
      <el-header>
        <h2>我的记录</h2></el-header>

      <el-main>
       <el-table :data="tableData" width="100%">
         <el-table-column prop="file_name" label="文件" >
         </el-table-column>
          <el-table-column prop="operate_type" label="操作类型"  width="150px">
         </el-table-column>
         <el-table-column prop="uploader" label="上传者" width="180px" align="center">
         </el-table-column>
         <el-table-column prop="operate_time" label="操作时间" width="200px" align="center">
         </el-table-column>
        </el-table>
      </el-main>
   </el-container>
  </div>
</template>

<<style scoped>


  .el-aside {
    background-color:white;
    color: white;
  }
</style>

<script>
  import {reqMyRecordList,BASE_URL} from '../../api'
  export default {
    data() {
      return {
        tableData: [],
      }
    },
    async mounted() {
      //alert(this.$store.state.id)
      const result= await reqMyRecordList(this.$store.state.id)
      this.tableData=result.myRecord

    }
  };
</script>

