import React from "react";
import { NavLink } from "react-router-dom";
import NewJobForm from "./NewJobForm";
import PrintingJobs from "./PrintingJobs";
import { Grid } from "@mui/material";


export const PrintingView = () => {
  return (
    <>
      <header className="App-header">
        <h1>Jobs</h1>
      </header>
      <Grid container spacing={2}> {/* Wrap the components into a Material-UI grid */}
        <Grid item xs={6}> {/* Make the left column occupy 50% of the screen */}
          <PrintingJobs /> 
        </Grid>
        <Grid item xs={6}> {/* Make the right column occupy 50% of the screen */}
          <NewJobForm /> 
        </Grid>
      </Grid>
      <div>Jobs Content</div>
      <NavLink to="temperature">
        <button>Go to Temperature</button>
      </NavLink>
    </>
  );
};