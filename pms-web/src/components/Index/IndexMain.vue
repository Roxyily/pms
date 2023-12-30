<template>
    <div class="main">
        <el-carousel :interval="4000" type="card" width="500px" height="420px">
            <el-carousel-item v-for="item in imgList" :key="item.id">
              <img style="object-fit: scale-down; width: 100%; height: 100%;" :src="item.idView" class="image">
            </el-carousel-item>
        </el-carousel>

        <div class="infoBox">
          <div class="infoItem">
            <div class="iconBox1">
              <i class="el-icon-office-building"></i>
            </div>
            <div class="textBox">
              <div class="number">500</div>
              <div class="text">总栋数</div>
            </div>
          </div>

          <div class="infoItem">
            <div class="iconBox2">
              <i class="el-icon-school"></i>
            </div>
            <div class="textBox">
              <div class="number">1153</div>
              <div class="text">总户数</div>
            </div>
          </div>
          
          <div class="infoItem">
            <div class="iconBox3">
              <i class="el-icon-s-custom"></i>
            </div>
            <div class="textBox">
              <div class="number">6542</div>
              <div class="text">总人数</div>
            </div>
          </div>

          <div class="infoItem">
            <div class="iconBox4">
              <i class="el-icon-s-check"></i>
            </div>
            <div class="textBox">
              <div class="number">3257</div>
              <div class="text">租户数量</div>
            </div>
          </div>
        </div>
         
        <div class="tip">信息统计</div>
        <Chart_peopNum></Chart_peopNum>
    </div>
</template>
  
  <script>
import Chart_peopNum from './Chart_PeopleNumber';

  export default {
    components:{ Chart_peopNum },
    data() {
    return {
      imgList:[
                {id:0,idView:require('@/assets/小区1.jpg')},
                {id:1,idView:require('@/assets/小区2.jpg')},
                {id:2,idView:require('@/assets/小区3.jpg')},
                {id:3,idView:require('@/assets/小区4.jpg')},
                {id:4,idView:require('@/assets/小区5.jpg')},
                {id:5,idView:require('@/assets/小区6.jpg')},
      ],
    }
  }
  }
  </script>
  
  <style scoped>
  .main {
    height: 750px;
    overflow: scroll;
  }

  .infoBox{
    display: flex;
    justify-content:space-between;
    margin: 5px 5px;
  }

  .infoItem {
    display: flex;
  }

  .textBox {
    margin: auto 70px;
  }

 .number {
  /* margin-left: 4px; */
  font-size: 25px;
  margin: 10px auto;
 }

 .text {
  font-size: 18px;
  color: rgb(91, 90, 90);
  margin: 10px auto;
 }

 .iconBox1 {
  width: 80px;
  height: 80px;
  background-color: rgb(6, 190, 98);
  display: flex;
  align-items: center;
 }

 .iconBox2 {
  width: 80px;
  height: 80px;
  background-color: rgb(237, 88, 71);
  display: flex;
  align-items: center;
 }

 .iconBox3 {
  width: 80px;
  height: 80px;
  background-color: rgb(26, 160, 232);
  display: flex;
  align-items: center;
 }

 .iconBox4 {
  width: 80px;
  height: 80px;
  background-color: rgb(137, 48, 196);
  display: flex;
  align-items: center;
 }
  .el-icon-office-building{
    font-size: 27px;
    color: white;
    margin: auto auto;
    border-radius: 1px;
  }

  .el-icon-school {
    font-size: 27px;
    color: white;
    margin: auto auto;
    border-radius: 1px;
  }

  .el-icon-s-custom{
    font-size: 27px;
    color: white;
    border-radius: 1px;
    margin: auto auto;
  } 

  .el-icon-s-check {
    font-size: 27px;
    color: white;
    border-radius: 1px;
    margin: auto auto;
  }
  .tip {
    font-size: 30px;
    color: rgb(157, 157, 157);
    margin-top: 65px;
    margin-left: 50px;
  }
  </style>