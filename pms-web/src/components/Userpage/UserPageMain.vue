<template>
   <div>
        <div class="info">
            <el-descriptions title="个人信息" direction="vertical" :column="4" border>
            <el-descriptions-item label="用户名">{{ $store.getters.getUser.no }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ $store.getters.getUser.phone }}</el-descriptions-item>
            <el-descriptions-item label="年龄" :span="2">{{ $store.getters.getUser.age }}</el-descriptions-item>
            <el-descriptions-item label="管理员编号" >{{ $store.getters.getUser.id }}</el-descriptions-item>
            <el-descriptions-item label="管理的小区编号">{{ $store.getters.getUser.communityId }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ $store.getters.getUser.communityId ? '男' : '女'}}</el-descriptions-item>
            </el-descriptions>
         </div>

         <div class="account">
            <el-descriptions title="账号管理" direction="vertical" :column="4" border>
            <el-descriptions-item label="用户名">{{ $store.getters.getUser.no }}</el-descriptions-item>
            <el-descriptions-item label="账号">sdada</el-descriptions-item>
            <el-descriptions-item label="密码" :span="2">
              <el-input :type="isVisable ? 'text' : 'password'" :disabled="!isEditable" v-model="password"></el-input>
              <el-button @click="enableEditing">修改</el-button>
              <el-button @click="update_pwd">保存</el-button>
            </el-descriptions-item>
            <el-descriptions-item label="权限">
                <el-tag size="small">超级管理员</el-tag>
            </el-descriptions-item>
            </el-descriptions>
         </div>
   </div>
  </template>
  
  <script>
  export default {
    name: "IndexMain",
    data() {
      return {
        tableData: [],
        isEditable:false,
        isVisable:false,
        password: this.$store.getters.getUser.password ,
      }
    },
    methods:{
      enableEditing() {
        this.isVisable = true;
        this.isEditable = true;
     },
     update_pwd(){
      this.isVisable = false;
      this.isEditable = false;
     },
      loadPost(){
        this.$axios.post(this.$httpUrl + '/resident/listP', {}).then(res=>{
          console.log(res);
        })
      }
    },
    beforeMount() {
      this.loadPost();
    }
  }
  </script>
  
  <style scoped>
  .info {
    padding: 10px;
    background-color:rgb(244, 243, 243);
    border-radius: 0px;
    margin-top: 10px;
  }

  .account {
    padding: 10px;
    background-color:rgb(244, 243, 243);
    border-radius: 0px;
    margin-top: 25px;
  }

  .el-input {
    width: 250px;
  }
  </style>