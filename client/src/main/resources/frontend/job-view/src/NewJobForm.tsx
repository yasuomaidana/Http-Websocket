import React, { useState } from 'react';

const NewJobForm: React.FC = () => {
  const [name, setName] = useState('');
  const [totalTime, setTotalTime] = useState(0);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    // Input validation (add as needed)

    const newJobData = {
      id: '', // Assume ID is generated on the backend
      status: '', // Assume initial status is set on the backend
      name,  
      totalTime
    };

    try {
      const response = await fetch('http://localhost:8080/job', { // Adjust endpoint if needed
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newJobData) 
      });

      if (response.ok) {
        // Handle successful creation (e.g., clear form, update jobs list?)
        setName('');
        setTotalTime(0);
      } else {
        // Handle error gracefully
        console.error('Error creating job:', await response.text()); 
      }
    } catch (error) {
      console.error('Error submitting form:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add New Job</h2>
      <div>
        <label htmlFor="name">Document Name:</label>
        <input 
          type="text" 
          id="name" 
          value={name} 
          onChange={(e) => setName(e.target.value)} 
          required
        />
      </div>
      <div>
        <label htmlFor="totalTime">Total Time (seconds):</label>
        <input 
          type="number" 
          id="totalTime" 
          value={totalTime} 
          onChange={(e) => setTotalTime(parseInt(e.target.value, 10))} 
          required
        />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default NewJobForm