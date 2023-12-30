<template>
  <div>
    <el-menu
    :default-active="$route.path"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    style="height: 100vh"
    :collapse="isCollapse"
    :collapse-transition="false"
    :router="true">

   <div class="userbox">
    <div>
      <el-avatar icon="el-icon-user-solid" @click.native="toUser"></el-avatar>
      <div>{{ $store.getters.getUser.no }}</div>
    </div>
    
   </div>

    <el-menu-item index="/index" >
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-submenu index="/One">
        <template slot="title">
          <i class="el-icon-user-solid"></i>
          <span>住户管理</span>
        </template>
          <el-menu-item index="/household">住户信息</el-menu-item>
          <el-menu-item index="/complain">住户投诉管理</el-menu-item>
          <el-menu-item index="/warranty">住户保修管理</el-menu-item>
          <el-menu-item index="/adv_payment">住户预付款管理</el-menu-item>
    </el-submenu>

    <el-submenu index="/Two">
        <template slot="title">
          <i class="el-icon-s-management"></i>
          <span>物业管理</span>
        </template>
          <el-menu-item index="/equipment">物业设备维修管理</el-menu-item>
          <el-menu-item index="/fee_item">物业收费项目管理</el-menu-item>
          <el-menu-item index="/fee">物业收费管理</el-menu-item>
          <el-menu-item index="/property_meter">物业计量仪表管理</el-menu-item>
    </el-submenu>


    <el-submenu index="/Three">
        <template slot="title">
          <i class="el-icon-s-data"></i>
          <span>数据报表与统计</span>
        </template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
    </el-submenu>

  </el-menu>

    

  </div>
</template>

<script>
export default {
  name: "IndexAside",
  data(){
    return{
      //isCollapse:false
    }
  },
  methods:{
    toUser(){
      this.$router.push("/userpage").catch(err => err);
      console.log('to_user')
    },
    // toHome(){
    //   this.$router.push("/index");
    //   console.log('to_home')
    // },
    // toHousehold(){
    //   this.$router.push("/household");
    //   console.log('to_household')
    // },
  },
  props:{
    isCollapse:Boolean
  }
}
</script>

<style scoped>
.userbox > div{
      margin: 10px auto;
      font-size: 20px;
      color: white;
      text-align: center
}
</style>