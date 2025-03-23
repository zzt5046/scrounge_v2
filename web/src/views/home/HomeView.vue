<script>
import LoginPanel from '@/views/home/LoginPanel.vue'
import RegisterPanel from '@/views/home/RegisterPanel.vue'

export default {
  name: 'HomeView',
  components: { RegisterPanel, LoginPanel },

  data() {
    return {
      register: false,
      authed: false
    }
  },

  methods: {
    toLogin() {
      this.register = false
    },
    toRegister() {
      this.register = true
    }
  }
}
</script>

<template>
  <LoginPanel v-if="!register" @register="toRegister" />
  <RegisterPanel v-if="register" @done="toLogin" />
</template>

<style scoped></style>
