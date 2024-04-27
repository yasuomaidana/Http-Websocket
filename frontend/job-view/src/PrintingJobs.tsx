import React, { useState, useEffect } from "react";
import { Endpoints } from "./url_config";
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

interface Job {
  id: number;
  name: string;
  status: string;
  totalTime: number; // Notice this field
}

const PrintingJobs = () => {
  const [jobs, setJobs] = useState<Job[]>([]);

  useEffect(() => {
    const websocket = new WebSocket(Endpoints.websocket.jobStatus);
    let isConnected = false;

    websocket.onopen = () => {
      isConnected = true;
    };
    websocket.onmessage = (event) => {
      const jobUpdates:Job[] = JSON.parse(event.data);
      setJobs(jobUpdates);
    }

    // Cleanup on unmount
    return () => {
      if (isConnected) {
        websocket.close();
      }
    };
  }, []);
  return (
    <div>
    <h2>Printing Jobs List:</h2>
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 300 }} aria-label="printing jobs table"> 
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell> 
            <TableCell align="right">Name</TableCell>
            <TableCell align="right">Status</TableCell>
            <TableCell align="right">Remaining Time</TableCell> 
          </TableRow>
        </TableHead>
        <TableBody>
          {jobs.map((job) => (
            <TableRow key={job.id}>
              <TableCell>{job.id}</TableCell>
              <TableCell align="right">{job.name}</TableCell>
              <TableCell align="right">{job.status}</TableCell>
              <TableCell align="right">{job.totalTime}</TableCell> 
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  </div>
  );
};

export default PrintingJobs;
