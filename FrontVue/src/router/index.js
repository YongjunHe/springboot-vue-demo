import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import home from '../page/home/home'
import login from '../page/login/login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
    },
    {
      path: '/home',
      name: 'home',
      component: home,
      children: [
        {
          path: '/home/login',
          component: login
        }
      ]
    }
  ]
})
