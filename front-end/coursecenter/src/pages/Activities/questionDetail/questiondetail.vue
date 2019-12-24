<template>
  <div>
    <el-dialog title="回复" :visible.sync="dialogVisible" :append-to-body="true" width="50%">

      <el-input type="textarea" :rows="5" placeholder="输入帖子内容" v-model="content" maxlength="160" show-word-limit></el-input>
      <span slot="footer" class="dialog-footer">
       <el-button @click="closeDialog()">取 消</el-button>
      <el-button type="primary" @click="handleEdit">确 定</el-button>
     </span>
    </el-dialog>
    <h2 class="title">{{post.title}}</h2>
    <el-button v-if="visible"type="danger" @click="delete_post()" size="medium" class="delete_post">删除帖子</el-button>
    <el-container class="post">
      <el-aside  width="12%">
        <div class="post_left">
          <p><img :src="post.pho_url" width="45px"></p>
          {{post.name}}
        </div>
      </el-aside>
      <el-main >
        <div class="post_content">
          {{post.content}}
        </div></el-main>

      <el-aside  width="12%">
        <el-button type="success" @click="reply(-1)" class="post_right_button">回复楼主</el-button>
        <div class="post_right">
          <span>楼主 </span>
          {{post.post_time}}

        </div>
      </el-aside>
    </el-container>
    <!--回复列表--->
    <div v-for="(item,index) in post.replyList" :key="index">
      <el-container class="post">
        <el-aside  width="12%">
          <div class="post_left">
            <p><img :src="item.pho_url" width="45px"></p>
            {{item.reply_name}}
          </div>
        </el-aside>

        <el-main >
          <div v-if="false" class="reply_post_content">
            <span v-if=item.reply_to>@</span>
            <span v-if=item.reply_to>{{item.reply_to_name}}</span>
            <span v-if=item.reply_to>{{item.reply_to_content}}</span>
          </div>
          <div class="post_content">

            {{item.content}}
          </div></el-main>

        <el-aside  width="12%">
          <div class="post_right">
            <p>{{item.reply_time}}</p>
            <p>{{(currentPage-1)*10+index+1}}楼</p>
          </div>
        </el-aside>
      </el-container>


    </div>
    <div class="paging">
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="currentPage"
        @current-change="handleCurrentChange"
        :total=post.reply_num>
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import {reqPostDetail,replyPost,deletePost}from '../../../api'
  export default {
    name: "questiondetail",
    data() {
      return {
        currentPage:1, //初始页
        reply_id:'',
        tie_id:'',
        content:'',
        visible:false,
        dialogVisible:false,
        post: {}
      }
    },
    methods:{
      reply(reply_id){
        this.dialogVisible=true;
        this.reply_id=reply_id
        console.log(reply_id)
      },
      closeDialog(){
        this.dialogVisible = false;
      },
      async handleEdit(){
        if(this.content===''){
          this.$message.error("回复内容不为空")
        }
        else{
          console.log(this.$store.state.id,this.tie_id,this.reply_id,this.content)
          const result=await replyPost(this.$store.state.id,this.tie_id,this.reply_id,this.content)
          if(result.state_code===0){
            this.$message.success("回复成功！！")
            setTimeout(async() =>{
              const result=await reqPostDetail(this.tie_id,this.currentPage)
              this.post=result
            },100);
            this.content=''
          }
          else{
            this.$message.error("回复失败")
          }
          this.dialogVisible=false;

        }


      },
      //跳转页面
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqPostDetail(this.tie_id,this.currentPage)
        this.post=result
        if(this.$store.state.id==this.post.id)
          this.visible=true;
      },
      //删除页面
      delete_post(){
        this.$confirm('确认删除该帖子吗，删除后将无法恢复, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
          const result=await deletePost(this.tie_id)
          if(result.state_code===0){
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.$router.replace('/center/activities/question')}
          else{
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
//初始化界面
    async mounted() {
      this.tie_id=this.$route.query.post_id
      const result=await reqPostDetail(this.tie_id,this.currentPage)
      this.post=result
      if(this.$store.state.id==this.post.id)
        this.visible=true;
    }
  }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
  .el-container{
    margin-top: 10px;
  }
  .el-aside {
    border-left: 1px solid rgba(7, 17, 27, 0.1);
    border-right: 1px solid rgba(7, 17, 27, 0.1);
  }
</style>
