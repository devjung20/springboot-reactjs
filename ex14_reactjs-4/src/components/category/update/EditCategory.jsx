import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Navigate } from 'react-router-dom';

export const EditCategory = () => {
  const { categoryId } = useParams();
  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');

  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    const fetchCatgory = async () => {
      try {
        const response = await axios.get(`http://localhost:2000/category/get-all/${categoryId}`);
        const category = response.data;
        setCode(category.categoriesCode);
        setName(category.name);
        setDescription(category.description);
      } catch (error) {
        console.error('Error fetching categories: ', error);
      }
    };
    fetchCatgory();
  }, [categoryId]);

  const handleUpdate = async (e) => {
    e.preventDefault();

    try {
      await axios.put(
        `http://localhost:2000/category/update/${categoryId}`,
        { categoriesCode: code, name, description },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sInJvbGVzIjpbIkFETUlOIl0sImlzRW5hYmxlIjp0cnVlLCJpYXQiOjE2OTIxNDk0MDYsImV4cCI6MTY5NTc0OTQwNn0.OcgoOvf48Q_dPOkR1XX3UwMrhlL1HBeYvkJ7xkHduNw',
          },
        }
      );
      setIsUpdated(true);
    } catch (error) {
      console.log('Error updating category: ' + error);
    }
  };

  if (isUpdated) {
    return <Navigate to="/categories" />;
  }

  return (
    <div>
      <h2>Edit Category</h2>
      <form>
        <div>
          <label htmlFor="">Code: </label>
          <input type="text" value={code} onChange={(e) => setCode(e.target.value)} />
        </div>
        <div>
          <label htmlFor="">Name: </label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
        </div>
        <div>
          <label htmlFor="">Description: </label>
          <textarea type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
        </div>
        <button onClick={handleUpdate}>Update</button>
      </form>
    </div>
  );
};
