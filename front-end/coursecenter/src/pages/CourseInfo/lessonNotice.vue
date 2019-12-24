<template>
    <div style="width: 100%">

          <el-container>
            <el-header >
             <h2 style="display: inline">课程通知</h2>
              <el-button class="send"  v-if=this.$store.state.status type="primary" size="medium" @click="sendNotice()">发布通知</el-button>
            </el-header>
           <el-main>
             <el-container>
               <el-main>
                 <div class="lessonInfo">
                   <el-table :data="tableData" width="100%">
                     <el-table-column label="标题" >
                       <template slot-scope="scope">
                         <el-button type="text" @click="gotoDetail(scope.row)">{{ scope.row.title }}</el-button>
                       </template>
                     </el-table-column>
                     <el-table-column label="时间" width="200px" >
                       <template slot-scope="scope">
                         <span >{{ scope.row.time }}</span>
                       </template>
                     </el-table-column>
                     <el-table-column label="通知者" width="200px" >
                       <template slot-scope="scope">
                         <span >{{ scope.row.by }}</span>
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
                     :total=noticeNum>
                   </el-pagination>
                 </div>
               </el-main>
             </el-container>
           </el-main>
          </el-container>

</div>
</template>

<script>
  import {reqLessonNoticeList,BASE_URL} from '../../api'
    export default {
        name: "lessonNotice",
      data() {

        return {
          noticeNum: 12, //总共的通知数
          currentPage:1, //初始页
          tableData:[],
        }
      },
      methods:{
        gotoDetail(row){
          this.$router.push({
            path: '/center/syllabus/notice',
            query: {
              notice_id: row.notice_id
            }
          })
        },
        sendNotice(){
          this.$router.push({
            path: '/center/syllabus/sendNotice',
          })
        },
        handleCurrentChange: async function(currentPage){
          this.currentPage = currentPage;
          const result= await reqLessonNoticeList(this.$store.state.lessonid,this.currentPage)
          this.tableData=result.noticeList
          this.noticeNum=result.noticeNum


        },
      },
      async mounted() {
        const result= await reqLessonNoticeList(this.$store.state.lessonid,this.currentPage)
        this.tableData=result.noticeList
        this.noticeNum=result.noticeNum
      }
    }
</script>

<style scoped>
  .noticePage{
   text-align: center;
  }
  .send{
    float: right;
  }
</style>
