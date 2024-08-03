describe('Account', () => {
  const ADMIN_DETAILS = {
    token: 'jwt',
    type: 'Bearer',
    id: 1,
    email: 'yoga@studio.com',
    firstName: 'Admin',
    lastName: 'ADMIN',
    admin: true,
    createdAt: Date.now(),
    updatedAt: Date.now(),
  };

  const loginAndSetup = () => {
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS).as('login');
    cy.intercept('GET', `/api/user/${ADMIN_DETAILS.id}`, ADMIN_DETAILS).as('getUser');
    cy.get('input[formControlName="email"]').type(ADMIN_DETAILS.email);
    cy.get('input[formControlName="password"]').type('test!1234');
    cy.get('button[type="submit"]').click();
    cy.wait('@login');
  };

  beforeEach(() => {
    loginAndSetup();
  });

  it('should display account info correctly', () => {
    cy.get('.link').contains('Account').click();
    cy.url().should('include', '/me');

    cy.get('p').contains('Name:')
      .should('have.text', `Name: ${ADMIN_DETAILS.firstName} ${ADMIN_DETAILS.lastName}`);

    cy.get('.mat-icon').click();
  });
});
