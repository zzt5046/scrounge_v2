<template>
    <div>
        <span class="asterisk">*</span>
        <span>{{ $t('validations.required_note') }}</span>
    </div>
</template>
<script>
export default {
  name: 'RequiredNote',
}
</script>
<style scoped>
    .asterisk {
        color: red;
        font-weight: bold;
        margin-right: 4px;
    }
</style>