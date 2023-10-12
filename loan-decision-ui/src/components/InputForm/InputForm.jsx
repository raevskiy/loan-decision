import React, { useState } from 'react';
import PropTypes from 'prop-types';
import '../App/App.css';

const initialValues = {
	personalCode: '',
	loanPeriodMonths: '',
	loanAmount: ''
}

const InputForm = ({ change }) => {
	const [state, setState] = useState(initialValues);

	const handleChange = e => {
		enforceValidIntegerIfNecessary(e);
		updateState(e);
	};

	const enforceValidIntegerIfNecessary = e => {
		if (e.target.value !== "" && e.target.type === "number" && e.target.inputMode === "numeric") {
			e.target.value = Math.trunc(e.target.value);
		}
	};

	const enforceMinMax = e => {
		if (e.target.value !== "" && e.target.type === "number") {
			let { min, max } = e.target;
			if (parseInt(e.target.value) < parseInt(min)) {
				e.target.value = min;
			}
			if (parseInt(e.target.value) > parseInt(max)) {
				e.target.value = max;
			}

			updateState(e);
		}
	};

	const updateState = e => {
		let { value, name } = e.target;
		setState({
			...state,
			[name]: value
		});
	};

	const handleSubmit = () => {
		change(state);
		setState(initialValues);
	};

	return (
		<>
			<div className="row">
				<div className="col m4 s12">
					<label htmlFor="personalCode">Personal Code</label>
					<input
						id="personalCode"
						name="personalCode"
						type="string"
						placeholder="xxxxxxxxxxx"
						value={state.personalCode}
						onChange={handleChange}
					/>
				</div>

				<div className="col m4 s12">
					<label htmlFor="loanAmount">Loan Amount</label>
					<input
						id="loanAmount"
						name="loanAmount"
						type="number"
						min="2000"
						max="10000"
						placeholder="2000.00"
						value={state.loanAmount}
						onBlur={enforceMinMax}
						onChange={handleChange}
					/>
				</div>

				<div className="col m4 s12">
					<label htmlFor="loanPeriodMonths">Duration (in months)</label>
					<input
						id="loanPeriodMonths"
						name="loanPeriodMonths"
						type="number"
						inputMode="numeric"
						min="12"
						max="60"
						placeholder="12"
						value={state.loanPeriodMonths}
						onBlur={enforceMinMax}
						onChange={handleChange}
					/>
				</div>
			</div>
			<div className="center">
				<button
					id="calculate-btn"
					className="calculate-btn"
					type="button"
					disabled={state.personalCode === ''
						|| state.loanAmount === '' || state.loanAmount < 2000 || state.loanAmount > 10000
						|| state.loanPeriodMonths === '' || state.loanPeriodMonths < 12 || state.loanPeriodMonths > 60}
					onClick={handleSubmit}
				>
					Calculate approved loan amount
				</button>
			</div>
		</>
	);
};

InputForm.propTypes = {
	change: PropTypes.func.isRequired
};

export default InputForm;
