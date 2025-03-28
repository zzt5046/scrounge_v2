<template>
  <div class="home-container">
    <LoginPanel v-if="showLogin" @register="toRegister" />
    <RegisterPanel v-if="showRegister" @registerSuccess="toRegisterSuccess" @back="toLogin"/>
    <RegisterSuccessPanel v-if="showRegisterSuccess" @done="toLogin"/>
  </div>
</template>

<style scoped></style>

<script>
import LoginPanel from '@/views/home/components/LoginPanel.vue'
import RegisterPanel from '@/views/home/components/RegisterPanel.vue'
import RegisterSuccessPanel from '@/views/home/components/RegisterSuccessPanel.vue'

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
    }
  }
}
</script>