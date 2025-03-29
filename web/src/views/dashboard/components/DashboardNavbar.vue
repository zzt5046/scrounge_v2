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
    export default {
        name: 'DashboardNavbar',

        data() {
            return {
                selectedSection: 'home', //default section
                sections: [
                    { name: 'home', title: this.$t('dashboard.navbar.home') },
                    { name: 'recipes', title: this.$t('dashboard.navbar.recipes') },
                    { name: 'account', title: this.$t('dashboard.navbar.account') },
                    { name: 'settings', title: this.$t('dashboard.navbar.settings') }
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
                this.selectedSection = newSectionName;
                this.$emit('changeTitle', this.sections.find(s => s.name === this.selectedSection).title);
            }
        }
    }
</script>