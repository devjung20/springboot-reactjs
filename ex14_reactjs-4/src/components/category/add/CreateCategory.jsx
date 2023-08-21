import axios from 'axios';
import React, { useState } from 'react';
import { Link, Navigate } from 'react-router-dom';

export const CreateCategory = () => {
  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [codeError, setCodeError] = useState('');
  const [nameError, setNameError] = useState('');
  const [descriptionError, setDescriptionError] = useState('');
  const [isCreated, setIsCreated] = useState(false);

  const handleCreate = async (e) => {
    e.preventDefault();
    setCodeError();
    setNameError();
    setDescriptionError();

    if (code === '') {
      setCodeError('Please enter a code');
    }
    if (name === '') {
      setNameError('Please enter a name');
    }
    if (description === '') {
      setDescriptionError('Please enter a description');
    }

    try {
      await axios.post(
        `http://localhost:2000/category/create`,
        { categoriesCode: code, name, description },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sInJvbGVzIjpbIkFETUlOIl0sImlzRW5hYmxlIjp0cnVlLCJpYXQiOjE2OTIxMTQ5MDksImV4cCI6MTY5NTcxNDkwOX0.kaOE5hLMeR-y5QlMH5zh5g7f2bKwkHd1XtygdnnsgpU',
          },
        }
      );
      setIsCreated(true);
    } catch (error) {
      console.log('Error creating category: ' + error);
    }
  };

  if (isCreated) {
    return <Navigate to="/categories" />;
  }

  return (
    <div>
      <h2>Create New Catgory</h2>
      <form action="" method="get">
        <div>
          <label htmlFor="">Code: </label>
          <input type="text" value={code} onChange={(e) => setCode(e.target.value)} />
          {codeError && <p style={{ color: 'red' }}>{codeError}</p>}
        </div>
        <div>
          <label htmlFor="">Name: </label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
          {nameError && <p style={{ color: 'red' }}>{nameError}</p>}
        </div>
        <div>
          <label htmlFor="">Description: </label>
          <textarea type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
          {descriptionError && <p style={{ color: 'red' }}>{descriptionError}</p>}
        </div>
        <button onClick={handleCreate}>Create</button>
      </form>
      <Link to="/categories">Back to category</Link>
    </div>
  );
};
