<template>
  <div style="width: 100%;">
    <el-main >
      <el-container>
        <el-header>
          <h2>课程信息</h2></el-header>
        <el-container class="lessontime">
          <el-header class="title"><i class="el-icon-news" style="color: #42b983"></i>  课程简介</el-header>
          <el-main>
            <el-container>
              <aside width="180px">
                <img :src="lesson.pho_url" width="120px" style="padding-top: 20px">
              </aside>
              <el-main>
                <div class="c_intro">
                  {{lesson.c_intro}}
                </div>
              </el-main>
            </el-container>
          </el-main>
        </el-container>
        <el-container class="lessontime">
          <el-header class="title"><i class="el-icon-date" style="color: #42b983"></i>  开课时间</el-header>
          <el-main style="border: 0px">
            <div style="line-height: 40px">
              <label>开课学年：{{lesson. year}}</label><br />
              <label>开课学期：{{lesson.term}}</label>
            </div>
          </el-main>
        </el-container>
        <el-container class="lessontime">
          <el-header class="title"><i class="el-icon-goods" style="color: #42b983"></i> 预备知识</el-header>
          <el-main><div ><label v-for="item in lesson.pre_knowledge"> {{item}}</label></div></el-main>
        </el-container>
        <el-container class="lessontime">
          <el-header class="title"><i class="el-icon-menu" style="color: #42b983"></i>  教师团队</el-header>
          <el-main>
            <div style="padding-top: 0px">
              {{ lesson.teacher}}
            </div>
          </el-main>
        </el-container>
      </el-container>
    </el-main>
  </div>
</template>
<script>
  import {reqLessonInfo,BASE_URL} from '../../api'
  export default {
    data() {
      return {
        imageURL1: require('../../assets/image/software.jpg'),
        lesson:'',
      }
    },
    async mounted() {
      const result= await reqLessonInfo(this.$store.state.lessonid)
      this.lesson=result
    }
  }
</script>
<style scoped>
  .title{
    font-size: 20px;
    line-height: 60px;
    text-align: left;
  }
  .lessontime{
    text-align: left;
  }
  .c_intro{
    padding-left: 80px;
  }
</style>
