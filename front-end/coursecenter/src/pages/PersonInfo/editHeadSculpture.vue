<template>
  <div>
    <el-container>
      <el-header>
        <h2>修改头像</h2></el-header>
      <el-main>
        <div class="uploadHeadSculpture">
          <form>
            <input style="width: 50%" class="el-button" type="file"   accept="image/png,image/jpeg" v-on:change="getFile($event)">
            <el-button  type="success" @click="submit($event)">提交</el-button>
          </form>
        </div>
        <div class="uploadHeadSculpture">
          <label>
            点击提交上传新头像{{gg}}
          </label>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
      data() {
        return {
          FileList: [],
          filename:"",
        }
    },
    methods: {
      getFile: function (event) {
        this.file = event.target.files[0];
        this.filename=this.file.name;
      },
      submit: function (event) {
        //阻止元素发生默认的行为
        event.preventDefault()
        let formData = new FormData()
        formData.append("uno",this.$store.state.id.toString())
        formData.append("file", this.file)
        this.gg=this.$axios.post('http://139.224.137.85:8080/user/upd_pho', formData)
          .then( response=> {
            this.$message.success("提交成功")
            this.$store.dispatch('get_pho')
            setTimeout(async () => {
              this.$router.push({
                path: '/center/personInfo/editInfo',
                //path: '/center/Index/index',
                query:{
                  changed:true
                }
              })
            }, 1500)
          })
          .catch(function (error) {
            this.$message.error("上传失败")
          })
      },
    }
  }
</script>
<style scoped>
  .uploadHeadSculpture{
    padding-top: 60px;
  }

</style>
