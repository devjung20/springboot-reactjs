import React from 'react';

const CategoryDetail = ({ category }) => {
  return (
    <div className="category-detail">
      <h2>Category Detail</h2>
      <p>Name: {category.name}</p>
      <p>Description: {category.description}</p>
    </div>
  );
};

export default CategoryDetail;
