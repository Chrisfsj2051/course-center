
/*通过mutationss间接更新state的多个方法对象*/
import {
  RECEIVE_STATUS,
  RECEIVE_TOKEN,
  RECEIVE_ID,
  RECEIVE_LESSONID,
  RECEIVE_PHO_URL,
  RECEIVE_NAME
} from './mutation-types'
import md5 from 'js-md5'
import {reqPersonInfo,
  login} from '../api'
export default {
    //异步获取token
  /*        获取token,并将id，           */
  async initialize({commit},{username='null',password}){
      password=md5(password)
      const result=await login(username,password)//验证密码
    if(result.state_code===0) {
      const token =0
     // const token =result.data.error_code
      let status=-1
      //若为教师则身份为1，为学生则省份
      if(result.user_type=="Teacher"){
        status=1
      }
      else if(result.user_type=="Student")
      {
          status=0;
      }
      const id=parseInt(username)

      const result2=await reqPersonInfo(id);
      const lessonid=result2.lessonid
      //设置id
      sessionStorage.setItem('lessonid',lessonid)
      sessionStorage.setItem('id',username)
      //设置身份1：老师 0：学生
      sessionStorage.setItem('status',status)
      //设置token
      sessionStorage.setItem('token',token)
      commit(RECEIVE_TOKEN, {token})
      commit(RECEIVE_STATUS,{status})
      commit(RECEIVE_ID,{id})
      commit(RECEIVE_LESSONID,{lessonid})
      return 0;
    }
    else{
      return 1;
    }
  },
  destroyToken({commit}){
    const token='';
    const id='';
    const lessonid='';
    const status='';
    sessionStorage.setItem('lessonid',lessonid)
    sessionStorage.setItem('id',id)
    //设置身份1：老师 0：学生
    sessionStorage.setItem('status',status)
    //设置token
    sessionStorage.setItem('token',token)
    commit(RECEIVE_TOKEN, {token})
    commit(RECEIVE_STATUS,{status})
    commit(RECEIVE_ID,{id})
    commit(RECEIVE_LESSONID,{lessonid})

  },
  async get_pho({commit}){
    const result=await reqPersonInfo(parseInt(sessionStorage.getItem('id')));
    const pho_url=result.pho_url
    const name=result.name
    commit(RECEIVE_PHO_URL,{pho_url})
    commit(RECEIVE_NAME,{name})
  }

}
