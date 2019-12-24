<template>
  <div style="width: 100%">
    <el-container>
      <el-header style="text-align: center; font-size: 20px">
        修改联系方式
      </el-header>
      <el-main>
        <el-form :model="numberValidateForm" ref="numberValidateForm" label-width="200px" label-position="right">
          <el-form-item
            label="请输入联系号码："
            prop="phone"
            :rules="[
            { required: true, message: '联系电话不能为空'},
            { type: 'number', message: '联系电话必须为数字值'}]"
          >
            <el-input type="phone" v-model.number="numberValidateForm.phone" autocomplete="off"
                      maxlength="11" minlength="11"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('numberValidateForm')">提交</el-button>
            <el-button type="primary" @click="resetForm('numberValidateForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {submitEditPhoneNumber, BASE_URL,} from '../../api'
  export default {
    name: "editPhoneNumber",
    data() {
      return {
        student:{},
        numberValidateForm: {
          phone: ''
        },
      }
    },
    methods: {
      async submitForm(formName) {
        var tmp=this.numberValidateForm.phone.toString()
        if (tmp.trim() !== '') {
          let flag=true
          if(tmp.length!=11)
            flag=false
          for (let i = 0; i<tmp.length;i++)
          {

            if(tmp[i]>= '0'&&tmp[i] <='9')
            {}
            else {
              flag=false
            }
          }
          if (flag)
          {
          const result = await submitEditPhoneNumber(this.$store.state.id, this.numberValidateForm.phone, this.student.nickName)
          this.student = result
          if (result.state_code === 0) {
            this.$message.success("联系电话修改成功！")
            setTimeout(async () => {
              this.$router.push({
                path: '/center/personInfo/editInfo',
              })
            }, 500)
          } else {
            this.$message.error("联系电话修改失败！")
           }
          }else {
            this.$message.error("联系电话只能是11位数字！")
          }
        }
        else {
          this.$message.error("联系电话不能为空！")
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
