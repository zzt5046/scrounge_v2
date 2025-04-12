const messages = {
  en: {
    common: {
      required: 'Required'
    },
    fields: {
      username: 'Username',
      password: 'Password',
      confirmPassword: 'Confirm Password',
      firstName: 'First Name',
      lastName: 'Last Name',
      emailAddress: 'Email Address',
      phoneNumber: 'Phone Number',
      securityQuestion: 'Security Question',
      securityAnswer: 'Security Question Answer'
    },
    actions: {
      login: 'Login',
      logout: 'Logout',
      register: 'Register',
      submit: 'Submit',
      cancel: 'Cancel',
      back: 'Back',
      confirm: 'Confirm',
      continue: 'Continue',
      add: 'Add',
      remove: 'Remove',
      delete: 'Delete',
      yes: 'Yes',
      no: 'No',
      done: 'Done'
    },
    home: {
      login: {
        header: 'LOGIN',
        misc: {
          registerDisclaimer1: "Don't have an account?",
          registerDisclaimer2: 'Sign Up'
        },
        failed: 'Username or password is incorrect.',
        error: 'An error has occurred. Please try again later.'
      },
      register: {
        header: 'Create your Account',
        nameTaken: 'Sorry, that username is not available.',
        success: 'Account created successfully. Please login to continue.',
      },
    },
    dashboard: {
      header: 'Dashboard',
      navbar: {
        home: 'Home',
        recipes: 'Recipes',
        account: 'Account',
        settings: 'Settings',
        logout: 'Logout'
      },
      footer: 'Footer',
    },
  }
}

export default messages
