import React from 'react'
import { Link } from 'react-router-dom'


const Temperature = () => {
  return (
    <>
    <header className="App-header">
        <h1>Temperature</h1>
    </header>
    <div>Temperature Content</div>
    <Link to="/"><button>View Jobs</button></Link>
    </>
    
  )

}

export default Temperature