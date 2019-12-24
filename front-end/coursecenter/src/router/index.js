import Vue from 'vue'
import Router from 'vue-router'
import Login from '../pages/Login/Login.vue'
import Center from '../pages/Center/Center'
import Resources from "../pages/Resources/Resources";
import Directory from "../pages/Resources/Directory/Directory";
import Courseware from "../pages/Resources/Courseware/Courseware";
import Activities from "../pages/Activities/Activities";
import Homework from "../pages/Activities/Homework/Homework";
import Marking from "../pages/Activities/Marking/Marking";
import HomeworkInfo from "../pages/Activities/HomeworkInfo/HomeworkInfo";
import Publish from "../pages/Activities/Publish/Publish";
import Question from "../pages/Activities/Question/Question";
import MyQuestion from "../pages/Activities/MyQuestion/MyQuestion";
import syllabus from "../pages/CourseInfo/syllabus";
import lessonInfo from "../pages/CourseInfo/lessonInfo";
import lessonNotice from "../pages/CourseInfo/lessonNotice";
import lessonMain from "../pages/CourseInfo/lessonMain";
import personInfo from "../pages/PersonInfo/personInfo";
import myInfo from "../pages/PersonInfo/myInfo";
import editorInfo from "../pages/PersonInfo/editorInfo";
import myhomework from "../pages/PersonInfo/myhomework";
import downl from "../pages/PersonInfo/downl";
import editpassword from "../pages/PersonInfo/editpassword";
import editHeadSculpture from "../pages/PersonInfo/editHeadSculpture";
import editPhoneNumber from "../pages/PersonInfo/editPhoneNumber";
import editNickName from "../pages/PersonInfo/editNickName";
import Index from "../pages/Index/Index";
import test from "../pages/Activities/test/test";
import Null2 from "../pages/Activities/Null/Null2";
import finaltest from "../pages/Activities/Finaltest/finaltest";
import noticeInfo from "../pages/CourseInfo/noticeInfo";
import PublishTest from "../pages/Activities/publishTest/PublishTest";
import sendNotice from "../pages/CourseInfo/sendNotice";
import Forum from "../pages/Forum/Forum";
import postList from "../pages/Forum/postList/postList";
import postDetail from "../pages/Forum/PostDetail/postDetail";
import questiondetail from "../pages/Activities/questionDetail/questiondetail";
import mypost from "../pages/Forum/MyPost/mypost";
import PaperList from "../pages/Activities/PaperList/PaperList";
Vue.use(Router)

export default new Router({
  //mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login,

    },
    {
      path: '/center',
      //name :'center',
      component: Center,
      children:[
        {//默认为首页
          path :'/center',
          redirect:'/center/index'
        },
        {
          path: '/center/index',
          name: 'index',
          component: Index,
        },
        {//课程信息
          path: '/center/syllabus',
         // name: 'syllabus',
          component: syllabus,
          children:[
            {//默认为课程信息
              path :'/',
              redirect:'/center/syllabus/lessonInfo'
            },
            {
              path: '/center/syllabus/lessonInfo',
              name: 'lessonInfo',
              component: lessonInfo
            },
            {
              path: '/center/syllabus/lessonNotice',
              name: 'lessonNotice',
              component: lessonNotice
            },
            {
              path: '/center/syllabus/lessonMain',
              name: 'lessonMain',
              component: lessonMain
            },{
              path:'/center/syllabus/notice',
              name:'noticeInfo',
              component:noticeInfo,
            },
            {
              path:'/center/syllabus/sendNotice',
              name:'sendNotice',
              component:sendNotice,
            },
          ]

        },
        //课程资源
        {
          path:'/center/resources',
         // name :'resources',
          component:Resources,
          children: [
            {//课程资源默认为课件
              path :'/',
              redirect:'/center/resources/courseware'
            },
            {
            path:'/center/resources/directory',
            name :'directory',
            component:Directory,
          },
            {
              path:'/center/resources/courseware',
              name :'courseware',
              component:Courseware,
            },
          ]
        },
        //课程活动
        {
          path:'/center/activities',
          component:Activities,
          children:[
            {//课程活动默认为作业列表
              path :'/',
              redirect:'/center/activities/homework'
            },
            {//作业列表
              path:'/center/activities/homework',
              name:'homework',
              component:Homework,

            },
           {//作业信息页面
             path:'/center/activities/homeworkInfo',
             name:'homeworkInfo',
             component:HomeworkInfo,
           } ,
            {//发布作业
              path:'/center/activities/publish',
              name:'publish',
              component:Publish
            },
            {//批阅作业
              path:'/center/activities/marking',
              name:'marking',
              component:Marking
            },
            {//空页面，用于路由跳转
              path:'/center/activities/null2',
              name:'null2',
              component:Null2
            },
            {//答疑列表
              path:'/center/activities/question',
              name:'question',
              component:Question
            },
            {//我的问题列表
              path:'/center/activities/myquestion',
              name:'myquestion',
              component:MyQuestion
            },
            {//答疑帖子详情
              path:'/center/activities/questiondetail',
              name:'questiondetail',
              component:questiondetail
            },
            {
              path:'/center/activities/test',
              name:'test',
              component:test
            },
            {
              path:'/center/activities/finaltest',
              name:'finaltest',
              component:finaltest
            },
            {
              path:'/center/activities/publishTest',
              name:'publishTest',
              component:PublishTest
            },
            {
              path:'/center/activities/paperlist',
              name:'paperlist',
              component:PaperList
            }



            ]
        },
        {
          path:'/center/personInfo',
          name:personInfo,
          component:personInfo,
          children:[
            {//个人信息默认为我的信息
              path :'/',
              redirect:'/center/personInfo/myInfo'
            },
            {
              path: '/center/personInfo/myInfo',
              name:'myInfo',
              component: myInfo
            },
            {
              path: '/center/personInfo/editInfo',
              name:'editInfo',
              component: editorInfo,
            },
            {
              path: '/center/personInfo/myhomework',
              name:'myhomework',
              component: myhomework
            },
            {
              path: '/center/personInfo/download',
              name:'dowmload',
              component: downl
            },
            {
              path:'/center/personInfo/editPassword',
              name:'editPassword',
              component:editpassword,
            },
            {
              path:'/center/personInfo/editHeadSculpture',
              name:'editHeadSculpture',
              component:editHeadSculpture,
            },
            {
              path:'/center/personInfo/editPhone',
              name:'editPhone',
              component:editPhoneNumber,
            },
            {
              path:'/center/personInfo/editNickName',
              name:'editNickName',
              component:editNickName,
            },
          ]
        },
        {
          path:'/center/forum',
          // name :'forum',
          component:Forum,
          children: [
            {//课程资源默认为课件
              path :'/',
              redirect:'/center/forum/postlist'
            },
            {
              path:'/center/forum/postlist',
              name :'postlist',
              component:postList,
            },
            {
              path:'/center/forum/mypost',
              name :'mypost',
              component:mypost,
            },
            {
              path:'/center/forum/postdetail',
              name :'postdetail',
              component:postDetail,
            },
          ]
        },
      ],
    },

    {
      path :'/',
      redirect:'/login'
    }
  ]
})


