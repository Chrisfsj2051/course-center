<template>
    <div id="app">
      <el-container>
        <el-aside width="300px">
          <el-header>  <h1 class="fontsize">个人信息</h1></el-header>
          <p style="padding-top: 30px"><img :src="imageURL" width="150px"></p>
          <p>姓名：{{username}}</p>
          <p v-if=this.$store.state.status >工号：{{userno}}</p>
          <P v-else>学号：{{userno}}</P>
          <p>登录时间：{{ time | date }}</p>
        </el-aside>
        <el-main>
          <el-header><h1 class="fontsize">课程通知</h1></el-header>
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
          <el-button type="text" @click="gotoLessonNotice">查看更多>></el-button>
        </el-main>
        <el-aside width="350px">
            <el-header> <h1 class="fontsize">课程简介</h1></el-header>
          <el-main>
            <img :src="lessonImg" width="120px" style="padding-top: 20px">
            <p>{{c_intro}}</p>
          </el-main>
        </el-aside>
      </el-container>
    </div>
</template>

<script>
  import {reqLessonInfo, reqLessonNoticeList, reqPersonInfo} from "../../api";

  var date= new Date();

    export default {
        name: "index",
      data() {
        return {
          currentPage:1, //初始页
          c_intro:'',
          imageURL: '',
          tableData:[],
          username:'',
          userno:'',
          time:new Date(),
          lessonImg:''
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
        gotoLessonNotice(){
          this.$router.push({
            path: '/center/syllabus/lessonNotice',
          })
        },
      },
      async mounted() {
          if(this.$store.state.status===-1){
            this.$router.replace('/center/forum')
          }
          else {
            const result = await reqLessonNoticeList(this.$store.state.lessonid, this.currentPage)
            this.tableData = result.noticeList.splice(0, 5)
            const result1 = await reqPersonInfo(this.$store.state.id)
            this.username = result1.name
            this.userno = result1.no
            this.imageURL = result1.pho_url
            const result2 = await reqLessonInfo(this.$store.state.lessonid)
            this.c_intro = result2.c_intro
          }
        const result= await reqLessonNoticeList(this.$store.state.lessonid,this.currentPage)
        this.tableData=result.noticeList.splice(0,5)
        const result1= await reqPersonInfo(this.$store.state.id)
       this.username=result1.name
        this.userno=result1.no
        this.imageURL=result1.pho_url
        const result2= await reqLessonInfo(this.$store.state.lessonid)
        this.c_intro=result2.c_intro
        this.lessonImg=result2.pho_url
      },
      filters:{
          date(time){
            let date   = new Date(time)
            let year   = date.getFullYear();
            let month  = date.getMonth()+1<10 ? '0'+ (date.getMonth()+1) : date.getMonth()+1;
            let day    = date.getDate()<10 ? '0' + date.getDate() : date.getDate();
            let hour   = date.getHours()<10 ? '0' + date.getHours() : date.getHours();
            let minute = date.getMinutes()<10 ? '0' + date.getMinutes() : date.getMinutes();
            let second =  date.getSeconds()<10 ? '0' + date.getSeconds() : date.getSeconds();
            return year+"年"+month+"月"+day+"日  "+hour+":"+minute+":"+second
          }
      }
    };

</script>

<style scoped>
  .el-aside {
    background-color: white;
    color: #333;
    text-align: center;
    padding: 15px 0px 30px 20px;
    border-left: 1px solid rgba(7, 17, 27, 0.1);
    border-right: 1px solid rgba(7, 17, 27, 0.1);
  }

  .el-main {
    background-color: white;
    color: #333;
    text-align: center;
  }
  .fontsize{
    font-size: 30px;
    line-height: 60px;
  }

</style>
