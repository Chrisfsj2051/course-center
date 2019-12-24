<template>
  <div style="width: 100%">
    <el-container>
      <el-header>
        <h2>修改资料</h2></el-header>
      <el-main>
        <div v-if=!this.$store.state.status>
          <el-container>
            <el-aside width="240px">
              <img :src="student.pho_url" width="200px" style="padding-top: 20px">
              <div style="padding-top: 30px">
                <el-button type="primary" icon="el-icon-edit" size="small" @click="updateHeadSculpture">更换头像</el-button>
              </div>
              <div class="showInfoleft">
                <label>带（*）属可修改属性</label>
              </div>
              <div class="showInfoleft">
              </div>
            </el-aside>
            <el-main>
              <div class="showInforight">
                <label>学号 ：</label>  {{student.no}}<br />
                <label>姓名 ：</label>{{ student.name}}<br />
                <label>用户类型 ：</label>{{student.user_type}}<br />
                <label>专业 ：</label>{{student.major}}<br />
                <label>学院 ：</label>  {{student.college}}<br />
                <label>性别 ：</label>  {{student.gender}}<br />
                <label>班级 ：</label>{{student.classid}}<br />
                <label>电话 （*）：  </label>{{student.phone}}
                <div style="float: right">
                  <el-button type="primary" icon="el-icon-edit" size="small" @click="updatePhone">修改</el-button>
                </div><br />
              </div>
            </el-main>
          </el-container>
        </div>
        <div v-if="this.$store.state.status">
          <el-container>
            <el-aside width="240px">
              <img :src="student.pho_url" width="200px" style="padding-top: 20px">
              <div style="padding-top: 30px">
                <el-button type="primary" icon="el-icon-edit" size="small" @click="updateHeadSculpture">更换头像</el-button>
              </div>
              <div class="showInfoleft">
                <label>带（*）属不可修改属性</label>
              </div>
            </el-aside>
            <el-main>
              <div class="showInforight">
                <label>职工号 ：</label>  {{student.no}}<br />
                <label>姓名 ：</label>{{ student.name}}<br />
                <label>用户类型 ：</label>{{student.user_type}}<br />
                <label v-if="!isManager">性别 ：</label>  {{student.gender}}<br />
                <label v-if="!isManager">学院 ：</label>  {{student.college}}<br />
                <label v-if="!isManager">联系电话 ：  </label>{{student.phone}}
                <div style="float: right">
                  <el-button type="primary" icon="el-icon-edit" size="small" @click="updatePhone">修改</el-button>
                </div><br />
                <label v-if="!isManager">办公室地址 ：</label>  {{student.office_address}}<br />
              </div>
            </el-main>
          </el-container>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>

  .showInfoleft{
    text-align: left;
    line-height: 40px;
    color: #333333;
    padding-left: 40px;
    padding-top: 30px;
  }
  .showInforight{
    text-align: left;
    line-height: 40px;
    color: #333333;
    padding-left: 100px;
    padding-top: 10px;
  }
</style>

<script>
  import {reqPersonInfo,BASE_URL} from '../../api'
  export default {
    data() {
      return {
        student:{},
        isManager:null
      }
    },
    methods: {
      updatePassword(){
            this.$router.push({path:'/center/personInfo/editPassword'})
      },
      updateHeadSculpture(){
        this.$router.push({path:'/center/personInfo/editHeadSculpture'})
      },
        updatePhone(){
          this.$router.push({path:'/center/personInfo/editPhone'})
        },
        updateNickname(){
          this.$router.push({path:'/center/personInfo/editNickName'})
        }
    },
    async mounted() {
      const result= await reqPersonInfo(this.$store.state.id)
      this.student=result
      this.student.pho_url=result.pho_url
      if(this.$store.state.status===-1){
        this.isManager=true
      }
    },
    beforeMount() {
      if(this.$route.query.changed===true)
      {
        this.$router.go(0)
      }


    }
  };
</script>

