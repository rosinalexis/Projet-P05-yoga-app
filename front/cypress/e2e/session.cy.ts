describe('Session', () => {
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

  const SESSION = {
    id: 1,
    name: 'session',
    date: Date.now(),
    teacher_id: 1,
    description: 'description for the session',
    users: [1],
    createdAt: Date.now(),
    updatedAt: Date.now(),
  };

  const SESSIONS_LIST = [SESSION];

  const TEACHERS_LIST = [
    {id: 1, lastName: 'teacher', firstName: 'uno'},
    {id: 2, lastName: 'teacher', firstName: 'duo'}
  ];

  const loginAndIntercept = () => {
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS).as('login');
    cy.intercept('GET', '/api/session', SESSIONS_LIST).as('session');
    cy.get('input[formControlName="email"]').type('yoga@studio.com');
    cy.get('input[formControlName="password"]').type('test!1234');
    cy.get('button[type="submit"]').click();
  };

  beforeEach(() => {
    loginAndIntercept();
  });

  it('should display the list of sessions', () => {
    cy.get('mat-card-title').should('contain.text', 'Rentals available');
    cy.get('mat-card').should('have.length', 2);
  });

  it('should display the create session form', () => {
    cy.get('button[routerLink="create"]').click();
    cy.get('mat-card-title').should('contain.text', 'Create session');
  });

  it('should navigate to session detail page on button click', () => {
    cy.intercept('GET', '/api/sessions/detail/1', SESSION).as('sessionDetail');
    cy.get('button').contains('Detail').click();
    cy.url().should('include', '/sessions/detail/1');
  });

  it('should display all required fields and enable submission when valid', () => {
    cy.intercept('GET', '/api/teacher', TEACHERS_LIST).as('teacherList');
    cy.get('button[routerLink="create"]').click();
    cy.get('input[formControlName="name"]').type('session');
    cy.get('input[formControlName="date"]').type('2024-01-02');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').first().click();
    cy.get('textarea[formControlName="description"]').type('A relaxing yoga session.');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/sessions');
  });

  it('should navigate to session update page on button click', () => {
    cy.intercept('GET', '/api/sessions/update/1', SESSIONS_LIST[0]).as('session');
    cy.get('button').contains('Edit').click();
    cy.url().should('include', '/sessions/update');
  });
});
