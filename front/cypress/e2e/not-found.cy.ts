describe('Not found', () => {
  it('Not Found successfully', () => {
    cy.visit('/url-unknow');
    cy.url().should('include', '/404');
  });
});
