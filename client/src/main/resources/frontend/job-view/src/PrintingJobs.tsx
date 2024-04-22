import React, {useState, useEffect} from 'react'

interface Job {
    id: number;
    status: string;
    remainingTime: number; 
  }

const PrintingJobs = () => {
    const [jobs, setJobs] = useState<Job[]>([]);

    useEffect(() => {
      const websocket = new WebSocket("ws://localhost:8080/jobs/status");
  
      websocket.onmessage = (event) => {
        const jobUpdates: Job[] = JSON.parse(event.data);
        setJobs(jobUpdates);
      };
  
      // Cleanup on unmount
      return () => websocket.close(); 
    }, []); 
  
    return (
      <div>
        <h2>Printing Jobs List:</h2>
        <ul id="job-list">
          {jobs.map((job) => (
            <li key={job.id}>
              Job {job.id}: {job.status} - Remaining Time: {job.remainingTime}
            </li>
          ))}
        </ul>
      </div>
    );
}

export default PrintingJobs