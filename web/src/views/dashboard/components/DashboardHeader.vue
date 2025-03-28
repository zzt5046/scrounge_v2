<template>
    <div class="dashboard-header">
        <h1>{{ $t('dashboard.header') }} - Welcome {{ account.userName }}.</h1>
    </div>
</template>

<script>

export default {
    name: 'DashboardHeader',
    props: {
        account: {
            type: Object,
            required: true
        }
    }
}
</script>