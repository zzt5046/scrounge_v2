<template>
    <div class="container">
      <div class="register-success-panel panel">
        <p>
          {{ $t('register.success') }}
        </p>
        <div>
          <div class="button-section flex-box">
            <button id="login-button" class="btn btn-primary" @click="done">
              {{ $t('actions.login') }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  
  export default {
    name: 'RegisterSuccessPanel',

    mounted() {
      console.log('RegisterSuccessPanel mounted')
    },

    methods: {
        done() {
            this.$emit('done')
        }
    }
  }
  </script>
  