import React from 'react';
import PropTypes from 'prop-types';

const Info = ({ personalCode, loanPeriodMonths, id, loanAmount, approvedAmount, deleteCard }) => {
  const handleDelete = () => {
    deleteCard(id);
  };

  return (
    <div className="col m6 s12">
      <div className="card">
        <div className="card-content">
          <span className="card-title" data-test="approvedAmount">
            Approved Amount: {approvedAmount}
          </span>
          <div className="card-data">
            <span data-test="personalCode">Personal Code: {personalCode}</span>
            <span data-test="loanPeriodMonths">Loan Period: {loanPeriodMonths} months</span>
            <span data-test="loanAmount">Requested Amount: {loanAmount}</span>
          </div>

          <button className="delete-btn" onClick={handleDelete}>
            X
          </button>
        </div>
      </div>
    </div>
  );
};

Info.propTypes = {
  personalCode: PropTypes.string,
  loanPeriodMonths: PropTypes.string,
  id: PropTypes.string,
  loanAmount: PropTypes.string,
  approvedAmount: PropTypes.string,
  deleteCard: PropTypes.func
};

export default Info;
