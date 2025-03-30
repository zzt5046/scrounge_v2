<script>
export default {
  name: 'App',
  components: {},

  created() {
    this.$router.push('/')
  }
}
</script>

<template>
  <div class="wrapper">
    <RouterView />
  </div>
</template>

<style scoped>
</style>
