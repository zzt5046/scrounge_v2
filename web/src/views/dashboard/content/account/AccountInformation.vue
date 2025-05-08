<template>
    <div class="account-information" v-if="account">

        <div class="account-information-header">
            <h2>{{ $t('account.information.header') }}</h2>
            <button v-if="!editMode" class="btn btn-primary" @click="editMode = true">{{ $t('actions.edit') }}</button>
            <button v-else class="btn btn-primary" @click="updateAccount">{{ $t('actions.save') }}</button>
        </div>

        <div class="account-information-content" id="account-information">
            <div class="account-information-item">
                <h6>{{ $t('fields.username') }}</h6>
                <span id="account-information-username">{{ account.userName }}</span>
            </div>
            <div class="account-information-item">
                <h6>{{ $t('fields.emailAddress') }}</h6>
                <span v-if="!editMode">{{ account.emailAddress }}</span>
                <TextInput v-else id="account-email-address" v-model="emailAddress" />
            </div>
            <div v-if="editMode" class="account-information-item">
                <h6>{{ $t('fields.securityQuestion') }}</h6>
                <SelectInput id="account-securityQuestion" :options="questions"
                    :placeholder="$t('fields.selectNewSecurityQuestion')" v-model="securityQuestion" />
            </div>
            <div v-if="editMode" class="account-information-item">
                <div class="security-answer-header">
                    <h6 id="security-answer-header">{{ $t('fields.newSecurityAnswer') }}</h6>
                    <a v-if="!securityAnswerShown" href="#" @click="showSecurityAnswer">{{ $t('actions.show') }}</a>
                    <a v-else href="#" @click="hideSecurityAnswer">{{ $t('actions.hide') }}</a>
                </div>

                <TextInput v-if="!securityAnswerShown" id="account-securityAnswer-password" v-model="securityAnswer" class="mb-1" type="password" />
                <TextInput v-else id="account-securityAnswer-text" v-model="securityAnswer" class="mb-1" type="text" />

                <h6>{{ $t('fields.confirmSecurityAnswer') }}</h6>

                <TextInput v-if="!securityAnswerShown" id="account-securityAnswerConfirm-password" v-model="securityAnswerConfirm" type="password" />
                <TextInput v-else id="account-securityAnswerConfirm-text" v-model="securityAnswerConfirm" type="text" />

            </div>

        </div>

        <div class="account-information-header">
            <h2>{{ $t('account.settings.header') }}</h2>
        </div>

        <div class="account-information-content" id="account-settings">

        </div>

    </div>
</template>
<script>
import TextInput from '../../../../components/core/input/TextInput.vue';
import SelectInput from '../../../../components/core/input/SelectInput.vue';
import { accountService } from '@/service/.service-registry';
import { store } from '../../../../store';
export default {
    name: 'AccountInformation',

    components: {
        TextInput,
        SelectInput,
    },

    data() {
        return {
            account: null,
            editMode: false,
            questions: store.securityQuestions,
            accountSettings: {},
            emailAddress: null,
            securityQuestion: null,
            securityAnswerShown: false,
            securityAnswer: null,
            securityAnswerConfirm: null,
        };
    },

    created() {
        this.loadAccount();
    },

    methods: {
        async loadAccount() {
            const account = await accountService.getAccount(store.activeAccountId)
            if (account) {
                this.account = account;
                this.emailAddress = account.emailAddress;
            } else {
                console.error('Account not found with ID: ' + store.activeAccountId);
            }
        },

        updateAccount() {
            // Save the account information
            this.editMode = false;
        },

        showSecurityAnswer() {
            this.securityAnswerShown = true;
        },
        hideSecurityAnswer() {
            this.securityAnswerShown = false;
        },
    },
};
</script>
<style scoped>
    #security-answer-header {
        margin-right: 1rem;
    }
</style>