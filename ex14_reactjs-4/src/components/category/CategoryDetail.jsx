import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import moment from 'moment';

export const CategoryDetail = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState(null);

  useEffect(() => {
    const fetchCatgory = async () => {
      try {
        const response = await axios.get(`http://localhost:2000/category/get-all/${categoryId}`);
        setCategory(response.data);
      } catch (error) {
        console.error('Error fetching categories: ', error);
      }
    };
    fetchCatgory();
  }, [categoryId]);

  if (!category) {
    return <p>Loading....</p>;
  }

  // Định dạng ngày thời gian theo định dạng mong muốn
  const formattedDateCreate = moment(category.dateCreate).format('HH:mm - DD/MM/YYYY');
  const formattedDateUpdate = moment(category.dateFix).format('HH:mm - DD/MM/YYYY');

  return (
    <div>
      <h1>Category Detail</h1>
      <p>Code: {category.categoriesCode}</p>
      <p>Name: {category.name}</p>
      <p>Description: {category.description}</p>
      <p>Date create: {formattedDateCreate}</p>
      <p>Date update: {formattedDateUpdate}</p>
    </div>
  );
};
