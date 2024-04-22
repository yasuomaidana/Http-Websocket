import React from 'react';
import logo from './logo.svg';
import './App.scss';
import PrintingJobs from './PrintingJobs';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Printing jobs</h1>
      </header>
      <PrintingJobs/>
    </div>
  );
}

export default App;
