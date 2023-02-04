import logo from './logo.svg';
import './App.css';
import SongList from './component/SongList';
import React, { useEffect } from "react";



function App() {

  const songs = [
    { id: 1, title: 'Tiramisu fraise', author: 'La Fine Equipe' },
    { id: 2, title: 'Trust Nobody', author: 'Hippie Sabotage' },
    { id: 3, title: 'The Chain - 2004 Remaster', author: 'Fleetwood Mac' },
  ];
 
  var message = "Something";
  message = "Yes";

  useEffect(() => {
    fetch("http://localhost:8090/api/songcollection/songs", {
      headers: {
        'Authorization': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjUsInVzZXJuYW1lIjoiSm9obm55WEQiLCJwYXNzd29yZCI6InF3ZXJ0eXVpb3AxMjMiLCJyb2xlcyI6WzEsMl0sImV4cCI6MTY3MzQ1ODM3NSwianRpIjoiOTlmZDQ1N2EtOTFjZC0xMWVkLWIyZGItOTEyMDA3MTE2MmJkIn0.6RnQdi80JXuAZFkD_qX9YLZjjF29SqwZx5hkl0qS-yo',
        // 'Access-Control-Allow-Origin': '*'
      },
    })
      .then(response => response.json())
      .then(data => console.log(data));
  });

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>{message}</p>

        <div>
          <h1>My Song List</h1>
          <SongList songs={songs} />
        </div>
        
      </header>
    </div>
  );
}

export default App;
