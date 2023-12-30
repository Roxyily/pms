<template>
    <div>
        <h3 class="peopleNum">小区人数统计</h3>
        <div id="mycharts" style="width: 100%; height: 400px" ref="char"></div>
    </div>
</template>
  
<script>
import * as echarts from 'echarts'

  export default {
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
      option: {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: [
            ['product', '2012', '2013', '2014', '2015', '2016', '2017'],
            ['A区', 56.5, 82.1, 88.7, 70.1, 53.4, 85.1],
            ['B区', 51.1, 51.4, 55.1, 53.3, 73.8, 68.7],
            ['C区', 40.1, 62.2, 69.5, 36.4, 45.2, 32.5],
            ['D区', 25.2, 37.1, 41.2, 18, 33.9, 49.1]
          ]
        },
        xAxis: { type: 'category' },
        yAxis: { gridIndex: 0 },
        grid: { top: '50%',bottom:'40' },
        series: [
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@2012} ({d}%)'
            },
            encode: {
              itemName: 'product',
              value: '2012',
              tooltip: '2012'
            }
          }
        ]
      }
    }
  },
    methods: {
    // 配置echarts
    initMychart() {
      // 销毁实例(防止重复被复制)
      if (this.myChart) {
        this.myChart.dispose()
      }
      // 获得echart的DOM
      let chartDom = this.$refs.char
      // 初始化echart
      this.myChart = echarts.init(chartDom, this.theme);
      this.myChart.on('updateAxisPointer', (event) =>{
      const xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          const dimension = xAxisInfo.value + 1;
          this.myChart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      this.option && this.myChart.setOption(this.option,true);
    }
  },
  mounted() {
     // echart入口函数
     this.initMychart()
    } 
  }
</script>
  
<style scoped>
 .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .el-carousel {
    margin: 30px 20px;
  }
  .peopleNum{
    margin: 20px;
    margin-left: 575px;
  }
  #myHomeChart {
    width: 800px;
    height: 800px;
  }

  .el-icon-office-building{
    color: white;
    background-color: green;
    font-size:100px;
    border-radius: 5px;
  }
</style>