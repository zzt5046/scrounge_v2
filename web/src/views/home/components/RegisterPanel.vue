<template>
  <!-- <div class="home-container"> -->
  <div>
    <div class="register-panel panel form">
      <div class="register-header">
        <h3> {{ $t('home.register.header') }} </h3>
        <RequiredNote />
      </div>
      <p>

        <TextInput id="register-username" :label="$t('fields.username')" v-model="username"
          :error="error(v$, 'username')" :errorMessage="errorMessage(v$, 'username')" required />

        <TextInput id="register-password" :label="$t('fields.password')" type="password" v-model="password"
          :error="error(v$, 'password')" :errorMessage="errorMessage(v$, 'password')" @change="v$.reset" required />

        <TextInput id="register-confirmPassword" :label="$t('fields.confirmPassword')" type="password"
          v-model="confirmPassword" :error="error(v$, 'confirmPassword')"
          :errorMessage="errorMessage(v$, 'confirmPassword')" @change="v$.reset" required />

        <TextInput id="register-email" :label="$t('fields.emailAddress')" type="email" v-model="email"
          :error="error(v$, 'email')" :errorMessage="errorMessage(v$, 'email')" />

        <SelectInput id="register-securityQuestion" :label="$t('fields.securityQuestion')" :options="questions"
          v-model="securityQuestion" :error="error(v$, 'securityQuestion')"
          :errorMessage="errorMessage(v$, 'securityQuestion')" required />

        <TextInput id="register-securityAnswer" :label="$t('fields.securityAnswer')" type="password"
          v-model="securityAnswer" :error="error(v$, 'securityAnswer')" :errorMessage="errorMessage(v$, 'securityAnswer')" required />
      </p>
      <div>
        <div class="button-section">
          <button class="btn btn-primary" @click="validateAndSubmit">{{ $t('actions.register') }}</button>
          <button class="btn btn-secondary" @click="back">
            {{ $t('actions.back') }}
          </button>
        </div>
        <div class="error-text" v-if="errorText">
          <span @click="errorText = null">{{ errorText }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SelectInput from '@/components/core/input/SelectInput.vue'
import TextInput from '@/components/core/input/TextInput.vue'
import { accountService } from '@/service/.service-registry'
import FormsMixin from '../../../mixins/FormsMixin'
import { store } from '../../../store'
import useVuelidate from '@vuelidate/core'
import { getValidations } from '@/validations'
import RequiredNote from '../../../components/core/input/RequiredNote.vue'

export default {
  name: 'RegisterPanel',
  mixins: [FormsMixin],
  components: { TextInput, SelectInput, RequiredNote },

  data() {
    return {
      v$: useVuelidate(),
      username: null,
      password: null,
      email: null,
      confirmPassword: null,
      questions: store.securityQuestions,
      securityQuestion: null,
      securityAnswer: null,
      errorText: null,
    }
  },

  validations() {
    return getValidations(
      this.$options.name,
      {password: this.password}
    )
  },

  methods: {
    back() {
      this.$emit('back')
    },

    validateAndSubmit() {
      this.v$.$validate()
      if (!this.v$.$error) {
        this.register()
      }
    },

    async register() {

      const request = {
          userName: this.username,
          password: this.password,
          emailAddress: this.email,
          securityQuestionId: this.securityQuestion,
          securityQuestionAnswer: this.securityAnswer,
      }

      try{
        const registerResponse = await accountService.register(request)
        this.$emit('registerSuccess')
      }catch(e){
        console.error('Register error:', e)
        this.errorText = this.$t('home.register.error')
      }
    }
  }
}
</script>