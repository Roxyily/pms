<template>
  <div>
    <el-container>
      <el-aside :width="aside_width" >
        <Aside :isCollapse="isCollapse"></Aside>
      </el-aside>

      <el-container >
        <el-header>
          <Header @daCollapse="doCollapse" :icon="icon"></Header>
        </el-header>

        <el-main>
          <EquipmentMain></EquipmentMain>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "../components/Aside";
import Header from "../components/Header";
import EquipmentMain from "../components/Equipment/EquipmentMain";


export default {
  name: "ComplainManage",
  components: {EquipmentMain, Header, Aside},
  data(){
    return{
      isCollapse:false,
      aside_width:'200px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse
      if(!this.isCollapse){
        //展开
        this.aside_width='200px',
            this.icon='el-icon-s-fold'
      }else{
        //收起
        this.aside_width='65px',
            this.icon='el-icon-s-unfold'
      }
    }
  }
}
</script>

<style>
.el-container {
  height: 100%;
  border: 1px solid #eee;
  overflow: hidden;
}
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
  text-align: right;
  font-size: 12px;
  border-bottom: rgba(169,169,169,0.3) 1px solid;
}
.el-main {
  padding: 0;
  height: 100%;
}
.el-aside {
  color: #333;
  background-color: rgb(238, 241, 246);
  height: 100vh;
  margin-left: -1px;
}
</style>