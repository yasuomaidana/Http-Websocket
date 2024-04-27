import React, { useState, useEffect } from "react";
import { Endpoints } from "./url_config";

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
      <ul id="job-list">
        {jobs.map((job,index) => (
          <li key={index}>
            Job {job.id}: {job.name} Satus: {job.status} - Remaining Time: {job.totalTime}
          </li>
        ))}
      </ul>       
    </div>
  );
};

export default PrintingJobs;
