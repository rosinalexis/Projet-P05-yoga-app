describe('Register', () => {
  const fillRegistrationForm = (firstName: string, lastName: string, email: string, password: string) => {
    cy.get('input[formControlName="firstName"]').type(firstName);
    cy.get('input[formControlName="lastName"]').type(lastName);
    cy.get('input[formControlName="email"]').type(email);
    cy.get('input[formControlName="password"]').type(password);
  };

  beforeEach(() => {
    cy.visit('/register');
  });

  it('should display the registration form with correct fields', () => {
    cy.get('mat-card-title').should('contain.text', 'Register');
    cy.get('button[type="submit"]').should('exist').and('be.disabled');
  });

  it('should enable the submit button when the form is filled out correctly', () => {
    fillRegistrationForm('toto', 'boedo', 'totoboedo@toto.com', 'password123456');
    cy.get('button[type="submit"]').should('not.be.disabled');
  });

  it('should navigate to login page on successful registration', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 200,
      body: {}
    }).as('registerRequest');

    fillRegistrationForm('toto', 'boedo', 'totoboedo@toto.com', 'password123456');
    cy.get('button[type="submit"]').click();
    cy.wait('@registerRequest');
    cy.url().should('include', '/login');
  });

  it('should show an error message if registration fails', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: {message: 'Registration failed'}
    }).as('registerRequest');

    fillRegistrationForm('toto', 'boedo', 'totoboedo@toto.com', 'password123456');
    cy.get('button[type="submit"]').click();
    cy.wait('@registerRequest');
    cy.get('.error').should('contain.text', 'An error occurred');
  });
});
