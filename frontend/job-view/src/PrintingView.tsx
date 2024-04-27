import React from "react";
import { NavLink } from "react-router-dom";
import NewJobForm from "./NewJobForm";
import PrintingJobs from "./PrintingJobs";
import { Box, Button, Grid } from "@mui/material";

export const PrintingView = () => {
  return (
    <>
      <header className="App-header">
        <h1>Jobs</h1>
      </header>
      <Grid container spacing={2}>
        {/* Wrap the components into a Material-UI grid */}
        <Grid item xs={12} sm={6} lg={9}>
          {/* Make the left column occupy 50% of the screen */}
          <PrintingJobs />
        </Grid>
        <Grid item xs={12} sm={6} lg={3}>
          {/* Make the right column occupy 50% of the screen */}
          <NewJobForm />
        </Grid>
      </Grid>

      <Grid container justifyContent="center" marginTop={2}> {/* Center the button */}
        <Grid item xs={12} sm={9} md={4}> {/* Responsive width */}
          <Button variant="contained" fullWidth component={NavLink} to="temperature">
            Go to Temperature
          </Button>
        </Grid>
      </Grid>    
    </>
  );
};
