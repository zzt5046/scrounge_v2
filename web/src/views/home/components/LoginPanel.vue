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
      <div class="button-div">
          <LoadingButton 
            id="login-button" 
            class="btn btn-primary" 
            :loading="loading"
            :label="$t('actions.login')"
            @click="validateAndSubmit"
          />
        <div class="login-error-section">
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
import LoadingButton from '@/components/core/button/LoadingButton.vue'

export default {
  name: 'LoginPanel',
  mixins: [FormsMixin],
  components: { 
    TextInput, 
    RequiredNote,
    LoadingButton
  },

  data() {
    return {
      v$: useVuelidate({$scope: false}),
      username: null,
      password: null,
      loginResponse: null,
      loading: false,
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

      this.loading = true

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
      }finally{
        this.loading = false
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
<style scoped>
  .button-div {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
  }
</style>