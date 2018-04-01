import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import home from '../page/home/home'
import login from '../page/account/login'
import register from '../page/account/register'
import homepage from '../page/account/homepage'

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
          path: '/home/login',
          component: login
        },
        {
          path: '/home/register',
          component: register
        },
        {
          path: '/home/homepage',
          component: homepage
        }
      ]
    }
  ]
})
