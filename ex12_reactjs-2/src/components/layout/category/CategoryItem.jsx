import React from 'react';

const CategoryItem = ({ category, onClickItem }) => {
  return (
    <div className="category-item" onClick={() => onClickItem(category)}>
      {category.name}
    </div>
  );
};

export default CategoryItem;
