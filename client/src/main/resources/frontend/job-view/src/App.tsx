import './App.scss';
import NewJobForm from './NewJobForm';
import PrintingJobs from './PrintingJobs';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Printing jobs</h1>
      </header>
      <div> 
        <NewJobForm /> 
      </div> 
      <div>
        <PrintingJobs />
      </div>
    </div>
  );
}

export default App;
