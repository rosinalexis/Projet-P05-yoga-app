describe('Login', () => {
  const fillLoginForm = (email: string, password: string) => {
    cy.get('input[formControlName="email"]').type(email);
    cy.get('input[formControlName="password"]').type(password);
  };

  beforeEach(() => {
    cy.visit('/login');
  });

  it('should display the login form with correct fields', () => {
    cy.get('mat-card-title').should('contain.text', 'Login');
    cy.get('input[formControlName="email"]').should('exist');
    cy.get('input[formControlName="password"]').should('exist');
    cy.get('button[type="submit"]').should('exist').and('be.disabled');
    cy.get('mat-icon').should('contain.text', 'visibility_off');
  });

  it('should enable the submit button when the form is filled out correctly', () => {
    fillLoginForm('mary.jones@example.com', 'password123');
    cy.get('button[type="submit"]').should('not.be.disabled');
  });

  it('should navigate to /sessions on successful login', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        token: 'mockToken',
        type: 'Bearer',
        id: 1,
        username: 'maryjones',
        firstName: 'Mary',
        lastName: 'Jones',
        admin: false
      }
    }).as('loginRequest');

    fillLoginForm('mary.jones@example.com', 'password123');
    cy.get('button[type="submit"]').click();

    cy.wait('@loginRequest');
    cy.url().should('include', '/sessions');
  });

  it('should show an error message if login fails', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 400,
      body: {message: 'Login failed'}
    }).as('loginRequest');

    fillLoginForm('mary.jones@example.com', 'password123');
    cy.get('button[type="submit"]').click();

    cy.wait('@loginRequest');
    cy.get('.error').should('contain.text', 'An error occurred');
  });

  it('should toggle password visibility when icon is clicked', () => {
    cy.get('input[formControlName="password"]').should('have.attr', 'type', 'password');
    cy.get('button[mat-icon-button]').click();
    cy.get('input[formControlName="password"]').should('have.attr', 'type', 'text');
  });
});
