<template>
  <div>

  <div class="page">
    <el-dialog title="发帖" :visible.sync="dialogVisible" width="50%">
      <h3>标题：</h3>
      <el-input type="text" placeholder="输入标题" v-model="title"  maxlength="30" show-word-limit ></el-input>
      <h3>帖子内容：</h3>
      <el-input   type="textarea" :rows="5" placeholder="输入帖子内容" v-model="content" maxlength="160" show-word-limit></el-input>
      <span slot="footer" class="dialog-footer">
       <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleEdit">确 定</el-button>
     </span>
    </el-dialog>
    <h2 style="display: inline">课程论坛</h2>
    <el-button v-if="!cannotPublish" class="send" type="primary" @click="publish()">发帖</el-button>
    <el-table :data="postList" stripe style="width: 100%">

      <el-table-column label="帖子标题">
        <template slot-scope="scope">
          <el-button type="text" @click="gotopost(scope.row.post_id)">{{ scope.row.title }}</el-button>

        </template>
      </el-table-column>

      <el-table-column label="发帖人">
        <template slot-scope="scope">
          <span >{{ scope.row.name }}</span>

        </template>
      </el-table-column>

      <el-table-column label="发帖日期">
        <template slot-scope="scope">
          <span >{{ scope.row.post_time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="最后回帖日期">
        <template slot-scope="scope">
          <span >{{ scope.row.last_reply_time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="回帖人次" >
        <template slot-scope="scope">
          <span >{{ scope.row. reply_num }}</span>
        </template>
      </el-table-column>


    </el-table>


  </div>
    <div class="paging">
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="currentPage"
        @current-change="handleCurrentChange"
        :total=tieNum>
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import {reqPostList,releasePost} from "../../../api";

  export default {
    name :'postList',
    data() {
      return {
        dialogVisible:false,
        currentPage:1, //初始页
        title:"",
        tieNum:100,
        content:"",
        cannotPublish:false,
        postList: [

        ]
      }
    },
    methods: {
      publish()
      {
        this.dialogVisible=true;
      },

      async handleEdit(){
        if(this.content.trim()===''||this.title.trim().length<5) {
          this.$message.error("标题或者内容均不能为空,且标题长度至少为5个字符")
        }
          else{
            const result=await releasePost(this.$store.state.id,this.title,this.content,1)/*1为课程论坛类型*/
          if(result.state_code===0){
            this.$message.success("发帖成功！！")
            setTimeout(async() =>{
              const result=await reqPostList(1,this.currentPage)
              this.postList=result.tieList;
              this.tieNum=result.tieNum
            },100);
          }
            this.dialogVisible=false;
          }

      },
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqPostList(1,this.currentPage)/*type 1为论坛，0为课程答疑*/
        this.postList=result.tieList;

      },
      gotopost(post_id){
        this.$router.push({
          path: '/center/forum/postdetail',
          query:{
            post_id:post_id
          }
        })
      }
    },
    async mounted() {
      const result=await reqPostList(1,this.currentPage)/*type 1为论坛，0为课程答疑*/
      if(result.state_code===0){
        this.postList=result.tieList;
        this.tieNum=result.tieNum
      }
      if(this.$store.state.status===-1){
        this.cannotPublish=true
      }
    }
  }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
</style>
