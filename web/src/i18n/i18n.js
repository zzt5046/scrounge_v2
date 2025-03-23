import { createI18n } from 'vue-i18n'
import messages from '@/i18n/messages'

const i18n = createI18n({
  locale: 'en',
  messages: messages
})

export default i18n
