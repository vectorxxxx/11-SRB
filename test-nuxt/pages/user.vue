<template>
  <div>
    <h1>用户中心</h1>

    <NuxtLink to="/user">
      用户信息
    </NuxtLink>

    <NuxtLink to="/user/account">
      用户账户
    </NuxtLink>

    <!-- user目录下的页面的路由出口 -->
    <NuxtChild />
  </div>
</template>

<script>
export default {
  layout: 'my'
}
</script>

<style scoped>

</style>
