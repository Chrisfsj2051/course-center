
/*直接更新state的多个对象*/
import {
  RECEIVE_TOKEN,
  RECEIVE_STATUS,
  RECEIVE_ID,
  RECEIVE_LESSONID,
  RECEIVE_PHO_URL, RECEIVE_NAME
} from './mutation-types'
export default {
    [RECEIVE_TOKEN](state,{token}){
      state.token=token
    },
  [RECEIVE_STATUS](state,{status}){
      state.status=status
  },
  [RECEIVE_ID](state,{id}){
    state.id=id
  },
  [RECEIVE_LESSONID](state,{lessonid}){
    state.lessonid=lessonid
  },
  [RECEIVE_PHO_URL](state,{pho_url}){
      state.pho_url=pho_url
  },
  [RECEIVE_NAME](state,{name}){
    state.name=name
  },

}
