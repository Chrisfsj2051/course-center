<template>
    <div style="width: 100%;">
      <el-container>
        <el-header>
          <h2>发布通知</h2>
        </el-header>
        <el-main style="padding-top: 20px">
          <el-form :model="sendForm" label-width="200px" label-position="right" ref="sendForm">
            <el-form-item  label="通知标题："  prop="title"  :rules="[
            { required: true, message: '通知标题不能为空！'}]">
            <el-input type="textarea" placeholder="请输入通知标题" v-model="sendForm.title" maxlength="30" :rows="1">
            </el-input>
            </el-form-item >
            <el-form-item   label="通知内容："   prop="content"  :rules="[
            { required: true, message: '通知内容不能为空！'}]">
            <el-input
              type="textarea"
              :rows="10"
              placeholder="请输入通知内容"
              v-model="sendForm.content">
            </el-input>
            </el-form-item>
          </el-form>
          <div style="float: right;padding-top: 30px">
            <el-button type="primary" icon="el-icon-upload2" size="small"  @click="submitForm()">确认发布</el-button>
          </div>
        </el-main>
      </el-container>
    </div>
</template>

<script>
  import {submitSendLessonNotice,BASE_URL} from '../../api'
    export default {
      name: "sendNotice",
      data() {
        return {
          sendForm:{
            title:'',
            content:'',
          }
        }
      },
      methods: {
        async submitForm() {
          if(this.sendForm.title.trim()!=''&&this.sendForm.content.trim()!=''){
              const result=submitSendLessonNotice(this.$store.state.id,this.sendForm.title,this.sendForm.content,this.$store.state.lessonid)
            if (result.state_code !==0) {
              this.$message.success("发布成功！")
              setTimeout(async () => {
                this.$router.push({
                  path: '/center/syllabus/lessonNotice',
                })
              }, 800)
            } else {
              this.$message.error("发布失败！")
            }
          }
          else{
            this.$message.error("标题与内容不能为空！")
          }
        }
      }

    }
</script>

<style scoped>
.sendNotice{
  height: 400px;
}
</style>
