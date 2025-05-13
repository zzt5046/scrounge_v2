<template>
    <img
        class="info-icon"
        src="../../../assets/icon/info-icon.png"
        :title="title"
    />
</template>
<script>
export default {
    name: 'InfoIcon',
    props: {
        title: {
            type: String,
            default: '',
        },
    },
};
</script>