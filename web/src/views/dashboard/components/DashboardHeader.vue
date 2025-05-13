<template>
    <div class="dashboard-header">
        <h2>{{ title }}</h2>
    </div>
</template>

<script>

export default {
    name: 'DashboardHeader',
    props: {
        title: {
            type: String,
            required: true
        }
    },
}
</script>