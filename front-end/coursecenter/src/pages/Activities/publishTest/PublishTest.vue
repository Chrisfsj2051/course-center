<template>
  <div>
    <el-container>
      <el-main>
        <h1>发布考题</h1>
        <el-input placeholder="请输入考卷名称" v-model="title" readonly="true">
          <template slot="prepend">考卷名称</template>
        </el-input>

        <div style="margin-top:30px">
          <el-input placeholder="请输入内容" v-model="question.content">
            <template slot="prepend">题干</template>
          </el-input>
        </div>
        <div>
          <el-input placeholder="请输入内容" v-model="question.options[0].state">
            <template slot="prepend">选项1</template>
          </el-input>
        </div>
        <div>
          <el-input placeholder="请输入内容" v-model="question.options[1].state">
            <template slot="prepend">选项2</template>
          </el-input>
        </div>
        <div>
          <el-input placeholder="请输入内容" v-model="question.options[2].state">
            <template slot="prepend">选项3</template>
          </el-input>
        </div>
        <div>
          <el-input placeholder="请输入内容" v-model="question.options[3].state">
            <template slot="prepend">选项4</template>
          </el-input>
        </div>
        <div>
          <el-radio v-model="question.question_type" label=1>多选</el-radio>
          <el-radio v-model="question.question_type" label=0>单选</el-radio>
        </div>
        <div>
          <el-checkbox-group v-model="question.answer">
            <el-checkbox label=1>答案A</el-checkbox>
            <el-checkbox label=2>答案B</el-checkbox>
            <el-checkbox label=3>答案C</el-checkbox>
            <el-checkbox label=4>答案D</el-checkbox>
          </el-checkbox-group>
        </div>
        <el-button round @click="submiton()">提交</el-button>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {sendFinaltest} from '../../../api'
  import {publishPaper} from '../../../api'
  import {delp} from '../../../api'
  import {delq} from '../../../api'
  export default {
    name: "finaltest",
    data() {
      return {
        ptitle:"",
        ppaper_type:"",
        delete_paper:"",
        delete_question_qid:"",
        delete_question_pid:"",
        title:this.$route.query.title,
        cid:this.$store.state.lessonid,
        paper_type:this.$route.query.paper_type,
        paper_id:this.$route.query.paper_id,
        question: {
          question_type:"",
          content: "",
          options: [{
            option: 1,
            state: ""
          }, {
            option: 2,
            state: ""
          }, {
            option: 3,
            state: ""
          }, {
            option: 4,
            state: ""
          },],
          answer: [],
        },
      }


    },
    methods:{
      async submiton() {
        if(this.question.content.trim()==''||this.question.question_type==''||this.question.options[0].state.trim()==''||this.question.options[1].state.trim()==''||this.question.options[2].state.trim()==''||this.question.options[3].state.trim()==''||this.question.answer=='') {
          this.$message.error("题干与选项不能为空")
        }
        else if(this.question.question_type=="0"&&this.question.answer.length>1){
          this.$message.error("此题为单选!")
        }
        else {
          this.question.answer.sort(function (a, b) {
            if (a < b) {
              return -1;
            }
            if (a > b) {
              return 1;
            }
            return 0;
          })

          const result2 = await sendFinaltest(this.paper_id, this.cid, this.paper_type, this.question)
          this.$message.success("发布成功")
          this.question.question_type=""
          this.question.answer=[]
          this.question.content=""
          this.question.options[0].state=""
          this.question.options[1].state=""
          this.question.options[2].state=""
          this.question.options[3].state=""
          console.log(this.paper.questions)
        }
      },
      async delp(){
        if(this.delete_paper=='') {
          this.$message.error("选项不能为空！")
        }
        else {
          const result4 = await delp(this.delete_paper)
        }
      },
      async publishp(){
        const result3 = await publishPaper("1",this.ppaper_type,this.ptitle)
        this.$message.success("发布成功")
      },
    },

  }


</script>

<style scoped>
  .el-input {
    width: 700px;
  }
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 50px;
  }
</style>
