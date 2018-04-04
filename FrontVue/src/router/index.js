import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import home from '../page/home/home'
import login from '../page/account/login'
import register from '../page/account/register'
import homepage from '../page/account/homepage'
import staffs from '../page/person/staffs'
import students from '../page/person/students'
import courses from '../page/courses/courses'
import scores from '../page/courses/scores'
import colleges from '../page/colleges/colleges'
import orders from '../page/orders/orders'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      name: 'home',
      component: home,
      children: [
        {
          path: '/home/helloWorld',
          component: HelloWorld
        },
        {
          path: '/account/login',
          component: login
        },
        {
          path: '/account/register',
          component: register
        },
        {
          path: '/account/homepage',
          component: homepage
        },
        {
          path: '/person/staffs',
          component: staffs
        },
        {
          path: '/person/students',
          component: students
        },
        {
          path: '/courses/courses',
          component: courses
        },
        {
          path: '/courses/scores',
          component: scores
        },
        {
          path: '/colleges/colleges',
          component: colleges
        },
        {
          path: '/orders/orders',
          component: orders
        }
      ]
    }
  ]
})
