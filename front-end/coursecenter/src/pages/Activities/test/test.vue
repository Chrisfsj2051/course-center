<template>
  <div>
    <el-container>


      <el-main>
        <h1>知识要点测试1</h1>
        <div style="text-align:left">
          <h4>1.{{paper.questions[0].content }}</h4>
          <el-checkbox-group v-model="checkList[0]" style="display:block">
            <el-checkbox style="display:block" v-for="op in paper.questions[0].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>2.{{paper.questions[1].content }}</h4>
          <el-checkbox-group v-model="checkList[1]">
            <el-checkbox style="display:block" v-for="op in paper.questions[1].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>3.{{paper.questions[2].content }}</h4>
          <el-checkbox-group v-model="checkList[2]">
            <el-checkbox style="display:block" v-for="op in paper.questions[2].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>4.{{paper.questions[3].content }}</h4>
          <el-checkbox-group v-model="checkList[3]">
            <el-checkbox style="display:block" v-for="op in paper.questions[3].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>5.{{paper.questions[4].content }}</h4>
          <el-checkbox-group v-model="checkList[4]">
            <el-checkbox style="display:block" v-for="op in paper.questions[4].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>6.{{paper.questions[5].content }}</h4>
          <el-checkbox-group v-model="checkList[5]">
            <el-checkbox style="display:block" v-for="op in paper.questions[5].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>7.{{paper.questions[6].content }}</h4>
          <el-checkbox-group v-model="checkList[6]">
            <el-checkbox style="display:block" v-for="op in paper.questions[6].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>8.{{paper.questions[7].content }}</h4>
          <el-checkbox-group v-model="checkList[7]">
            <el-checkbox style="display:block" v-for="op in paper.questions[7].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>9.{{paper.questions[8].content }}</h4>
          <el-checkbox-group v-model="checkList[8]">
            <el-checkbox style="display:block" v-for="op in paper.questions[8].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
          <h4>10.{{paper.questions[9].content }}</h4>
          <el-checkbox-group v-model="checkList[9]">
            <el-checkbox style="display:block" v-for="op in paper.questions[9].options" :label="op.option" :key="op.id">{{op.state}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <el-button style="text-align:center" type="primary" v-on:click="toggleSelection()">提交</el-button>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {reqFinaltest} from '../../../api'
  export default {
    name: "finaltest",
    data() {
      return {
        count:0,
        checkList:[[],[],[],[],[],[],[],[],[],[]],
        paper_id:1,
        paper:{
          title:"第一次综合练习",
          paper_id:1,
          cid:1,
          //maker_name:"王老师",
          //maker_id:100001,
          questions:[{
            content:'下面说法哪个不属于设计准则?',
            options: [{
              option: 1,
              state: '提高模块的内聚，降低模块间的耦合'
            }, {
              option: 2,
              state: '降低模块接口的复杂程度'
            }, {
              option: 3,
              state: '模块大小要适中'
            }, {
              option: 4,
              state: '模块要有重用性'
            },],
            answer:[1,2,3],
          },{
            content:'计算机的体系结构对程序设计语言具有?',
            options: [{
              option: 1,
              state: '反作用'
            }, {
              option: 2,
              state: '促进作用'
            }, {
              option: 3,
              state: '抑制作用'
            }, {
              option: 4,
              state: '完善作用'
            },],
            answer:[1],
          },{
            content:'ISO9000是由ISO/TC176制定的关于（）和质量保证的国际标准。',
            options: [{
              option: '1',
              state: '质量控制'
            }, {
              option: '2',
              state: '质量管理'
            }, {
              option: '3',
              state: '质量策划'
            }, {
              option: '4',
              state: '质量改进'
            },],
            answer:[1],
          },{
            content:'CMM提供了一个框架，将软件过程改进的进化步骤组织成5个成熟度等级。除第1级外，每个等级都包含了实现该成熟度等级目标的若干（）',
            options: [{
              option: '1',
              state: '关键实践'
            }, {
              option: '2',
              state: '关键过程域'
            }, {
              option: '3',
              state: '软件过程能力'
            }, {
              option: '4',
              state: '软件过程'
            },],
            answer:[1],
          },{
            content:'在软件开发的各种资源中，（）是最重要的资源。',
            options: [{
              option: '1',
              state: '开发工具'
            }, {
              option: '2',
              state: '方法'
            }, {
              option: '3',
              state: '硬件环境'
            }, {
              option: '4',
              state: '人员'
            },],
            answer:[1],
          },{
            content:'软件的复杂性是（），它引起人员通信困难、开发费用超支、开发时间超时等问题。',
            options: [{
              option: '1',
              state: '固有的'
            }, {
              option: '2',
              state: '人为的'
            }, {
              option: '3',
              state: '可消除的'
            }, {
              option: '4',
              state: '不可降低的'
            },],
            answer:[1],
          },{
            content:'在各种不同的软件需求中，（D）描述了用户使用产品必须要完成的任务，可以在用例模型或方案脚本中予以说明。',
            options: [{
              option: '1',
              state: '业务需求'
            }, {
              option: '2',
              state: '功能需求'
            }, {
              option: '3',
              state: '非功能需求'
            }, {
              option: '4',
              state: '用户需求'
            },],
            answer:[1],
          },{
            content:'原型化方法是用户和软件开发人员之间进行的一种交互过程，适用于（）系统。',
            options: [{
              option: '1',
              state: '需求不确定的'
            }, {
              option: '2',
              state: '需求确定的'
            }, {
              option: '3',
              state: '管理信息'
            }, {
              option: '4',
              state: '决策支持'
            },],
            answer:[1],
          },{
            content:'（）意味着一个操作在不同的类中可以有不同的实现方式。',
            options: [{
              option: '1',
              state: '多态性'
            }, {
              option: '2',
              state: '多继承'
            }, {
              option: '3',
              state: '类的可复用'
            }, {
              option: '4',
              state: '信息屏蔽'
            },],
            answer:[1],
          },{
            content:'时序图反映对象之间发送消息的时间顺序，它与（）是同构的。',
            options: [{
              option: '1',
              state: '用例图'
            }, {
              option: '2',
              state: '类图'
            }, {
              option: '3',
              state: '活动图'
            }, {
              option: '4',
              state: '协作图'
            },],
            answer:[1],
          },],
        },
      }
    },
    methods:{
      toggleSelection() {
        var flag=[true,true,true,true,true,true,true,true,true,true];
        for (var j=0;j<10;j++) {
          for (var i = 0; i < this.checkList[0].length; i++) {
            if (this.checkList[j][i] != this.paper.questions[j].answer[i]) {
              flag[j] = false
            }
          }
          if (this.checkList[j].length != this.paper.questions[j].answer.length)
            flag[j] = false
          if (flag[j] == true) {
            this.count = this.count + 10
          }
        }

        this.$alert(this.count, '当前得分', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ this.$router.go(0) }`
            });
          }
        });
      },
    },
    async mounted() {
      const result= await reqFinaltest(this.paper_id)
      this.paper=result

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
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 50px;
  }
</style>
