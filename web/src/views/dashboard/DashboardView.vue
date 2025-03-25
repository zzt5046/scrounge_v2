<template>
  <div>
    <h1>Scrounge</h1>
    <h3>Dashboard</h3>
  </div>
</template>
<script>
  import { accountService } from '@/service/.service-registry'
  export default {
    name: 'DashboardView',

    data() {
      return {
        account: null,
      };
    },

    created() {
      this.loadAccount()
    },

    methods: {
      async loadAccount() {
        const activeAccount = await accountService.getAccount(this.activeUserId)
        .then((response) => {
          return response
        })
        this.account = activeAccount
      },
    },
  };
</script>
