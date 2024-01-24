<template>
  <div>
    <NuxtLink to="/about">
      关于我们
    </NuxtLink>

    <NuxtLink to="/lend">
      我要投资
    </NuxtLink>

    <NuxtLink to="/user">
      用户中心
    </NuxtLink>

    <a href="http://atguigu.com" target="_blank">尚硅谷</a>

    <h1>Home page</h1>

    {{ ip }}
  </div>
</template>

<script>
export default {
  async asyncData({ $axios }) {
    return {
      // ip: await $axios.$get('http://icanhazip.com')
      ip: await $axios.$get('/')
    }
  },
  // data() {
  //   return {
  //     ip: null
  //   }
  // },

  created() {
    // this.$axios.$get('http://icanhazip.com').then(response => {
    //   console.log(response)
    //   this.ip = response
    // })
  }
}
</script>

<style scoped>

</style>
