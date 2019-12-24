<template>
    <div>
      <h2 v-if="paper_type">综合测试</h2>
      <h2 v-else>要点测试</h2>
      <el-button v-if="isTeacher" round @click="publish_show()" type="primary">发布试卷</el-button>
      <el-table :data="paperList" stripe style="width: 100%">



        <el-table-column label="试卷标题">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column width="600px">
          <template slot-scope="scope">
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="medium" @click="gotoPaper(scope.row)">
              <span v-if="isTeacher">编辑/查看测试</span>
              <span v-else>查看/提交测试</span>
            </el-button>
          </template>
        </el-table-column>

        <el-table-column label="查看最高分" v-if="!isTeacher">
          <template slot-scope="scope">
            <el-button type=""  size= "medium" @click="Findscore(scope.row)"> 查看最高分</el-button>
          </template>
        </el-table-column>

        <el-table-column   v-if="isTeacher" style="float:right" label="">
          <template slot-scope="scope">
            <el-button v-if="isTeacher" style="text-align:center;float:right" type="danger"  size="medium" v-on:click="deletep(scope.row.paper_id)">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column width="100px">
          <template slot-scope="scope">
          </template>
        </el-table-column>
      </el-table>

<!--      <el-input auto-complete="off" v-model="newPaperTitle" ></el-input>-->
      <el-dialog title="创建新试卷" :visible="dialogVisible" @close="dialogVisible=false">
        <el-form >
          <el-form-item label="请输入试卷名">
            <el-input auto-complete="off" v-model="newPaperTitle" ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="publish_paper">确 定</el-button>
        </div>
      </el-dialog>
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
  import {delp, findscore, publishPaper} from '../../../api'
  import {reqPaperList} from '../../../api'
  import {createPaper} from '../../../api'
    export default {
        name: "paperlist",

      data(){
          return{
            isTeacher:false,
            paperList:[],
            currentPage:1, //初始页
            tieNum:10,
            dialogVisible: false,
            newPaperTitle:'',
            isTeacher:false,
          }
      },

      methods: {
        gotoPaper(row) {
          this.$router.push({
            path: '/center/activities/finaltest',
            query: {
              paper_id: row.paper_id,
              paper_type: this.paper_type,
              paper_title:row.title
            }
          })
        },
        async Findscore(row){
          const result5 = await findscore(row.paper_id,this.$store.state.id)
          this.score=result5.score
          if (result5.state_code===0) {
            this.$alert(this.score)
          }
          else{
            this.$message.error("您还未做过该卷")
          }
        },
        beforeMount(){
          this.paper_type=this.$route.query.paper_type
        },
        /*async mounted() {
          const result=await reqPaperList(this.paper_type,this.$store.state.lessonid)
          this.paperList=result
          if(this.$store.state.status===1){
            this.isTeacher=true}
        },*/

        handleCurrentChange: async function(currentPage) {
          this.currentPage = currentPage;
          const result=await reqPaperList(this.paper_type,this.$store.state.lessonid,(this.currentPage-1)*10)
          this.paperList=result.paper_list
          this.tieNum=result.totNum
        },

        async deletep(id){
          const result3 = await delp(id)
          const result=await reqPaperList(this.paper_type,this.$store.state.lessonid,(this.currentPage-1)*10)
          this.paperList=result.paper_list
          this.tieNum=result.totNum
          if(this.paperList.length===0&this.currentPage>1){
            this.currentPage-=1;
            const result=await reqPaperList(this.paper_type,this.$store.state.lessonid,(this.currentPage-1)*10)
            this.paperList=result.paper_list
            this.tieNum=result.totNum
          }
          this.$message.success("删除成功")
        },
        publish_show(){
          this.newPaperTitle=""
          this.dialogVisible = true
        },
        async publish_paper(){
          if(this.newPaperTitle.trim()===""){
            alert("试卷名不能为空");
            return ;
          }
          this.dialogVisible=false
          const result = createPaper(this.paper_type,this.$store.state.lessonid,this.newPaperTitle)
          if(result.state_code!==1){
            this.$message.success("发布成功！！")
            setTimeout(async () => {
              const result=await reqPaperList(this.paper_type,this.$store.state.lessonid,(this.currentPage-1)*10)
              this.paperList=result.paper_list
              this.tieNum=result.totNum
            }, 100);
          }
        }
      },
      beforeMount(){
        this.paper_type=this.$route.query.paper_type
      },
      async mounted() {
          const result=await reqPaperList(this.paper_type,this.$store.state.lessonid,(this.currentPage-1)*10)
          this.paperList=result.paper_list
        this.tieNum=result.totNum
        if(this.$store.state.status===1){

          this.isTeacher=true}

        }



    }
</script>

<style scoped>

</style>
