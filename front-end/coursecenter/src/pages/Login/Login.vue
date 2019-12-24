<template>
  <div class="asdf">
    <div id="loginmenu">
     <el-form id="loginform">
       <h1 style="margin-bottom: 20%"> 课程教学中心</h1>
       <el-form-item>
         <i style="display: inline" class="iconfont icon-user"></i>
         <el-input v-model="username" id="userinput" placeholder="学号/教工号"  clearable></el-input>
       </el-form-item>
       <el-form-item>
         <i style="display: inline" class="iconfont icon-password"></i>
         <el-input v-model="password" id="passinput" placeholder="密码"  clearable show-password></el-input>
       </el-form-item>
       <el-form-item>

         <el-button size="medium"  type="primary"  @click="Student_login()" @keyup.enter="Student_login()">登陆</el-button>
       </el-form-item>
     </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name :'login',
    data() {
      return {
        password: '',
        username:'',
      }
    },
    methods:{

      async Student_login() {
        const result = await this.$store.dispatch('initialize',{username:this.username,password: this.password})
        if (result === 0)
          this.$router.push('/center')
        else{
          this.$message.error('用户名或者密码不正确，用户名：学生通常为学号，教师为工号');
        }
      },
    },
    mounted() {
      history.pushState(null, null, document.URL);
       window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
      })
    }
  }
</script>

<style scoped>
  #loginmenu{
    padding: 2%;
    float: right;
    width:23%;
    background-color: white;
    min-height: 670px;
    height: 100%;
    opacity: 0.9;}
  #loginform{
    margin: 50% auto;
  }
  .asdf{
    background-image: url(../../assets/image/4.jpg);
    background-size:cover;
    margin: 0;
    padding: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: scroll;
  }
  .iconfont{
    font-size: 40px;
    margin-right: 5px;
  }
  #username{
    height: 100%;

  }
  #password{
    height: 100%;
  }


  .el-input__inner{
    line-height: 5px;

  }
  .el-input{
    width: 70%;
  }


</style>
