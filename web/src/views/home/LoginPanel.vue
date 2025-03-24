<template>
  <div class="container">
    <div class="login-panel panel form">
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
import FormsMixin from '../../mixins/FormsMixin.vue'

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

  methods: {
    async submit() {
      this.login()
    },

    async login() {

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
          }else if(response.status == 401){
            this.errorText = this.$t('login.failed')
          } else {
            this.errorText = this.$t('login.error')
          }
        })
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
