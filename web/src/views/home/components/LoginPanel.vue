<template>
  <div>
    <div class="login-panel panel form">
      <div class="login-header">
        <h3 class="login-header">{{ $t('splash.login.header') }}</h3>
        <div>
          {{ $t('splash.login.misc.registerDisclaimer1') }}
          <a href="#" @click="gotoRegister"> {{ $t('splash.login.misc.registerDisclaimer2') }} </a>
        </div>
        <RequiredNote />
      </div>
      <p>
        <TextInput id="login-username" :label="$t('fields.username')" v-model="username" @enter="login"
          :error="error(v$, 'username')" :errorMessage="errorMessage(v$, 'username')" required />
        <TextInput id="login-password" :label="$t('fields.password')" v-model="password" @enter="login" type="password"
          :error="error(v$, 'password')" :errorMessage="errorMessage(v$, 'password')" required />
      </p>
      <div>
        <div class="button-section flex-box">
          <button id="login-button" class="btn btn-primary" @click="validateAndSubmit">
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
import { accountService } from '@/service/.service-registry'
import TextInput from '@/components/core/input/TextInput.vue'
import FormsMixin from '@/mixins/FormsMixin.js'
import useVuelidate from '@vuelidate/core'
import { getValidations } from '@/validations'
import RequiredNote from '../../../components/core/input/RequiredNote.vue'

export default {
  name: 'LoginPanel',
  mixins: [FormsMixin],
  components: { TextInput, RequiredNote },

  data() {
    return {
      v$: useVuelidate({$scope: false}),
      username: null,
      password: null,
      loginResponse: null,
      errorText: null
    }
  },

  validations() {
    return getValidations(this.$options.name)
  },

  methods: {

    validateAndSubmit(){
      this.v$.$validate()
      if(!this.v$.$error){
        this.login()
      }
    },

    async login() {

      this.v$.$validate()

      this.errorText = null

      const request = {
        userName: this.username,
        password: this.password
      }

      try{
        const loginResponse = await accountService.login(request)
          .then((response) => {
            return response
          })
        if (loginResponse.accountId != null) {
          this.$router.push('/dashboard')
        }
      }catch (error) {
        if (error.status == 401) {
          this.errorText = this.$t('splash.login.failed')
        } else {
          this.errorText = this.$t('splash.login.error')
        }
      }
    },

    clearError() {
      this.errorText = null
    },

    gotoRegister() {
      this.$emit('register')
    },
  },
}
</script>
