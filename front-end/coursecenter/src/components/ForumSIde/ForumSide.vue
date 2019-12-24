<template>
  <div>
    <el-row class="tac">
      <el-col :span="20">
        <h5>资源下载</h5>
        <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" >
          <el-menu-item index="/center/forum/postlist">
            <template slot="title"><i class="el-icon-document"></i>帖子列表</template>
          </el-menu-item>
          <el-menu-item index="/center/forum/mypost">
            <template slot="title"><i class="el-icon-edit"></i>我的帖子</template>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "forumside"
  }
</script>

<style scoped>

</style>
