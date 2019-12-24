//异步获取作业通知
import ajax from './ajax'
import axios from 'axios'
import Qs from 'qs'
export const BASE_URL='http://139.224.137.85:8080'



//登陆
export const login=(uno,pwd)=>ajax(BASE_URL+'/user/login',{uno,pwd},'POST')

//获取个人信息
export const reqPersonInfo=(uno)=>ajax(BASE_URL+'/user/get_info',{uno},'POST')

/*      课程资源        */

//获取教学资源列表
export const reqppt_List=(cid)=>ajax(BASE_URL+'/course/get_ppt',{cid},'POST')
//获取花名册xls
export const reqxls=(cid)=>ajax(BASE_URL+'/course/get_xls',{cid},'POST')

export const reqStudentList=(cid)=>ajax(BASE_URL+'/course/get_roster',{cid},'POST')
//记录下载
export const log_download=(url,uno)=>ajax(BASE_URL+'/course/log_download',{url,uno},'POST')
//删除课件
export const delete_courseware=(fid)=>ajax(BASE_URL+'/course/delete_ppt',{fid},'POST')

/*         课程活动           */

//获取作业列表
export const reqHomeworkList=(cid,page)=>ajax(BASE_URL+'/hw/get_hw_list',{cid,page},'POST')
//获取单次作业所有学生提交列表
export const reqMarkingList=(hw_id,page)=>ajax(BASE_URL+'/hw/get_sub_hw_list',{hw_id,page},'POST')
//删除单次作业
export const delete_homework=(hw_id)=>ajax(BASE_URL+'/hw/delete_hw',{hw_id},'POST')
/*    打分    */
export const markHomework=(uno,hw_id,mark,suggestion)=>ajax(BASE_URL+'/hw/mark_hw',{uno,hw_id,mark,suggestion},'POST')
/*    获得归档文件   */
export const downZip=(hw_id)=>ajax(BASE_URL+'/hw/get_zip',{hw_id},'POST')
/*获取学生作业分数*/
export const reqMyMark=(hw_id,sno)=>ajax(BASE_URL+'/hw/get_my_status',{hw_id,sno},'POST')

/*请求综合测试*/
export const reqFinaltest=(paper_id)=>ajax(BASE_URL+'/exam/get_paper',{paper_id},'POST')
/*发布综合测试*/
export const sendFinaltest=(paper_id,cid,paper_type,question)=>axios.post(BASE_URL+'/exam/rel_question',{paper_id,cid,paper_type,question})
/*设置分数*/
export const count=(uno,paper_id,score)=>ajax(BASE_URL+'/exam/set_score',{uno,paper_id,score},'POST')
/*删除问题*/
export const delq=(paper_id,question_id)=>ajax(BASE_URL+'/exam/del_question',{paper_id,question_id},'POST')
/*删除试卷*/
export const delp=(paper_id)=>ajax(BASE_URL+'/exam/del_paper',{paper_id},'POST')
/*获取试卷列表*/
export const reqPaperList=(paper_type,cid,start)=>ajax(BASE_URL+'/exam/get_paper_list',{paper_type,cid,start},'POST')
/*发布试卷*/
export const publishPaper=(paper_type,cid)=>ajax(BASE_URL+'/exam/get_paper_list',{paper_type,cid},'POST')
/*创建试卷*/
export const createPaper=(paper_type,lesson_id,title)=>ajax(BASE_URL+'/exam/create_paper',{paper_type,lesson_id,title},'POST')
/*查看分数*/
export const findscore=(paper_id,sno)=>ajax(BASE_URL+'/exam/get_my_mark',{paper_id,sno},'POST')

/*          发表作业           */
export const release=(userid,title,lessonid,content,deadline)=>ajax(BASE_URL+'/hw/release_hw',{userid,title,lessonid,content,
deadline},'POST')
//获取作业内容
export const reqHomeworkDetail=(hw_id)=>ajax(BASE_URL+'/hw/get_hw',{hw_id},'POST')


/**********************课程论坛*****************/
//请求帖子列表
export const reqPostList=(type,page)=>ajax(BASE_URL+'/forum/get_tie_list',{type,page},'POST')
//获取帖子具体内容
export const reqPostDetail=(tie_id,page)=>ajax(BASE_URL+'/forum/get_tie',{tie_id,page},'POST')
//发帖
export const releasePost=(uno,title,content,type)=>ajax(BASE_URL+'/forum/post_tie',{uno,title,content,type},'POST')
//回帖
export const replyPost=(uno,tie_id,reply_id,content)=>ajax(BASE_URL+'/forum/reply_tie',{uno,tie_id,reply_id,content},'POST')
//我的帖子列表
export const reqMyPostList=(type,uno,page)=>ajax(BASE_URL+'/forum/get_my_tie_list',{type,uno,page},'POST')
//删除帖子
export const deletePost=(tie_id)=>ajax(BASE_URL+'/forum/delete_tie',{tie_id},'POST')

//思壕
export const reqMyHomeworkList=(uno,cid)=>ajax(BASE_URL+'/user/my_hwk',{uno,cid},'POST')

export const reqMyRecordList=(uno)=>ajax(BASE_URL+'/user/down_his',{uno},'POST')

export const reqLessonNoticeList=(cid,page)=>ajax(BASE_URL+'/course/notice_list',{cid,page},'POST')

export const reqLessonInfo=(cid)=>ajax(BASE_URL+'/course/intro',{cid},'POST')

export const reqLessonNotice=(nid)=>ajax(BASE_URL+'/course/get_notice',{nid},"POST")

export const submitEditPassword=(uno,pwd,old_pwd)=>ajax(BASE_URL+'/user/upd_pwd',{uno,pwd,old_pwd},"POST")

export const submitEditNickName=(uno,phone,nickname)=>ajax(BASE_URL+'/user/upd_info',{uno,phone,nickname},"POST")

export const submitEditPhoneNumber=(uno,phone,nickname)=>ajax(BASE_URL+'/user/upd_info',{uno,phone,nickname},"POST")

export const submitSendLessonNotice=(uno,title,content,cid)=>ajax(BASE_URL+'/course/post_notice',{uno,title,content,cid},"POST")

export const preiewLessonMain=(cid)=>ajax(BASE_URL+'/course/get_outline',{cid},"POST")
