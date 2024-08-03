describe('Logout', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        token: 'mockToken',
        type: 'Bearer',
        id: 1,
        username: 'username',
        firstName: 'firstname',
        lastName: 'lastname',
        admin: true
      }
    }).as('login');

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName="email"]').type('yoga@studio.com');
    cy.get('input[formControlName="password"]').type('test!1234');
    cy.get('button[type="submit"]').click();


  });

  it('Logout successfully', () => {
    cy.get('.link').contains('Logout').click();
    cy.url().should('include', '/');
  });

});
