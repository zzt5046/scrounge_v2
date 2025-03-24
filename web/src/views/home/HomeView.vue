<script>
import LoginPanel from '@/views/home/LoginPanel.vue'
import RegisterPanel from '@/views/home/RegisterPanel.vue'
import RegisterSuccessPanel from '@/views/home/RegisterSuccessPanel.vue'

export default {
  name: 'HomeView',
  components: { LoginPanel, RegisterPanel, RegisterSuccessPanel },

  data() {
    return {
      register: false,
      registerSuccess: false,
      authed: false
    }
  },

  computed: {
    showLogin() {
      return !this.register && !this.registerSuccess
    },
    showRegister() {
      return this.register && !this.registerSuccess
    },
    showRegisterSuccess() {
      return !this.register && this.registerSuccess
    }
  },

  methods: {
    toLogin() {
      this.register = false
      this.registerSuccess = false
    },
    toRegister() {
      this.register = true
      this.registerSuccess = false
    },
    toRegisterSuccess() {
      this.register = false
      this.registerSuccess = true
      console.log('showLogin: ' + this.showLogin)
      console.log('showRegister: ' + this.showRegister)
      console.log('showRegisterSuccess: ' + this.showRegisterSuccess)
    }
  }
}
</script>

<template>
  <LoginPanel v-if="showLogin" @register="toRegister" />
  <RegisterPanel v-if="showRegister" @registerSuccess="toRegisterSuccess" />
  <RegisterSuccessPanel v-if="showRegisterSuccess" @done="toLogin"/>
</template>

<style scoped></style>
