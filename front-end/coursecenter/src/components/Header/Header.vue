<template>
  <div class="Header">
    <div id="logo">
      <i class="iconfont icon-book"></i>
      <h1>课程教学中心</h1>

      <el-button type="primary" size="" @click="logout()">退出</el-button>
      <a>{{name}}</a>
      <a id="image"><img :src="pho_url" width="35px"></a>
    </div>

  </div>
</template>

<script>
  import {reqPersonInfo} from "../../api";

  export default {
    name: "Header",
    data (){
      return{
        information:{
        name:'缪金',
        pho_url: '',
        }
      }
    },
    methods:{
      logout(){
        this.$store.dispatch('destroyToken');
        this.$router.replace('/login')
      }

    },
    mounted() {
      //const result= await reqPersonInfo(this.$store.state.id)
      //this.information=result
      this.$store.dispatch('get_pho')

    },
    computed:{
      pho_url(){
        return this.$store.state.pho_url;
      },
      name(){
        return this.$store.state.name;
      }
    }


  }
</script>

<style scoped>
  @import "../../assets/style/Header.css";

</style>
