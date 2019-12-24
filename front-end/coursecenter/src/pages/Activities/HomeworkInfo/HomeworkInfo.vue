<template>
  <div>
    <h2>{{homework.title}}</h2>
    <h3 id="deadline"> 截至日期：{{homework.deadline}}</h3>
    <h3 style="text-align: left">{{homework.content}}</h3>
    <div class="upload" v-if="canhandHw">
      <h3>上传作业</h3>
      <form>
        <input style="width: 50%" class="el-button" type="file" v-on:change="getFile($event)">
        <el-button  v-bind:disabled="!disable" type="success" @click="submit($event)">提交</el-button>
      </form>
    </div>
  </div>
</template>

<script>
  import {reqHomeworkDetail} from '../../../api'
    export default {
      name: "HomeworkInfo",

      data(){
        return {
          disable:true,
          content:'这是作业，虽然你肯定也不会做',
          homework_id: '',

          homework:{},
          canhandHw:false
        }
      },
      methods:{
        preview(row){
          const BASE ='http://view.officeapps.live.com/op/view.aspx?src=';
          const url=BASE+row.file_address
          var a=document.createElement("a");
          a.href=url
          window.open(a.href)
        },
        getFile: function (event) {
          this.file = event.target.files[0];
          this.filename=this.file.name;
        },
        submit: function (event) {
          //阻止元素发生默认的行为
          event.preventDefault();
          let formData = new FormData();
          formData.append("file", this.file)

          formData.append("uno",this.$store.state.id.toString())
          formData.append("hw_id", this.homework_id)
          formData.append("lesson_id",this.$store.state.lessonid)
          this.$axios.post('http://139.224.137.85:8080/hw/submit_hw', formData)
            .then(response=> {
              this.$message.success("上传成功！！")
              this.$router.replace('/center/activities/homework')
            })
            .catch(function (error) {
              this.$message.error("上传失败")
            })
        }
      },
     beforeMount() {
        this.homework_id=this.$route.query.hw_id;
      },
      async mounted() {
        const result=await reqHomeworkDetail(this.homework_id)
        this.homework=result.data
       if(this.homework.enable===0){
          this.disable=false;
        }
        if(this.$store.state.status===0){
          this.canhandHw=true;
        }
      }
    }
</script>

<style scoped>
  @import "../../../assets/style/style.css";
  #deadline{
    color:#F56C6C;
  }
</style>
