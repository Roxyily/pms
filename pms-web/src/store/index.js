import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


//公共state
const state={
    user:{
        age: '',
        communityId: '',
        id:'',
        isvalid: "",
        name: "",
        no: "",
        password: "",
        phone: "",
        roleId: '',
        sex:'',
    },
    residentList:[],
    residentsNum:{number:''}
}

//唯一取值的方法，计算属性
const getters= {
    getUser(state){
        return state.user;
    },
    getResidentList(state){
        return state.residentList;
    },
    getResidentNum(state){
        return state.residentsNum.number
    }
}

//
const mutations={
    updateUser(state,user){
        state.user=user;
    },
    updateResidentList(state,residentList){
        state.residentList=residentList;
    },
    updateResidentNum(state,number){
        state.residentsNum.number=number;
    },

}

//
const actions={
    asyncUpdateUser(context, user) {
        context.commit('updateUser',user);
    },
    asyncUpdateResidentList(context, residentList) {
        context.commit('updateResidentList',residentList);
    },
    asyncUpdateResidentNum(context, number) {
        context.commit('updateResidentNum',number);
    },
}

export default new Vuex.Store({
    state,
    getters,
    mutations,
    actions,
})