<script>
import SelectInput from '@/components/core/input/SelectInput.vue'
import TextInput from '@/components/core/input/TextInput.vue'
import { accountService } from '@/service/.service-registry'
import FormsMixin from '../../mixins/FormsMixin.vue'

export default {
  name: 'RegisterPanel',
  mixins: [FormsMixin],
  components: { TextInput, SelectInput },

  data() {
    return {
      username: null,
      email: null,
      password: null,
      confirmPassword: null,
      questions: [],
      securityQuestion: null,
      securityAnswer: null,
      response: null,
    }
  },

  created() {
    accountService.getSecurityQuestions().then((response) => {
      this.questions = response.map((question) => {
        return {
          id: question.id,
          name: question.question
        }
      })
    })
  },

  methods: {
    back() {
      this.$emit('done')
    },

    async register() {

      //Hash creds
      const secretHash = await this.hashStrings(this.password).then((hashed) => {
        return hashed
      })

      //Hash security question answer
      const answerHash = await this.hashStrings(this.securityAnswer).then((hashed) => {
        return hashed
      })

      const decoder = new TextDecoder()

      const request = {
          userName: this.username,
          credentials: encodeURI(decoder.decode(secretHash)),
          emailAddress: this.email,
          securityQuestionId: this.securityQuestion,
          securityQuestionAnswer: encodeURI(decoder.decode(answerHash))
      }

      console.log(request)

      accountService.register(request)
        .then((response) => {
          this.response = response
        })

    }
  }
}
</script>

<template>
  <div class="container">
    <div class="register-panel form">
      <div class="register-header">
        <h3>
          <a href="#" id="backButton">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              fill="currentColor"
              class="bi bi-arrow-left"
              viewBox="0 0 16 16"
              @click="back"
            >
              <path
                fill-rule="evenodd"
                d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8"
              />
            </svg>
          </a>

          {{ $t('actions.register') }}
        </h3>

        <span class="required-label">* {{ $t('common.required') }}</span>
      </div>
      <p>

        <TextInput id="register-username" :label="$t('fields.username')" v-model="username" required/>

        <TextInput id="register-email" :label="$t('fields.emailAddress')" type="email" v-model="email" />

        <TextInput
          id="register-password"
          :label="$t('fields.password')"
          type="password"
          v-model="password"
          required
        />

        <TextInput
          id="register-confirmPassword"
          :label="$t('fields.confirmPassword')"
          type="password"
          v-model="confirmPassword"
          required
        />

        <SelectInput
          id="register-securityQuestion"
          :label="$t('fields.securityQuestion')"
          :options="questions"
          v-model="securityQuestion"
          required
        />

        <TextInput
          id="register-securityAnswer"
          :label="$t('fields.securityAnswer')"
          type="password"
          v-model="securityAnswer"
          required
        />
      </p>
      <div>
        <div class="button-section">
          <button class="btn btn-primary" @click="register">{{ $t('actions.register') }}</button>
          <button class="btn btn-secondary" @click="back">
            {{ $t('actions.back') }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
