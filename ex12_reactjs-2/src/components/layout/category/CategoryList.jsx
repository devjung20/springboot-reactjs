import React from 'react';
import CategoryItem from './CategoryItem';

const CategoryList = ({ categories, onClickItem }) => {
  return (
    <div className="category-list">
      <h2>Catgeory List</h2>
      {categories.map((category) => (
        <CategoryItem key={category.id} category={category} onClickItem={onClickItem} />
      ))}
    </div>
  );
};

export default CategoryList;
