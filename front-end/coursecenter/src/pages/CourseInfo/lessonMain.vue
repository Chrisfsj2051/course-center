<template>
  <div style="width: 100%">
    <el-main>
      <el-container>
        <el-header>
          <h2>课程大纲</h2></el-header>
        <el-main>
          <img :src="imageURL1" width="50%" style="padding-top: 40px">
          <div v-if=this.$store.state.status style="padding-top: 60px">
            <form>
              <input style="width: 30%" class="el-button" type="file" v-on:change="getFile($event)">
              <el-button  type="success" @click="submit($event)">上传</el-button>
              <el-button
                type="primary "
                size="mideum"
                icon="el-icon-view"
                @click="preview(file)">查看大纲</el-button>
            </form>
          </div>
          <div v-else>
            <el-button
              id="id"
              type="primary "
              size="mideum"
              icon="el-icon-view"
              @click="preview(file)">查看大纲</el-button>
          </div>
        </el-main >
      </el-container>
    </el-main>
  </div>
</template>

<script>
  import {preiewLessonMain,BASE_URL} from '../../api'
    export default {
      data() {
        return {
          imageURL1: require('../../assets/image/lessmain.jpg'),
          FileList: [],
          filename: '',
          file: ''
        }
      },
      methods: {
        getFile: function (event) {
          this.file = event.target.files[0];
          this.filename = this.file.name;
        },
        preview(file){
          if(file.preview_url!='')
          {
            window.open(file.preview_url)
          }else {
            this.$message.error("当前无教学大纲文件，请先上传！")
          }
        },
        submit: function (event) {
          //阻止元素发生默认的行为
          event.preventDefault();
          let formData = new FormData();
          formData.append("file", this.file)
          formData.append("cid", this.$store.state.lessonid)
          formData.append("uno", this.$store.state.id.toString())
          this.$axios.post('http://139.224.137.85:8080/course/upload_outline', formData)
            .then(async response => {
              this.$message.success("上传成功！！")
              })
            .catch(function (error) {
              this.$message.error("上传失败")
            })
        }
      },
      async mounted() {
        const result = await preiewLessonMain(this.$store.state.lessonid)
        this.file = result
        let ff=document.getElementById('id')
        if(ff!='')
        {
          setTimeout(async () => {
            ff.click()
          }, 0)
        }
      },
    }
</script>

<style scoped>

</style>
