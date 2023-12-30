import VueRouter from "vue-router"
import login from '../views/Login.vue'
import index from '../views/Index.vue'
import userpage from '../views/UserPage.vue'
import household from '../views/HouseholdManage.vue'
import complain from '../views/ComplainManage.vue'
import warranty from '../views/HouseholdWarrantyManage.vue'
import adv_payment from '../views/HouseholdAdvancedPaymentManage.vue'
import equipment from "@/views/Equipment.vue";
import fee from "@/views/Fee.vue";
import fee_item from "@/views/FeeItem.vue";
import property_meter from "@/views/PropertyMeter.vue";

const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/index',
    name: 'index',
    component: index
  },
  {
    path: '/userpage',
    name: 'userpage',
    component: userpage
  },
  {
    path: '/household',
    name: 'household',
    component: household
  },
  {
    path: '/complain',
    name: 'complain',
    component: complain
  },
  {
    path: '/warranty',
    name: 'warranty',
    component: warranty
  },
  {
    path: '/adv_payment',
    name: 'adv_payment',
    component: adv_payment
  },
  {
    path: '/equipment',
    name: 'equipment',
    component: equipment
  },
  {
    path: '/fee',
    name: 'fee',
    component: fee
  },
  {
    path: '/fee_item',
    name: 'fee_item',
    component: fee_item
  },
  {
    path: '/property_meter',
    name: 'property_meter',
    component: property_meter
  },
]

const router = new VueRouter({
  mode:'history',
  routes
})

export default router
