<template>
  <!-- <div class="home-container"> -->
  <div>
    <div class="login-panel panel form">
      <div class="login-header">
        <h3 class="login-header">{{ $t('home.login.header') }}</h3>
        {{ $t('home.login.misc.registerDisclaimer1') }}
        <a href="#" @click="gotoRegister"> {{ $t('home.login.misc.registerDisclaimer2') }} </a>
      </div>
      <p>
        <TextInput id="login-username" :label="$t('fields.username')" v-model="username" />
        <TextInput id="login-password" :label="$t('fields.password')" v-model="password" type="password" />
      </p>
      <div>
        <div class="button-section flex-box">
          <button id="login-button" class="btn btn-primary" @click="login">
            {{ $t('actions.login') }}
          </button>
        </div>
        <div class="login-error-section flex-box">
          <span class="login-error error-text" v-if="errorText" @click="clearError">
            {{ errorText }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { accountService, recipeService } from '@/service/.service-registry'
import TextInput from '@/components/core/input/TextInput.vue'
import FormsMixin from '../../../mixins/FormsMixin.vue'
import { store } from '../../../store.js'

export default {
  name: 'LoginPanel',
  mixins: [FormsMixin],
  components: { TextInput },

  data() {
    return {
      username: null,
      password: null,
      loginResponse: null,
      errorText: null
    }
  },

  // created(){
  //   this.cookieLogin()
  // },

  methods: {

    //auth silently in the background and skip login if there's a session cookie
    // async cookieLogin() {
    //   const cookieLoginResponse = await accountService.cookieLogin()
    //   if(cookieLoginResponse.message === "cookie-auth"){
    //       this.$router.push('/dashboard')
    //   }
    // },

    async login() {

      this.errorText = null

      const secretHash = await this.hashStrings(this.password).then((hashed) => {
        return hashed
      })

      const request = {
        userName: this.username,
        credentials: encodeURI(secretHash)
      }

      const loginResponse = await accountService.login(request)
        .then((response) => {
          return response
        })

      if(loginResponse.accountId != null){
        this.$router.push('/dashboard')
      }else if(loginResponse.status == 401){
        this.errorText = this.$t('home.login.failed')
      } else {
        this.errorText = this.$t('home.login.error')
      }
    },

    clearError() {
      this.errorText = null
    },

    gotoRegister() {
      this.$emit('register')
    }
  },
}

</script>
