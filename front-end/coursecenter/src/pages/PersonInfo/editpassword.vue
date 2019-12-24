<template>
    <div>
      <el-container>
        <el-header style="text-align: center; font-size: 20px">
          修改密码
        </el-header>
        <el-main>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="200px" label-position="right" >
            <el-form-item label="请输入旧密码：" prop="oldPass">
              <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"   placeholder="必填项" show-password="true"></el-input>
            </el-form-item>
            <el-form-item label="请输入新密码：" prop="newPass">
              <el-input type="password" v-model="ruleForm.newPass" autocomplete="off" placeholder="必填项" show-password="true"></el-input>
            </el-form-item>
            <el-form-item label="请确认密码：" prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="必填项" show-password="true"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button  type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-main>
        </el-container>
    </div>
</template>

<script>
  import {submitEditPassword,BASE_URL} from '../../api'
  import md5 from 'js-md5'
  export default {
        name: "editpassword",
      data() {
          var validateOld = (rule, value, callback) => {
            if (!value) {
              return callback(new Error(' 请输入旧密码'));
            }
            callback();
          };
        var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'));
          } else {
            if (this.ruleForm.checkPass !== '') {
              this.$refs.ruleForm.validateField('checkPass');
            }
            callback();
          }
        };
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.ruleForm.newPass) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return {
          ruleForm: {
            oldPass:'',
            newPass: '',
            checkPass: '',
          },
          rules: {
            oldPass:[{validator:validateOld,trigger:'blur'}],
            newPass: [
              { validator: validatePass, trigger: 'blur' }
            ],
            checkPass: [
              { validator: validatePass2, trigger: 'blur' }
            ],
          }
        };
      },
      methods: {
        async submitForm() {
          if(this.ruleForm.newPass===this.ruleForm.checkPass){
          const result=await submitEditPassword(this.$store.state.id,md5(this.ruleForm.newPass),md5(this.ruleForm.oldPass))
          if(result.state_code===0){
            this.$message.success("密码修改成功！！！" + "将在3秒后跳转到登录页面")
            setTimeout(async() =>{
              this.$router.push({
                path: '/',
              })
            },3000)
          }
          else{
            this.$message.error("密码修改失败，请核对后重新输入！")
          }
          }
          else{
            this.$message.error("输入错误，请检查后重新输入！")
          }
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>
