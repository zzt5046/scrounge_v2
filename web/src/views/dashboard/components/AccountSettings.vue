<template>
<div>
    Account Settings placeholder

    <SelectInput>
        <option v-for="setting in settings" :key="setting.key" :value="setting.value">
            {{ setting.name }}
        </option>
    </SelectInput>
</div>
</template>

<script>

import SelectInput from '@/components/core/input/SelectInput.vue'
import { accountService } from '@/service/.service-registry'

export default {
  name: 'AccountSettings',
    components: {
        SelectInput
    },

  props: {
    accountId: {
      type: String,
      required: true
    }
  },

  data() {
    return {
      settings: [],
    };
  },

  created() {
    this.loadSettings()
  },

  methods: {
    async loadSettings() {
      const settings = await accountService.getAccountSettings(this.accountId)
      this.settings = settings
      console.log(this.settings)
    },

    async saveSettings() {
      await accountService.updateSettings(this.accountId, {
        settings: this.account.settings
      })
      .then((response) => {
        return response
      })
    }
  },
};
</script>