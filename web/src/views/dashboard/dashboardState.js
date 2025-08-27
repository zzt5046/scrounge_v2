import { reactive } from 'vue'

export const DASHBOARD_HOME = 'home';
export const DASHBOARD_RECIPES = 'recipes';
export const DASHBOARD_SETTINGS = 'settings';
export const DASHBOARD_LOGOUT = 'logout';

export const dashboardState = reactive({

    // Sections can be added or removed from the navbar
    // Don't forget to add the key in messages.js, icon in assets, 
    // and showWhateverSection() function in DashboardView.vue

    navbarSections: [
        {
            id: DASHBOARD_HOME,
            labelKey: 'dashboard.navbar.home',
            iconPath: '/navbar/home.png',
        },
        {
            id: DASHBOARD_RECIPES,
            labelKey: 'dashboard.navbar.recipes',
            iconPath: '/navbar/recipes.png',
        },
        {
            id: DASHBOARD_SETTINGS,
            labelKey: 'dashboard.navbar.settings',
            iconPath: '/navbar/settings.png',
        },
        {
            id: DASHBOARD_LOGOUT,
            labelKey: 'dashboard.navbar.logout',
            iconPath: '/navbar/logout.png',
        },
    ],

    // The currently selected dashboard view (default is 'home')
    defaultSection: 'home',
    activeSection: 'home',
    setActiveSection(sectionId) {
        this.activeSection = sectionId
    },

    previousSection: '',
    setPreviousSection(sectionId) {
        if (sectionId !== 'logout') {
            this.previousSection = sectionId
        }
    },

    getLabelKey(sectionId) {
        const section = this.navbarSections.find(section => section.id == sectionId)
        return section ? section.labelKey : null
    }

})