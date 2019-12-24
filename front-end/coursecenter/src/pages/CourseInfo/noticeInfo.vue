<template>
    <div style="width: 100%">
      <main>
        <el-container>
          <el-header>
            <h2>通知信息</h2>
          </el-header>
          <el-main>
            <el-container>
              <el-header style="text-align: left;background-color: #FFFFFF;">
                <span style="margin-right: 20px">标题：</span>
                <label>{{notice.title}}</label>
              </el-header>
              <el-main>
                <div style="text-align: left">
                  <span style="margin-right: 20px">正文：</span>
                  {{notice.content}}
                </div>
              </el-main>
              <el-footer>通知人 ： {{notice.by}}</el-footer>
              <el-footer>通知时间 ： {{notice.time}}</el-footer>
            </el-container>
          </el-main>
        </el-container>
      </main>
    </div>
</template>

<script>
  import {reqLessonNotice,BASE_URL} from '../../api'
    export default {
        name: "noticeInfo",
    data() {
      return {
        notice: '',

      }
    },
      async mounted() {
      const result= await reqLessonNotice(this.$route.query.notice_id)
      this.notice=result.data
    }
    };
</script>

<style scoped>

  .el-footer{
    background-color: #FFFFFF;
    line-height: 60px;
    text-align: right;
  }
</style>
