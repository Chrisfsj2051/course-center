<template>
  <div class="Actsidebar">

    <el-row class="tac">
      <el-col :span="20">
        <h5>课程活动</h5>

        <el-menu router :default-active="$route.path" unique-opened class="el-menu-vertical-demo"
                 >

          <el-menu-item index="/center/activities/homework">
            <i class="el-icon-star-on"></i>
            <span slot="title">课程作业</span>
          </el-menu-item>


          <!--     <el-submenu v-if=this.$store.state.status index="2">
             <template slot="title">
             <i class="el-icon-edit"></i>
             <span>批阅作业</span>
             </template>
             <el-menu-item-group>
               <label v-for="(item,index) in homeworkData" :key="index">
                 <el-menu-item @click="gotoModule(item.homework_id)">
                   <span slot="title">{{item.title}}</span>
                 </el-menu-item>
               </label>
             </el-menu-item-group>
           </el-submenu>-->

           <el-submenu index="3">
             <template slot="title">

               <i class="el-icon-tickets"></i>
               <span>课程答疑</span>

             </template>
             <el-menu-item-group>
               <el-menu-item @click="gotoQuestion()">
                 <span slot="title">课程答疑</span>
               </el-menu-item>
               <el-menu-item @click="gotoMyQuestion()">
                 <span slot="title">我的问题</span>
               </el-menu-item>
             </el-menu-item-group>
           </el-submenu>

           <el-menu-item index="4" @click="gotoPaperList(0)">
             <i class="el-icon-edit"></i>
             <span slot="title">要点测试</span>
           </el-menu-item>

           <el-menu-item index="5" @click="gotoPaperList(1)">
             <i class="el-icon-setting"></i>
             <span slot="title">综合测试</span>
           </el-menu-item>

 <!--          <el-menu-item v-if=this.$store.state.status index="/center/activities/publishTest">-->
<!--            <i class="el-icon-circle-plus"></i>-->
<!--            <span slot="title">发表测试</span>-->
<!--          </el-menu-item>-->

        </el-menu>

      </el-col>
    </el-row>

  </div>
</template>

<script>
  import {reqHomeworkList} from '../../api'
  export default {
    name: "actsidebar",
    data(){
      return{
        moduleData: [

        ],
        homeworkData: [

        ]
      }
    },
    methods:{


      gotoQuestion(){
        this.$router.replace({
          path: '/center/activities/question',
        })
      },
      gotoMyQuestion(){
        this.$router.replace({
          path: '/center/activities/myquestion',
        })
      },
      gotoPaperList(paper_type){
        this.$router.replace({
          path: '/center/activities/null2',
          query: {
            paper_type: paper_type
          }
        })
      }

    },
    mounted() {
    }

  }
</script>

<style scoped>

  @import "../../assets/style/style.css";
</style>
