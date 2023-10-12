describe('InputForm Component', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('prevents invalid input', () => {
    cy.get('#loanPeriodMonths')
      .type('12.5')
      .should('have.value', '12');
    cy.get('#loanPeriodMonths')
        .clear()
        .type('11')
        .blur()
        .should('have.value', '12');
    cy.get('#loanPeriodMonths')
        .clear()
        .type('61')
        .blur()
        .should('have.value', '60');
    cy.get('#loanAmount')
        .type('100')
        .blur()
        .should('have.value', '2000');
    cy.get('#loanAmount')
        .type('100000')
        .blur()
        .should('have.value', '10000');
  });

  context('Form submission', () => {
    it('Adds a new loan request data', () => {
      let personalCode = '49002010998';
      const loanPeriodMonths = '12';
      const loanAmount = '2999.99';

      cy.get('#personalCode')
        .type(personalCode)
        .should('have.value', personalCode);
      cy.get('#loanPeriodMonths')
          .type(loanPeriodMonths)
          .should('have.value', loanPeriodMonths);
      cy.get('#loanAmount')
          .type(loanAmount)
          .should('have.value', loanAmount);

      cy.get('#calculate-btn').click();

      cy.get("[data-test='personalCode']").should('contain', personalCode);
      cy.get("[data-test='loanPeriodMonths']").should('contain', loanPeriodMonths);
      cy.get("[data-test='loanAmount']").should('contain', loanAmount);
      cy.get("[data-test='approvedAmount']").should('contain', '12000.00');
    });
  });
});
