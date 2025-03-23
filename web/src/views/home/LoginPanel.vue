<template>
  <div class="container">
    <div class="login-panel form">
      <h3>{{ $t('login.header') }}</h3>
      <p>
        <TextInput id="login-username" :label="$t('fields.username')" v-model="username" />
        <TextInput id="login-password" :label="$t('fields.password')" v-model="password" type="password" />
      </p>
      <div>
        <div class="button-section flex-box">
          <button id="login-button" class="btn btn-primary" @click="submit">
            {{ $t('actions.login') }}
          </button>
          <div class="login-error">
            <span class="login-error error-text" v-if="errorText" @click="clearError">
              {{ errorText }}
            </span>
          </div>
        </div>
        <div class="action-section flex-box">
          {{ $t('login.misc.registerDisclaimer1') }}
          <a href="#" @click="gotoRegister"> {{ $t('login.misc.registerDisclaimer2') }} </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { accountService } from '@/service/.service-registry'
import TextInput from '@/components/core/input/TextInput.vue'
import { useVuelidate } from '@vuelidate/core'
import { helpers, required } from '@vuelidate/validators'
import FormsMixin from '../../mixins/FormsMixin.vue'

export default {
  name: 'LoginPanel',
  mixins: [FormsMixin],
  components: { TextInput },

  setup() {
    return { v$: useVuelidate() }
  },

  data() {
    return {
      username: null,
      password: null,
      loginResponse: null,
      errorText: null
    }
  },

  validations() {
    return {
      username: {
        required: helpers.withMessage('Username is required', required)
      },
      password: {
        required: helpers.withMessage('Password is required', required)
      }
    }
  },

  methods: {
    async submit() {
      this.login()
    },

    async login() {

      //Hash creds
      const secretHash = await this.hashStrings(this.password).then((hashed) => {
        return hashed
      })

      const decoder = new TextDecoder()

      const request = {
        userName: this.username,
        credentials: encodeURI(decoder.decode(secretHash))
      }

      await accountService.login(request)
        .then((response) => {
          if(response == 200){
            this.$router.push('/dashboard')
          } else if(response == 401){
            this.errorText = this.$t('login.failed')
          } else {
            this.errorText = this.$t('login.error')
          }
        })
    },

    displayValidationErrors() {
      for (let error in this.v$.$errors) {
        console.log(error)
      }
      this.usernameError = null
      this.passwordError = null
    },

    clearError() {
      this.errorText = null
    },

    gotoRegister() {
      this.$emit('register')
    }
  }
}
</script>
