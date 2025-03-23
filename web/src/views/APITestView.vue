<template>
  <div class="about">
    <h1>{{ message }}</h1>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      message: null
    }
  },

  async created() {
    await this.aliveCheck()
  },

  methods: {
    async aliveCheck() {
      try {
        this.message = await axios.get('http:localhost:8081/alive')
        console.info('response:' + this.message)
      } catch (ex) {
        console.log(ex)
      }
    }
  }
}
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
<script setup></script>
