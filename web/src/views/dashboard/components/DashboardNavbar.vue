<template>

    <img src="@/assets/img/logo.png" alt="logo" class="navbar-logo" />

    <div class="navbar-section-list">
        <div 
            v-for="section in sections" 
            :key="section.id"
            :class="{ 'navbar-section': true, 'selected': activeSection == section.id }"
            @click="changeSection(section.id)">

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

        computed: {
            activeSection() {
                return dashboardState.activeSection;
            }
        },

        mounted() {
            //set the default section to home
            this.$emit('select-section', this.selectedSection);
        },

        methods: {
            //send new title to parent component
            changeSection(sectionId) {
                dashboardState.setPreviousSection(this.selectedSection);
                this.selectedSection = sectionId;
                dashboardState.setActiveSection(sectionId);
                this.$emit('change-section', sectionId);
            },
        }
    }
</script>