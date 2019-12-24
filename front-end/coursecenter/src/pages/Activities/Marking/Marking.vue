<template>
  <div>

    <h2 style="display: inline">{{title}}</h2>
    <el-button
      type="primary"
      class="send"
      @click="downZip">下载全部作业</el-button>
    <el-table :data="homeworkList" stripe style="width: 100%">
      <el-table-column label="学号" >
        <template slot-scope="scope">
          <span>{{ scope.row.uno }}</span>
        </template>
      </el-table-column>


      <el-table-column label="姓名" width="70%">
        <template slot-scope="scope">
          <span >{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="上传日期">
        <template slot-scope="scope">
          <span >{{ scope.row.post_time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="分数" width="70%">
        <template slot-scope="scope">
          <span >{{ scope.row.mark}}</span>
        </template>
      </el-table-column>

      <el-table-column label="评价">
        <template slot-scope="scope">
          <span >{{ scope.row.suggestion }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="download(scope.row.url)">下载作业</el-button>
          <el-button
            size="mini"
            @click="openDialog(scope.$index, scope.row)">打分</el-button>
        </template>
      </el-table-column>

    </el-table>
    <el-dialog title="批阅" :visible.sync="dialogVisible" width="30%">
      <h3>分数：</h3>
      <el-rate v-model="mark" :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
      </el-rate>
      <h3>评价：</h3>
      <el-input type="textarea" :rows="2" placeholder="请输入评价" v-model="suggestion"></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleEdit()">确 定</el-button>
  </span>
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
  import {reqMarkingList,markHomework,downZip} from '../../../api'
  export default {

    name :'marking',
    data() {
      return {
        currentPage:1, //初始页
        tieNum:null,
        homework_id :'1',
        dialogVisible: false,
        suggestion:'',
        mark: 3.5,
        dialogIndex:'',//用于存储当前打开的学生面板的数组下标
        dialogRow:'',
        homeworkList: [

        ]
      }
    },
    methods: {
      openDialog(index,row)
      {
        this.dialogVisible=true;
        this.suggestion=row.suggestion;
        this.dialogIndex=index;
        this.mark=row.mark;
        this.dialogRow=row;
      },

      async handleEdit(){

        const result=await markHomework(this.dialogRow.uno,this.homework_id,this.mark,this.suggestion);

        if(result.state_code===0){
          this.homeworkList[this.dialogIndex].mark=this.mark;
          this.homeworkList[this.dialogIndex].suggestion=this.suggestion;
          this.dialogVisible=false;
          this.mark='';
          this.suggestion='';
          this.$message.success("批阅成功！！");
        }
        else{
          this.$message.error("批阅失败");
        }

      },
      download(url){
        window.open(url);
      },
      async downZip(){
        const result=await downZip(this.homework_id)
        if(result.state_code===0){
          window.open(result.url)
        }

      },
      handleCurrentChange: async function(currentPage){
        this.currentPage = currentPage;
        const result=await reqMarkingList(this.homework_id,this.currentPage)
        this.homeworkList=result.data
        this.tieNum=result.totNum

      },
    },
    beforeMount() {
      this.homework_id=this.$route.query.id;
      this.title=this.$route.query.title;
    },
    async mounted() {
      const result=await reqMarkingList(this.homework_id,this.currentPage)
      this.homeworkList=result.data
      this.tieNum=result.totNum
    }


  }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
</style>
