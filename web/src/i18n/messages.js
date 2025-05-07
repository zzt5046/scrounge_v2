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
      save: 'Save',
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
      done: 'Done',
      move_up: 'Move Up',
      move_down: 'Move Down',
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
    recipe: {
      list: {
        header: 'My Recipes',
        no_recipes: 'No recipes found. Add a recipe to see it listed here!',
        deleted: 'Deleted recipe: ',
      },
      information: 'Information',
      name: 'Name',
      description: 'Description',
      add_description: 'Add description here.',
      ingredients: 'Ingredients',
      no_ingredients: 'No ingredients found.',
      directions: 'Directions',
      add_direction: 'Add direction.',
      no_directions: 'No directions found.',
      notes: 'Notes',
      add_notes: 'Add notes here.',
      no_notes: 'No notes found.',
      make_public: 'Make this recipe public?',
      make_public_desc: 'This will allow other users to see this recipe.',
      actions: {
        add: '+ Add Recipe',
        edit: 'Edit Recipe',
        save: 'Save Recipe',
        favorite: 'Favorite Recipe',
        delete: 'Delete Recipe',
        cancel: 'Cancel'
      },
      ingredient: {
        name: 'Name',
        quantity: 'Quantity',
        unit: 'Unit',
        add: '+ Add',
      },
      direction: {
        add: '+ Add',
      },
      inspect: {
        submitted: 'Submitted by:',
      },
      create: {
        header: 'Create Recipe',
        success: 'Recipe created successfully.',
        error: 'An error has occurred. Please try again later.'
      },
      update: {
        success: 'Recipe updated successfully.',
        error: 'An error has occurred. Please try again later.'
      },
    },
  }
}

export default messages
