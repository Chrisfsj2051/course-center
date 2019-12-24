<template>
  <div>
      <h2 style="display: inline">作业列表</h2>
    <el-button v-if="isTeacher" type="primary"class="send"  @click="publish()">发布作业</el-button>
    <el-table :data="homeworkList" stripe style="width: 100%">
      <el-table-column label="作业" >
        <template slot-scope="scope">
          <el-button type="text" @click="Detail(scope.row.homework_id)">{{ scope.row.title }}</el-button>
        </template>
      </el-table-column>

      <el-table-column label="上传日期">
        <template slot-scope="scope">
          <span >{{ scope.row.release_time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="截止日期">
        <template slot-scope="scope">
          <span >{{ scope.row.deadline }}</span>
        </template>
      </el-table-column>



      <el-table-column label="提交人次" >
        <template slot-scope="scope">
          <span >{{ scope.row.submit_times }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            v-if=!isTeacher
            size="mini"
            type="primary"
            @click="Detail(scope.row.homework_id)">提交</el-button>
          <el-button
            v-if=!isTeacher
            size="mini"
            @click="getMyMark(scope.row)">查看分数</el-button>
          <el-button
            v-if=isTeacher
            size="mini"
            type="primary"
            @click="gotoModule(scope.row)">批阅</el-button>
          <el-button
            v-if=isTeacher
            size="mini"
            type="danger"
            @click="delete_hw(scope.row)">删除</el-button>

        </template>
      </el-table-column>

    </el-table>
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


  import {reqHomeworkList,delete_homework,reqMyMark} from "../../../api";

  export default {
    name :'homework',
    data() {
      return {
        isTeacher:false,
        tieNum:null,
        score :null,
        suggestion:null,
        currentPage:1, //初始页
        homeworkList: [

        ]
      }
    },
    methods: {
      gotoModule(row){//作业章节跳转
        this.$router.push({
          path: '/center/activities/marking',
          query: {
            id: row.homework_id,
            title:row.title
          }
        })
      },
      Detail(homework_id)
      {
        this.$router.push({
          path: '/center/activities/homeworkInfo',
          query: {
            hw_id: homework_id
          }
        })
      },
      async getMyMark(row){
        const result =await reqMyMark(row.homework_id,this.$store.state.id)

        if(result.state_code===1){
          this.suggestion="该次作业未提交"
          this.score=null
        }
        else{
          this.suggestion='教师评价:'+result.suggestion
          this.score='我的分数:'+result.score+'/5'
        }
        this.$alert(this.suggestion, this.score, {
          confirmButtonText: '确定',
          center: true

        });

      },
      publish(){
        this.$router.push({
          path: '/center/activities/publish',
        })

      },
      delete_hw(row){
        {
          this.$confirm('确认删除该次作业吗，删除后将无法恢复, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then( async () => {
            console.log(row.homework_id)
            const result=await delete_homework(row.homework_id)
            if(result.state_code===0){
              this.$message.success("删除成功")
              setTimeout(async() =>{
                const result=await reqHomeworkList(this.$store.state.lessonid,this.currentPage);
                this.homeworkList=result.homeworkList
                this.tieNum=result.totNum
                console.log(  this.homeworkList)
                if(this.homeworkList.length===0&&this.currentPage>1){
                  this.currentPage-=1
                  const result=await reqHomeworkList(this.$store.state.lessonid,this.currentPage);
                  this.homeworkList=result.homeworkList
                  this.tieNum=result.totNum
                }

              },100);}
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
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqHomeworkList(this.$store.state.lessonid,this.currentPage);
        if(result.state_code===0){
          this.homeworkList=result.homeworkList
          this.tieNum=result.totNum
        }

      },

    },
   async mounted() {
      const result=await reqHomeworkList(this.$store.state.lessonid,this.currentPage);
      if(result.state_code===0){
        this.homeworkList=result.homeworkList
        this.tieNum=result.totNum
        if(this.$store.state.status===1)
        {
          this.isTeacher=true
        }
      }

    }

  }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
</style>
