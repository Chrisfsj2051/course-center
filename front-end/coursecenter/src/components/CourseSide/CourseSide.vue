<template>
  <div>
    <el-row class="tac">
      <el-col :span="20">
        <h5>资源下载</h5>
        <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" @open="" @close="">
          <el-menu-item index="/center/syllabus/lessonInfo">
            <template slot="title"><i class="el-icon-document"></i>课程信息</template>
          </el-menu-item>

          <el-menu-item index="/center/syllabus/lessonNotice">
            <template slot="title"><i class="el-icon-message"></i>课程通知</template>
          </el-menu-item>

          <el-menu-item index="/center/syllabus/lessonMain">
            <template slot="title"><i class="el-icon-tickets"></i>教学大纲</template>
          </el-menu-item>

        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "courseside"
  }
</script>

<style scoped>

</style>
