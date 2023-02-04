import logo from './logo.svg';
import './App.css';
import SongList from './component/SongList';
import UserList from './component/UserList';
import React, { useEffect, useState } from "react";



function App() {

  const songs = [
    { id: 1, title: 'Tiramisu fraise', author: 'La Fine Equipe' },
    { id: 2, title: 'Trust Nobody', author: 'Hippie Sabotage' },
    { id: 3, title: 'The Chain - 2004 Remaster', author: 'Fleetwood Mac' },
  ];
  

  const [user, setUser] = useState([]);

  const fetchData = () => {
    return fetch("https://jsonplaceholder.typicode.com/users")
          .then((response) => response.json())
          .then((data) => setUser(data));
  }

  useEffect(() => {
    fetchData();
  },[])

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div>
          <h1>My Song List</h1>
          <SongList songs={songs} />
        </div>
        <h1>User List</h1>
          <UserList user={user} />
        <h1> Another User List</h1>
      </header>
    </div>
  );
}

export default App;
