<template>
  <div class="dashboard-container">

    <header class="dashboard-header" :value="activeSectionTitle">
      <DashboardHeader :title="activeSectionTitle"/>
    </header>

    <aside class="dashboard-navbar">
      <DashboardNavbar @change-section="changeNavbarSection" @logout="logout"/>
      <button type="button" id="navbar-resize" @click="navbarToggle"><strong>&#9776;</strong></button>
    </aside>

    <main class="dashboard-main">
      <HomePanel v-show="showHomeSection"/>
      <RecipesPanel :account="account" v-show="showRecipeSection" @refresh-account="loadAccount"/>
      <AccountPanel :account="account" v-show="showSettingsSection"/>
    </main>
    
    <footer class="dashboard-footer">
      {{ $t('dashboard.footer') }}
    </footer>
  </div>
</template>
<script>
import { accountService } from '@/service/.service-registry'
import { store } from '../../store.js'
import { dashboardState } from './dashboardState.js'
import { DASHBOARD_HOME, DASHBOARD_RECIPES, DASHBOARD_SETTINGS, DASHBOARD_LOGOUT } from './dashboardState.js'
import DashboardHeader from './components/DashboardHeader.vue';
import DashboardNavbar from './components/DashboardNavbar.vue';
import HomePanel from './content/HomePanel.vue';
import RecipesPanel from './content/recipes/RecipesPanel.vue';
import AccountPanel from './content/account/AccountPanel.vue';
  export default {
    name: 'DashboardView',
    components: {
      DashboardHeader,
      DashboardNavbar,
      HomePanel,
      RecipesPanel,
      AccountPanel,
    },

    data() {
      return {
        account: null,
        activeSectionTitle: this.$t('dashboard.navbar.home'),
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

    computed: {
      showHomeSection() {
        return dashboardState.activeSection == DASHBOARD_HOME
      },
      showRecipeSection() {
        return dashboardState.activeSection == DASHBOARD_RECIPES
      },
      showSettingsSection() {
        return dashboardState.activeSection == DASHBOARD_SETTINGS
      },
    },

    methods: {
      async loadAccount() {
        //TODO finish implementing session management to resolve account
        const account = await accountService.getAccount(store.activeAccountId)
        this.account = account
        store.setActiveAccount(account)
      },

      changeNavbarSection(sectionId) {
        if(sectionId == DASHBOARD_LOGOUT) {
          this.logout()
        } else {
          const labelKey = dashboardState.getLabelKey(sectionId);
          this.activeSectionTitle = this.$t(labelKey);
        }
      },

      logout() {
        accountService.logout().then(() =>{
          this.$router.push({ name: 'root' })
        })
      },
    },
  };
</script>

<style>

.dashboard-hero {
  max-width: 10rem;
  max-height: auto;
}

</style>
