import { required, email, sameAs } from '@vuelidate/validators'

export function hasError(v$, fieldName) {
  return v$.$errors.some(error => error.$property === fieldName)
}

export function getErrorMessage(v$, fieldName) {
  const error = v$.$errors.find(error => error.$property === fieldName)
  if (error) {
    return validationMessages[error.$validator]
  }
  return null
}

export const validations = {
  LoginPanel: {
    username: { required },
    password: { required }
  },
  RegisterPanel: {
    username: { required },
    password: { required, sameAs: sameAs('confirmPassword') },
    email: { email },
    confirmPassword: { required, sameAs: sameAs('password') },
    securityQuestion: { required },
    securityAnswer: { required },
  },
}

export const validationMessages = {
  required: 'This field is required.',
  minLength: 'This field must be at least {min} characters long.',
  maxLength: 'This field must be at most {max} characters long.',
  email: 'Please enter a valid email address.',
  sameAs: 'The passwords do not match.',
}