<template>
  <div>
    <el-container>

      <el-main >
        <div>
          <h2 style="text-align:center;display: inline;font-size: 40px;">{{title}}</h2>

          <el-button v-if="this.$store.state.status" style="float:right;display: inline;font-size: 25px;"
                     type="primary" v-on:click="test1()">发布新问题</el-button>
        </div>
        <div style="text-align:left;font-size: 20px;margin:30px">
          <div v-for="(qu,index) in paper.questions" :key="qu.id">
            <div v-if="qu.type===1">
              <h2 style="display: inline">[多选]{{index+1}}.{{qu.content }}</h2>
              <el-button  v-if="isTeacher"
                          style="float:right;display: inline;font-size: 20px;" type="danger"
                          size="mini" v-on:click="deleteq(qu.question_id)">删除本题</el-button>
          <el-checkbox-group v-model="checkList[index]" style="display:block">
            <el-checkbox style="display:block" v-for="op in qu.options" :label="op.option" :key="op.id">{{op.choice}}</el-checkbox>
          </el-checkbox-group>
            </div>
            <div v-else>
              <h2 style="display: inline">[单选]{{index+1}}.{{qu.content }}</h2>
              <el-button  v-if="isTeacher"
                          style="float:right;display: inline;font-size: 20px;" type="danger"
                          size="mini" v-on:click="deleteq(qu.question_id)">删除本题</el-button>
              <el-checkbox-group v-model="checkList[index]" style="display:block" :max="1">
                <el-checkbox style="display:block" v-for="op in qu.options" :label="op.option" :key="op.id">{{op.choice}}</el-checkbox>
              </el-checkbox-group>
            </div>
            <p v-if="isTeacher" style="display: inline">答案：{{qu.answer}}</p>
        </div>
        </div>
        <el-button v-if="!this.$store.state.status" style="text-align:center" type="primary" v-on:click="toggleSelection()">提交</el-button>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {reqFinaltest} from '../../../api'
  import {count} from '../../../api'
  import {delq} from '../../../api'
  import {findscore} from '../../../api'
  export default {
    name: "finaltest",
    data() {
      return {
        score:"",
        sno:this.$store.state.id,
        title:this.$route.query.paper_title,
        isTeacher:false,
        count:0,
        realcount:0,
        checkList:[[],[],[],[],[],[],[],[],[],[]],
        paper_id:this.$route.query.paper_id,
        paper:{

        },
      }
    },
    methods:{
      async deleteq(id){
        const result3 = await delq(this.paper_id, id)
        const result= await reqFinaltest(this.paper_id)
        this.paper=result
        this.$message.success("删除成功")
     },

      test1(){
        this.$router.push({
          path: '/center/activities/publishTest',
          query:{
            title:this.title,
            paper_type:this.$route.query.paper_type,
            paper_id:this.$route.query.paper_id,
          }})

      },

      async toggleSelection() {
        var flag = [true, true, true, true, true, true, true, true, true, true];
        for (var j = 0; j < this.paper.questions.length; j++) {
          if (this.paper.questions[j].type==1) {
            for (var i = 0; i < this.checkList[j].length; i++) {
              this.checkList[j].sort(function(a,b){
                if(a<b){
                  return -1;
                }
                if(a>b){
                  return 1;
                }
                return 0;
              })
              // if (this.paper.questions[j].type == 1) {
              if (this.checkList[j][i] != this.paper.questions[j].answer[i]) {
                flag[j] = false
              }
            }
            if (this.checkList[j].length != this.paper.questions[j].answer.length)
              flag[j] = false
            if (flag[j] == true) {
              this.count = this.count + 1
            }
          }
          else {
            if (this.checkList[j]!=this.paper.questions[j].answer[0]) {
              flag[j] = false
            }
            if (flag[j] == true) {
              this.count = this.count + 1
            }
          }
        }


        this.realcount=parseInt(this.count/this.paper.questions.length*100)
        const result1=await count(this.$store.state.id,this.paper_id,this.realcount)
        this.$alert(this.realcount, '当前得分', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ this.$router.go(0) }`
            });
            this.count=0;
          }
        });
      },
    },
    async mounted() {
      const result= await reqFinaltest(this.paper_id)
      this.paper=result
      if(this.$store.state.status===1){
        this.isTeacher=true}
    },
    beforeMount() {
      this.paper_id=this.$route.query.paper_id
    }

  }

</script>

<style scoped>
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 50px;
  }
</style>
