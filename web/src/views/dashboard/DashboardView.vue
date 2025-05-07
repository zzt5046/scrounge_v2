<template>
  <div class="dashboard-container">

    <header class="dashboard-header" :value="activeSectionTitle">
      <DashboardHeader :account="account" :title="activeSectionTitle"/>
    </header>

    <aside class="dashboard-navbar">
      <DashboardNavbar @change-title="changeTitle" @logout="logout"/>
      <button type="button" id="navbar-resize" @click="navbarToggle"><strong>&#9776;</strong></button>
    </aside>

    <main class="dashboard-main">
      <HomePanel v-show="showHomeSection"/>
      <RecipesPanel :account="account" v-show="showRecipeSection"/>
    </main>
    
    <footer class="dashboard-footer">
      {{ $t('dashboard.footer') }}
    </footer>
  </div>
</template>
<script>
import { accountService } from '@/service/.service-registry'
import { store } from '../../store.js'
import DashboardHeader from './components/DashboardHeader.vue';
import DashboardNavbar from './components/DashboardNavbar.vue';
import AccountSettings from './content/SettingsPanel.vue';
import HomePanel from './content/HomePanel.vue';
import RecipesPanel from './content/recipes/RecipesPanel.vue';
  export default {
    name: 'DashboardView',
    components: {
      AccountSettings,
      DashboardHeader,
      DashboardNavbar,
      HomePanel,
      RecipesPanel,
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
        return this.activeSectionTitle === this.$t('dashboard.navbar.home')
      },
      showRecipeSection() {
        return this.activeSectionTitle === this.$t('dashboard.navbar.recipes')
      },
      showSettingsSection() {
        return this.activeSectionTitle === this.$t('dashboard.navbar.settings')
      },
      showAccountSection() {
        return this.activeSectionTitle === this.$t('dashboard.navbar.account')
      },
    },

    methods: {
      async loadAccount() {
        //TODO finish implementing session management to resolve account
        const account = await accountService.getAccount(store.activeAccountId)
        .then((response) => {
          return response
        })
        this.account = account
      },

      changeTitle(title) {
        this.activeSectionTitle = title
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
