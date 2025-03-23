<script>
import LoginView from '@/views/home/LoginView.vue'
import RegisterView from '@/views/home/RegisterView.vue'

export default {
  name: 'HomeView',
  components: { RegisterView, LoginView },

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
  <LoginView v-if="!register" @register="toRegister" />
  <RegisterView v-if="register" @done="toLogin" />
</template>

<style scoped></style>
