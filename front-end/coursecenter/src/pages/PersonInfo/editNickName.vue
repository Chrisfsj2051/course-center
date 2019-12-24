<template>
  <div>
    <el-container>
      <el-header style="text-align: center; font-size: 20px">
        修改论坛昵称
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" label-position="right">
          <el-form-item label="请输入论坛昵称" prop="nickName" :rules="[
            { required: true, message: '论坛昵称不能为空！'},
              { min: 1, max: 14, message: '长度在 1到 14 个字符', trigger: 'blur' }
             ]">
            <el-input v-model="ruleForm.nickName"
              type="text"
              placeholder="必填项"
              maxlength="14"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {submitEditNickName,BASE_URL} from '../../api'
  export default {
    name: "edit",
    data() {
      return {
        ruleForm: {
          nickName: '',
        },
        student:{}
      }
    },
    rules: {
      nickName: [{required: true, message: '请输入论坛昵称！', trigger: 'blur'}],
    },
    methods: {
      async submitForm() {
        if (this.ruleForm.nickName.trim() !== '') {
          const result = await submitEditNickName(this.$store.state.id, this.student.phone, this.ruleForm.nickName)
          this.student = result
          if (result.state_code === 0) {
            this.$message.success("论坛昵称修改成功！")
            setTimeout(async () => {
              this.$router.push({
                path: '/center/personInfo/editInfo',
              })
            }, 500)
          } else {
            this.$message.error("论坛昵称修改失败！")
          }
        }
        else {
          this.$message.error("论坛昵称不能为空！")
        }
      },
      resetForm(formName)
        {
          this.$refs[formName].resetFields();
        }
    },
  }

</script>

<style scoped>
</style>
