<template>
  <div>
    <el-form>
      <h2>发布作业</h2>
      <el-form-item>
        <h3>作业标题：</h3>
        <el-input  style="width: 80%" v-model="title"></el-input>
      </el-form-item>


      <el-form-item>
        <h3>作业说明：</h3>
        <el-input type="textarea" :rows="10" style="width: 80%" v-model="content"></el-input>
      </el-form-item>

      <el-form-item>
        <div class="block">
          <span class="demonstration" >截止日期</span>
          <el-date-picker value-format="yyyy/MM/dd" v-model="date" type="date" placeholder="选择日期" default-value="">
          </el-date-picker>

          <el-time-select
            v-model="time"
            :picker-options="{start: '00:00',step: '00:30', end: '23:30'}"
            placeholder="选择时间">
          </el-time-select>
        </div>

      </el-form-item>

      <el-form-item>
        <el-button size="medium" type="primary" @click="releaseH">发布作业</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
  import {release} from '../../../api'
    export default {
        name: "publish",
      data(){
          return{
            content:"",
            date:'',
            time:'',
            title:''
          }
      },
      methods:{
        async releaseH(){
              this.deadline=this.date+' '+this.time
              if(this.content.trim()===''||this.title.trim()===''||this.date.trim()===''||this.time.trim()===''){
                this.$message.error("标题，内容与截至日期均不为空")
              }
              else{
                const result=await release(this.$store.state.id,this.title,this.$store.state.lessonid,this.content,this.deadline)
                if(result.state_code===0) {
                  this.$message.success("发布成功！！")
                  this.$router.push('/center/activities/homework')
                }
              else if(result.state_code===2){
                  this.$message.error("截至日期不可以早于当前日期")
                }
              else{
                  this.$message.error("发布失败")
                }
                console.log(result)
              }


          }
      }
    }
</script>

<style scoped>

</style>
