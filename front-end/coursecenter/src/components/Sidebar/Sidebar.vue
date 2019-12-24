<template>
    <div>
      <el-row class="tac">
        <el-col :span="20">
          <h5>教学资源</h5>
          <el-menu router :default-active="$route.path" class="el-menu-vertical-demo">
            <el-menu-item index="/center/resources/courseware">
              <i class="el-icon-download"></i>
              <span slot="title">课件相关</span>
            </el-menu-item>
            <el-menu-item  v-if=this.$store.state.status index="/center/resources/directory">
              <i class="el-icon-menu"></i>
              <span slot="title">学生花名册</span>
            </el-menu-item>

          </el-menu>
        </el-col>
      </el-row>
    </div>
</template>

<script>
    export default {
        name: "sidebar"
    }
</script>

<style scoped>

</style>
