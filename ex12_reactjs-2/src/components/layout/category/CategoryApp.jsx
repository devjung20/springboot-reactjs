import React, { useState } from 'react';
import CategoryList from './CategoryList';
import CategoryDetail from './CategoryDetail';

const CategoryApp = () => {
  const [selectedCategory, setSelectedCategory] = useState(null);

  const handleCategoryClick = (category) => {
    setSelectedCategory(category);
  };

  const categories = [
    { id: 1, name: 'Category 1', description: 'Description category 1' },
    { id: 2, name: 'Category 2', description: 'Description category 2' },
    { id: 3, name: 'Category 3', description: 'Description category 3' },
    { id: 4, name: 'Category 4', description: 'Description category 4' },
    { id: 5, name: 'Category 5', description: 'Description category 5' },
  ];

  return (
    <div>
      <CategoryList categories={categories} onClickItem={handleCategoryClick} />
      {selectedCategory && <CategoryDetail category={selectedCategory} />}
    </div>
  );
};

export default CategoryApp;
