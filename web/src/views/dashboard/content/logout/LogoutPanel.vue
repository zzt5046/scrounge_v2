<template>
    <div class="logout-panel panel">

        <h4 class="logout-message">{{ $t('logout.message') }}</h4>

        <img src="../../../../assets/icon/logout.png" class="logout-icon"/>
        <div class="logout-actions">
            <button class="btn btn-secondary logout-button" @click="back">{{ $t('actions.cancel') }}</button>
            <button class="btn btn-primary logout-button" @click="logout">{{ $t('actions.confirm') }}</button>
        </div>
    </div>
</template>

<script>
import { accountService } from '@/service/.service-registry'
import { dashboardState } from '../../dashboardState';
    export default {
        name: 'LogoutPanel',

        methods: {
            logout() {
                accountService.logout().then(() =>{
                    this.$router.push({ name: 'root' })
                })
            },
            back() {
                dashboardState.setActiveSection(dashboardState.previousSection);
                this.$emit('back', dashboardState.previousSection);
            }
        }
    };
</script>
<style scoped>
    .logout-button {
        width: 30%;
    }
</style>