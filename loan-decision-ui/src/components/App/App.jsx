import React, { useState } from 'react';
import { v4 as uuidv4 } from 'uuid';
import 'materialize-css/dist/css/materialize.min.css';
import './App.css';
import InputForm from '../InputForm/InputForm';
import Info from '../Info/Info';
import { encode } from "base-64";

const App = () => {
  const initialState = () => [];
  const [state, setState] = useState(initialState);

  const handleChange = val => {
    let url = `http://localhost:8080/v1/loan/decision?personalCode=${val.personalCode}&loanAmount=${val.loanAmount}&loanPeriodMonths=${val.loanPeriodMonths}`;

    fetch(url, {method:'GET', headers: {'Content-Type': 'text/plain','Authorization': 'Basic ' + encode('manager:manager')}})
        .then(res => {
            if (!res.ok) {
                throw new Error(`${url} returned unsuccessful response`);
            }
            return res.text()
        })
        .then(text => {
          val.approvedAmount = text;
          addNewModel(val);
        })
        .catch(error => {
            console.error(error);
            val.approvedAmount = "N/A";
            addNewModel(val);
        })
  };

  const addNewModel = val => {
      val.id = uuidv4();
      let newVal = [...state, val];
      let len = newVal.length;
      if (len > 4) newVal = newVal.slice(1, len);
      setState(newVal);
  }

  const handleDelete = id => {
    let newState = state.filter(i => {
      return i.id !== id;
    });
    setState(newState);
  };

  return (
    <div className='container'>
      <div className='row center'>
        <h1 className='white-text'> Loan Decision Assistant </h1>
      </div>
      <div className='row'>
        <div className='col m12 s12'>
          <InputForm change={handleChange} />
          <div>
            <div className='row center'>
              <h4 className='white-text'>Recent Requests</h4>
            </div>
            <div className='data-container row'>
              {state.length > 0 ? (
                <>
                  {state.slice(0).reverse().map(info => (
                    <Info
                      key={info.id}
                      id={info.id}
                      personalCode={info.personalCode}
                      loanPeriodMonths={info.loanPeriodMonths}
                      loanAmount={info.loanAmount}
                      approvedAmount={info.approvedAmount}
                      deleteCard={handleDelete}
                    />
                  ))}
                </>
              ) : (
                  <div className='center white-text'>No recent requests found</div>
                )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default App;
