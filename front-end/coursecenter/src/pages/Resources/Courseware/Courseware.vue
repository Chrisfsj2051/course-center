<template>
  <div>
    <el-dialog  :visible.sync="dialogVisible" width="30%" :before-close="closeDialog">
      <h3>课件上传</h3>

      <form id="file">
        <input style="width: 100%" class="el-button"  type="file" v-on:change="getFile($event)" >
        <el-button  type="success" id="submit" @click="submit($event)">提交</el-button>
      </form>
    </el-dialog>
    <el-container>
      <el-main>
    <h2 style="display: inline">课件下载</h2><el-button style="float: right" type="success" @click=" openDialog" v-if="isTeacher">上传课件</el-button>
    <el-table
      stripe
      :data="FileList"
      style="width: 100%">
      <el-table-column width="200%" label="课件名" >
        <template slot-scope="scope">
          <span>{{ scope.row.file_name }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150%" label="上传日期">
        <template slot-scope="scope">
          <span >{{ scope.row. operate_time }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100%" label="上传人">
        <template slot-scope="scope">
          <span >{{ scope.row.by }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100%"label="下载次数" >
        <template slot-scope="scope">
          <span >{{ scope.row.down_times }}</span>
        </template>
      </el-table-column>
      <el-table-column width="300%" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            icon="el-icon-view"
            @click="preview(scope.row)">预览</el-button>
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-download" @click="download(scope.row)">下载</el-button>
          <el-button
            v-if="isTeacher"
            size="mini"
            type="danger"
            icon="el-icon-delete" @click="delete_file(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>
      </el-main>
    </el-container>
  </div>

</template>

<script>
  import {reqppt_List,log_download,delete_courseware} from "../../../api";


  export default {
    name :'directory',
    data() {
      return {
        FileList: [],
        isTeacher:false,
        dialogVisible:false
      }
    },
    methods: {
      async download(row) {
        window.open(row.file_address)
        await log_download(row.file_address, this.$store.state.id)
      },
      closeDialog(){
        this.dialogVisible=false;
        document.getElementById("file").reset()
      },

      preview(row) {
        window.open(row.preview_url)
      },
      getFile: function (event) {
        this.file = event.target.files[0];
      },
      openDialog(){
        this.dialogVisible=true;
      },
      submit: function (event) {
        //阻止元素发生默认的行为
        event.preventDefault();
        let formData = new FormData();
        formData.append("file", this.file)
        formData.append("uno", this.$store.state.id.toString())
        formData.append("cid", this.$store.state.lessonid)
        this.$message.success("上传成功！！")
        this.$axios.post('http://139.224.137.85:8080/course/upload_ppt', formData)
          .then(response => {
            this.$message.success("上传成功！！")

            setTimeout(async () => {
              const result = await reqppt_List(this.$store.state.lessonid)
              this.FileList = result.fileList
              this.dialogVisible=false;
              document.getElementById("file").reset()
            }, 100);
          })
          .catch(function (error) {
            this.$message.error("上传失败")
            this.dialogVisible=false;
          })
      },
      async delete_file(row) {
        this.$confirm('确认删除该课件吗，删除后将无法恢复, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const result = await delete_courseware(row.file_id)
          if (result.state_code === 0) {
            this.$message.success("删除成功")
            setTimeout(async () => {
              const result = await reqppt_List(this.$store.state.lessonid)
              this.FileList = result.fileList
            }, 100);
          } else {
            this.$message.error("删除失败")
          }

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }

    },

   async mounted() {
     const result=await reqppt_List(this.$store.state.lessonid)
     this.FileList=result.fileList
     if(this.$store.state.status!==0){
       this.isTeacher=true;
     }



    }
  }
</script>

<style scoped>
    .el-aside{
      border-left: 1px solid rgba(7, 17, 27, 0.1);

    }
   .download-link{
      text-decoration: none;
      color: white;
    }
  #filename{
    color:#F56C6C;
  }
  #submit{
   margin-top: 50px;
  }
</style>
