<template>
  <div>

    <div class="page">

      <h2 >我的帖子</h2>
      <el-table :data="postList" stripe style="width: 100%">

        <el-table-column label="帖子标题">
          <template slot-scope="scope">
            <el-button type="text" @click="gotopost(scope.row.post_id)">{{ scope.row.title }}</el-button>

          </template>
        </el-table-column>

        <el-table-column label="发帖日期">
          <template slot-scope="scope">
            <span >{{ scope.row.post_time }}</span>
          </template>
        </el-table-column>

        <el-table-column label="最后回帖人">
          <template slot-scope="scope">
            <span >{{ scope.row.last_reply_userid }}</span>
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
  import {reqMyPostList} from "../../../api";

  export default {
    name :'mypost',
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
      gotopost(post_id){
        this.$router.push({
          path: '/center/activities/questiondetail',
          query:{
            post_id:post_id
          }
        })
      },
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqMyPostList(0,this.$store.state.id,this.currentPage)/*type 1为论坛，0为课程答疑*/
        if(result.state_code===0){
          this.postList=result.tieList;
          this.tieNum=result.tieNum
        }

      },
    },
    async mounted() {
      const result=await reqMyPostList(0,this.$store.state.id,this.currentPage)/*type 1为论坛，0为课程答疑*/
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
