import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.scss';

import React from 'react'
import Temperature from './Temperature';
import { PrintingView } from './PrintingView';

function App() {
  return (
    <div className="App">
      <BrowserRouter basename={process.env.PUBLIC_URL ?? process.env.REACT_APP_BASENAME}>
            <Routes>
              <Route path="" element={<PrintingView/>} />
              {/* <Route path="job" element={<PrintingView/>} /> */}
              <Route path="temperature" element={<Temperature/>}/>
            </Routes>
          </BrowserRouter>
    </div>
  );
}

export default App;
