<template>

    <img src="@/assets/img/logo.png" alt="logo" class="navbar-logo" />

    <div class="navbar-section-list">
        <div 
            v-for="section in sections" 
            :key="section.name"
            :class="{ 'navbar-section': true, 'selected': section.name === this.selectedSection }"
            @click="changeSection(section.name)">

            <span>{{ section.title }}</span>
        </div>
    </div>
</template>

<script>
import { store } from '../../../store'
    export default {
        name: 'DashboardNavbar',

        data() {
            return {
                selectedSection: 'home', //default section
                sections: [
                    { name: 'home', title: this.$t('dashboard.navbar.home') },
                    { name: 'recipes', title: this.$t('dashboard.navbar.recipes') },
                    { name: 'account', title: this.$t('dashboard.navbar.account') },
                    { name: 'logout', title: this.$t('dashboard.navbar.logout') },
                ]
            };
        },

        mounted() {
            //set the default section to home
            this.$emit('select-section', this.section);
        },

        methods: {
            //send new title to parent component
            changeSection(newSectionName) {
                store.setCurrentDashboardView(newSectionName);
                this.selectedSection = newSectionName;
                if(this.selectedSection === 'logout') {
                    this.$emit('logout');
                } else {
                    //send the title of the selected section to the parent component
                    this.$emit('changeTitle', this.sections.find(s => s.name === this.selectedSection).title);
                }
            }
        }
    }
</script>