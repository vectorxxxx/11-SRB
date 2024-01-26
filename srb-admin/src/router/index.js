import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    // 页面组件
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/core/integral-grade',
    // 应用Layout布局文件
    component: Layout,
    redirect: '/core/integral-grade/list',
    name: 'coreIntegralGrade',
    meta: { title: '积分等级管理', icon: 'el-icon-s-marketing' },
    alwaysShow: true,
    children: [
      {
        path: 'list',
        name: 'coreIntegralGradeList',
        component: () => import('@/views/core/integral-grade/list'),
        meta: { title: '积分等级列表' }
      },
      {
        path: 'create',
        name: 'coreIntegralGradeCreate',
        component: () => import('@/views/core/integral-grade/form'),
        meta: { title: '新增积分等级' }
      },
      {
        path: 'edit/:id',
        name: 'coreIntegralGradeEdit',
        component: () => import('@/views/core/integral-grade/form'),
        meta: { title: '编辑积分等级' },
        hidden: true
      }
    ]
  },

  {
    path: '/core',
    component: Layout,
    redirect: '/core/dict/list',
    name: 'coreDict',
    meta: { title: '系统设置', icon: 'el-icon-setting' },
    alwaysShow: true,
    children: [
      {
        path: 'dict/list',
        name: '数据字典',
        component: () => import('@/views/core/dict/list'),
        meta: { title: '数据字典' }
      }
    ]
  },

  {
    path: '/core/user-info',
    component: Layout,
    redirect: '/core/user-info/list',
    name: 'coreUserInfo',
    meta: { title: '会员管理', icon: 'user' },
    alwaysShow: true,
    children: [
      {
        path: 'list',
        name: 'coreUserInfoList',
        component: () => import('@/views/core/user-info/list'),
        meta: { title: '会员列表' }
      }
    ]
  },

  {
    path: '/core/borrower',
    component: Layout,
    name: 'coreBorrower',
    meta: { title: '借款管理', icon: 'el-icon-s-unfold' },
    alwaysShow: true,
    children: [
      {
        path: 'list',
        name: 'coreBorrowerList',
        component: () => import('@/views/core/borrower/list'),
        meta: { title: '借款人列表' }
      },
      {
        path: 'detail/:id',
        name: 'coreBorrowerDetail',
        component: () => import('@/views/core/borrower/detail'),
        meta: { title: '借款人详情' },
        hidden: true
      },
      {
        path: 'info-list',
        name: 'coreBorrowInfoList',
        component: () => import('@/views/core/borrow-info/list'),
        meta: { title: '借款列表' }
      },
      {
        path: 'info-detail/:id',
        name: 'coreBorrowInfoDetail',
        component: () => import('@/views/core/borrow-info/detail'),
        meta: { title: '借款详情' },
        hidden: true
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
