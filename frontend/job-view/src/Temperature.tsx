import React from 'react'
import { NavLink } from 'react-router-dom'


const Temperature = () => {
  return (
    <>
    <header className="App-header">
        <h1>Temperature</h1>
    </header>
    <div>Temperature Content</div>
    <NavLink to="/"><button>View Jobs</button></NavLink>
    </>
    
  )

}

export default Temperature