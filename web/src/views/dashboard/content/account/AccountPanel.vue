<template>
    <div class="account-panel">
        <AccountInformation />
    </div>
</template>
<script>
import AccountInformation from './AccountInformation.vue';
export default {
    name: 'AccountPanel',
    components: {
        AccountInformation
    },
};
</script>