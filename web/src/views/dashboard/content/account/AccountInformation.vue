<template>
    <div class="account-information" v-if="account">

        <div class="account-information-header">
            <h2>{{ $t('account.information.header') }}</h2>
            <div v-if="editMode" class="account-information-actions">
                <button class="btn btn-primary" @click="editMode = false">{{ $t('actions.cancel') }}</button>
                <button class="btn btn-primary margin-left" @click="updateAccount">{{ $t('actions.save') }}</button>
            </div>
            <div v-else class="account-information-actions">
                <button class="btn btn-primary" @click="editMode = true">{{ $t('actions.edit') }}</button>
            </div>
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
                    <a v-if="!securityAnswerShown" class="show-hide" href="#" @click="showSecurityAnswer">{{
                        $t('actions.show') }}</a>
                    <a v-else href="#" class="show-hide" @click="hideSecurityAnswer">{{ $t('actions.hide') }}</a>
                </div>

                <TextInput v-if="!securityAnswerShown" id="account-securityAnswer-password" v-model="securityAnswer"
                    class="mb-1" type="password" />
                <TextInput v-else id="account-securityAnswer-text" v-model="securityAnswer" class="mb-1" type="text" />

                <h6>{{ $t('fields.confirmSecurityAnswer') }}</h6>

                <TextInput v-if="!securityAnswerShown" id="account-securityAnswerConfirm-password"
                    v-model="securityAnswerConfirm" type="password" />
                <TextInput v-else id="account-securityAnswerConfirm-text" v-model="securityAnswerConfirm" type="text" />

            </div>

        </div>

        <div class="account-information-header">
            <h2>{{ $t('account.settings.header') }}</h2>
        </div>

        <div class="account-settings-content" id="account-settings-edit">
            <div class="account-settings-item">
                <div class="account-settings-item-header">
                    <h6>{{ $t('account.settings.language.header') }}</h6>
                    <InfoIcon :title="$t('account.settings.language.tooltip')" />
                </div>
                <SelectInput v-if="editMode" id="account-settings-language" :options="settings.languageOptions"
                    :placeholder="$t('account.settings.language.placeholder')" v-model="settings.language" />
                <span v-else> {{ languageLabel }} </span>
                </div>
                <div class="account-settings-item">
                    <div class="account-settings-item-header">
                        <h6>{{ $t('account.settings.measurement_system.header') }}</h6>
                        <InfoIcon :title="$t('account.settings.measurement_system.tooltip')" />
                    </div>
                    <SelectInput v-if="editMode" id="account-settings-measurement_system"
                        :options="settings.measurementSystemOptions"
                        :placeholder="$t('account.settings.measurement_system.placeholder')"
                        v-model="settings.measurement_system" />
                    <span v-else> {{ measurementSystemLabel }} </span>
                </div>
                <div class="account-settings-item">
                    <div class="account-settings-item-header">
                        <h6>{{ $t('account.settings.theme.header') }}</h6>
                        <InfoIcon :title="$t('account.settings.theme.tooltip')" />
                    </div>
                    <SelectInput v-if="editMode" id="account-settings-theme-header" :options="settings.themeOptions"
                        :placeholder="$t('account.settings.theme.placeholder')" v-model="settings.theme" />
                    <span v-else> {{ themeLabel }} </span>
                </div>
            </div>

        </div>
</template>
<script>
import TextInput from '../../../../components/core/input/TextInput.vue';
import SelectInput from '../../../../components/core/input/SelectInput.vue';
import InfoIcon from '../../../../components/core/icon/InfoIcon.vue';
import FormsMixin from '../../../../mixins/FormsMixin.vue';
import { accountService } from '@/service/.service-registry';
import { store } from '../../../../store';
export default {
    name: 'AccountInformation',
    mixins: [FormsMixin],
    components: {
        TextInput,
        SelectInput,
        InfoIcon
    },

    data() {
        return {
            account: null,
            editMode: false,
            questions: store.securityQuestions,
            emailAddress: null,
            securityQuestion: null,
            securityAnswer: null,
            securityAnswerConfirm: null,
            securityAnswerShown: false,

            settings: {
                language: null,
                languageOptions: [],
                measurement_system: null,
                measurementSystemOptions: [],
                theme: null,
                themeOptions: [],
            },
            languageLabel: null,
            measurementSystemLabel: null,
            themeLabel: null,
        };
    },

    async created() {
        await this.loadAccount();
        this.loadSettingsOptions();
    },

    methods: {
        async loadAccount() {
            const account = await accountService.getAccount(store.activeAccountId)
            if (account) {
                this.account = account;
                this.emailAddress = account.emailAddress;
                this.securityQuestion = account.securityQuestionId;
                this.loadSettings();
            } else {
                console.error('Account not found with ID: ' + store.activeAccountId);
            }
        },

        async loadSettings(){
            this.settings.language = this.account.settings['LANGUAGE'];
            this.settings.measurement_system = this.account.settings['MEASUREMENT_SYSTEM'];
            this.settings.theme = this.account.settings['THEME'];
            this.languageLabel = this.$t('account.settings.language.' + this.settings.language.toLowerCase());
            this.measurementSystemLabel = this.$t('account.settings.measurement_system.' + this.settings.measurement_system.toLowerCase());
            this.themeLabel = this.$t('account.settings.theme.' + this.settings.theme.toLowerCase());
        },

        async updateAccount() {

            const answerHash = await this.hashStrings(this.securityAnswer).then((hashed) => {
                return hashed
            })

            const accountUpdateRequest = {
                userName: this.account.userName,
                emailAddress: this.emailAddress,
                securityQuestionId: this.securityQuestion,
                securityQuestionAnswer: encodeURI(answerHash),
                settings: {
                    LANGUAGE: this.settings.language,
                    MEASUREMENT_SYSTEM: this.settings.measurement_system,
                    THEME: this.settings.theme,
                },
            }

            accountService.updateAccount(store.activeAccountId, accountUpdateRequest).then(() => {
                this.loadAccount();
                this.editMode = false;
                this.securityAnswer = null;
                this.securityAnswerConfirm = null;
            }).catch((error) => {
                console.error('Error updating account:', error);
            });
        },

        //load all options for the select inputs
        async loadSettingsOptions() {
            const settings = store.allAccountSettings;
            settings['LANGUAGE'].forEach((option) => {
                let messageKey = 'account.settings.language.' + option.toLowerCase();
                this.settings.languageOptions.push({
                    id: option,
                    name: this.$t(messageKey),
                });
            });

            settings['MEASUREMENT_SYSTEM'].forEach((option) => {
                let messageKey = 'account.settings.measurement_system.' + option.toLowerCase();
                this.settings.measurementSystemOptions.push({
                    id: option,
                    name: this.$t(messageKey),
                });
            });

            settings['THEME'].forEach((option) => {
                let messageKey = 'account.settings.theme.' + option.toLowerCase();
                this.settings.themeOptions.push({
                    id: option,
                    name: this.$t(messageKey),
                });
            });
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
    .margin-left {
        margin-left: 1rem;
    }
    .show-hide {
        cursor: pointer;
        font-size: 0.8rem;
    }
    .info-icon {
        width: 1.1rem;
        height: 1.1rem;
        margin-left: 0.5rem;
    }
</style>