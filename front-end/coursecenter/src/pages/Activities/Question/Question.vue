<template>
  <div>

    <div class="page">

      <h2 style="display: inline">课程答疑</h2>
      <el-button class="send" type="primary"  @click="publish()">发帖</el-button>
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
      <el-dialog title="发表问题" :visible.sync="dialogVisible" width="50%">
        <h3>标题：</h3>
        <el-input type="text" placeholder="输入标题" v-model="title"  maxlength="30" show-word-limit ></el-input>
        <h3>帖子内容：</h3>
        <el-input   type="textarea" :rows="5" placeholder="输入帖子内容" v-model="content" maxlength="160" show-word-limit></el-input>
        <span slot="footer" class="dialog-footer">
       <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleEdit">确 定</el-button>
     </span>
      </el-dialog>

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
    name :'question',
    data() {
      return {
        currentPage:1, //初始页
        tieNum:null,
        dialogVisible:false,
        title:"",
        content:"",
        postList: [

        ]
      }
    },
    methods: {
      publish()
      {
        this.dialogVisible=true;
      },
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqPostList(0,this.currentPage)/*type 1为论坛，0为课程答疑*/
        this.postList=result.tieList;

      },
      async handleEdit(){
        if(this.content.trim()===''||this.title.trim().length<5) {
          this.$message.error("标题或者内容均不能为空,且标题长度至少为5个字符")
        }
        else{
          const result=await releasePost(this.$store.state.id,this.title,this.content,0)/*1为课程论坛类型*/
          if(result.state_code===0){
            this.$message.success("发布问题成功！！")
            setTimeout(async() =>{
              const result=await reqPostList(0,this.currentPage)
              this.postList=result.tieList;
              this.tieNum=result.tieNum
              this.title=''
              this.content=''
            },100);
          }
          this.dialogVisible=false;
        }

      },
      gotopost(post_id){
        this.$router.push({
          path: '/center/activities/questiondetail',
          query:{
            post_id:post_id
          }
        })
      }
    },
    async mounted() {
      const result=await reqPostList(0,this.currentPage)/*type 1为论坛，0为课程答疑*/
      if(result.state_code===0){
        this.postList=result.tieList;
        this.tieNum=result.tieNum
      }
    }
  }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
</style>
