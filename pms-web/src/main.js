import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import router from './router'
import VueRouter from "vue-router";
import echarts from 'echarts'  //引入echarts
import ECharts from 'vue-echarts'
import Vuex from 'vuex'
import store from './store'

axios.defaults.withCredentials=true;
Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8090';
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts  //挂载在全局
Vue.component('VueEcharts', ECharts)
Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(Vuex);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')

