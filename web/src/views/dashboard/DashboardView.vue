<template>
  <div class="dashboard-container">

    <header class="dashboard-header" :value="activeSectionTitle">
      <!-- <h1>{{ $t('dashboard.header') }}</h1>
      <div>
        <AccountSettings :account="account" class="dashboard-settings"/>
      </div> -->
      <DashboardHeader :account="account" :title="activeSectionTitle"/>
    </header>

    <aside class="dashboard-navbar">
      <DashboardNavbar @change-title="changeTitle"/>
      <button type="button" id="navbar-resize" @click="navbarToggle"><strong>&#9776;</strong></button>
    </aside>

    <main class="dashboard-main">
      <router-view ></router-view>
    </main>
    
    <footer class="dashboard-footer">
      {{ $t('dashboard.footer') }}
    </footer>
  </div>
</template>
<script>
import { accountService } from '@/service/.service-registry'
import DashboardHeader from './components/DashboardHeader.vue';
import DashboardNavbar from './components/DashboardNavbar.vue';
import AccountSettings from './components/AccountSettings.vue';
  export default {
    name: 'DashboardView',
    components: {
      AccountSettings,
      DashboardHeader,
      DashboardNavbar,
    },

    data() {
      return {
        account: null,
        activeSectionTitle: this.$t('dashboard.header'),
      };
    },

    created() {
      this.loadAccount()
    },

    mounted() {
      //set up the sidebar toggle
      const container = document.querySelector('.dashboard-container');
      const resizeButton = document.getElementById('navbar-resize');
        resizeButton.addEventListener('click', (e) => {
          e.preventDefault();
          container.classList.toggle('sb-shrink')
        });
    },

    methods: {
      async loadAccount() {
        //TODO finish implementing session management to resolve account
        const account = await accountService.getAccount("67e4549c1cabf45e727f0620")
        .then((response) => {
          return response
        })
        this.account = account
      },

      changeTitle(title) {
        this.activeSectionTitle = title
      },
    },
  };
</script>
