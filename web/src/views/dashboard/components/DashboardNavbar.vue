<template>

    <img src="@/assets/img/logo.png" alt="logo" class="navbar-logo" />

    <div class="navbar-section-list">
        <div 
            v-for="section in sections" 
            :key="section.id"
            :class="{ 'navbar-section': true, 'selected': this.selectedSection == section.id }"
            @click="changeSection(section.id)">

            <!-- <img :src="iconBasePath + section.iconPath" :alt="section.id + '-icon'" class="navbar-icon" /> -->
            <span>{{ this.$t(section.labelKey) }}</span>
        </div>
    </div>
</template>

<script>
import { dashboardState } from '../dashboardState.js';
    export default {
        name: 'DashboardNavbar',

        data() {
            return {
                selectedSection: dashboardState.defaultSection, //default section
                sections: dashboardState.navbarSections,
                iconBasePath: 'src/assets/icon',
            };
        },

        mounted() {
            //set the default section to home
            this.$emit('select-section', this.selectedSection);
        },

        methods: {
            //send new title to parent component
            changeSection(sectionId) {
                this.selectedSection = sectionId;
                dashboardState.setActiveSection(sectionId);
                this.$emit('change-section', sectionId);
            },
        }
    }
</script>