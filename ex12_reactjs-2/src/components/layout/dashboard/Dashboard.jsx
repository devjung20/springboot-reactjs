import React from 'react';
import CategoryApp from '../category/CategoryApp';

const Dashboard = ({ username }) => {
  return (
    <div>
      <h2>Dashboard</h2>
      <p>Welcome, {username}!</p>

      <CategoryApp />
    </div>
  );
};

export default Dashboard;
