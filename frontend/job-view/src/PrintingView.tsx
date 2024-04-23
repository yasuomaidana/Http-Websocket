import React from "react";
import { NavLink } from "react-router-dom";
import NewJobForm from "./NewJobForm";
import PrintingJobs from "./PrintingJobs";


export const PrintingView = () => {
  return (
    <>
      <header className="App-header">
        <h1>Printing jobs</h1>
      </header>
      <div>
        <NewJobForm />
      </div>
      <div>
        <PrintingJobs />
      </div>
      <NavLink to="temperature">Go to Temperature</NavLink>

    </>
  );
};
