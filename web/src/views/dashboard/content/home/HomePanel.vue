<template>
    <div class="dashboard-content home-panel">
        Welcome home {{ username }}! All dashboard content should appear here.<br>
        <img src="@/assets/img/logo.png" alt="Dashboard Hero" class="dashboard-hero"/>
    </div>
</template>
<script>
import { store } from '../../../../store'
export default {
    name: 'HomePanel',
    data() {
        return {
            username: store.activeAccountUsername,
        };
    },
};
</script>