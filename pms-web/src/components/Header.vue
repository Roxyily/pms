<template>
  <div style="display: flex; line-height: 60px;">
    <div style="margin-top: 5px; margin-left: -2px">
      <i :class="icon" style="font-size: 30px; cursor: pointer" @click="collapse"></i>
    </div>
    <div style="flex: 1; text-align: center; font-size: 34px">
      <span>欢迎来到金城天下物业管理系统</span>
    </div>
    <el-avatar icon="el-icon-user-solid" @click.native="toUser"></el-avatar>
    <span>{{ $store.getters.getUser.no }}</span>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin-left: 10px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
// import Aside from "@/components/IndexAside";
// import IndexHeader from "@/components/IndexHeader";
// import IndexMain from "@/components/IndexMain";
// import UserPage from "@/views/UserPage.vue";

export default {
  name: "IndexHeader",
  props:{
    icon:String
  },
  methods:{
    toUser(){
      this.$router.push("/userpage").catch(err => err);
      console.log('to_user')
    },
    logout(){
      console.log('logout')
    },
    collapse(){
      this.$emit('daCollapse')
    }
  }
}
</script>

<style scoped>
   .el-avatar {
    margin-top: 10px;
    margin-right: 5px;
   }
</style>