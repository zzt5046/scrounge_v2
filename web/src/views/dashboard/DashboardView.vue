<template>
  <div class="dashboard-container">

    <header class="dashboard-header">
      <!-- <h1>{{ $t('dashboard.header') }}</h1>
      <div>
        <AccountSettings :account="account" class="dashboard-settings"/>
      </div> -->
      <DashboardHeader :account="account" />
    </header>

    <aside class="dashboard-sidebar">
      <!-- <nav>
        <ul>
          <li>
            <router-link to="/dashboard">{{ $t('dashboard.menu.home') }}</router-link>
          </li>
          <li>
            <router-link to="/dashboard/account">{{ $t('dashboard.menu.account') }}</router-link>
          </li>
          <li>
            <router-link to="/dashboard/transactions">{{ $t('dashboard.menu.recipes') }}</router-link>
          </li>
          <li>
            <router-link to="/dashboard/transfer">{{ $t('dashboard.menu.transfer') }}</router-link>
          </li>
          <li>
            <router-link to="/dashboard/settings">{{ $t('dashboard.menu.settings') }}</router-link>
          </li>
        </ul>
      </nav> -->
      <DashboardNavbar />
      <button type="button" id="navbar-resize" @click="navbarToggle">&#9776;</button>
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
        const account = await accountService.getAccount("67e4549c1cabf45e727f0620")
        .then((response) => {
          return response
        })
        this.account = account
      },
    },
  };
</script>
