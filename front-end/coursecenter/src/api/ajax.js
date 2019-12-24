/*ajax请求函数模块*/
/*返回data*/
import axios from 'axios'
import Qs from 'qs'
import state from '../store/state'
export default function ajax(url,data={},type='GET') {
  return new Promise(function (resolve, reject) {
    let promise
    if(type==='GET'){
      //准备url query参数
      let dataStr=''
      Object.keys(data).forEach(key=>{
        dataStr+=key+'='+data[key]+'&'
      })
      if(dataStr!==''){
        dataStr=dataStr.substring(0,dataStr.lastIndexOf('&'))
        url=url+'?'+dataStr
      }
      promise=axios.get(url)
      console.log(url)
    }
    else{
      const data2=Qs.stringify(data)
     promise=axios.post(url,data2)

    }
    promise.then(response=>{
      resolve(response.data)
    }).catch(error=>{
      reject(error)
    })
  })
}
