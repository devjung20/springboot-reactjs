import React from 'react';

const Dashboard = ({ username }) => {
  return (
    <div>
      <h2>Dashboard</h2>
      <p>Welcome, {username}!</p>
    </div>
  );
};

export default Dashboard;
