import React from "react";
import { NavLink } from "react-router-dom";
import NewJobForm from "./NewJobForm";
import PrintingJobs from "./PrintingJobs";


export const PrintingView = () => {
  return (
    <>
      <header className="App-header">
        <h1>Jobs</h1>
      </header>
      <div>
        <NewJobForm />
      </div>
      <div>
        <PrintingJobs />
      </div>
      <div>Jobs Content</div>
      <NavLink to="temperature"><button>Go to Temperature</button></NavLink>

    </>
  );
};
