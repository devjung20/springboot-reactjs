import { useEffect, useState } from 'react';
import jwtInterceptor from '../shared/jwtInterceptor';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { Button, Form, FormControl, FormGroup, FormLabel } from 'react-bootstrap';

export const CategoryAdd = () => {
  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [codeError, setCodeError] = useState('');
  const [nameError, setNameError] = useState('');
  const [descriptionError, setDescriptionError] = useState('');
  const [isCreated, setIsCreated] = useState(false);
  const navigate = useNavigate();

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
      await jwtInterceptor.post(
        `http://localhost:2000/category/create`,
        { categoriesCode: code, name, description },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );
      setIsCreated(true);
      toast.success('Created successfully');
    } catch (e) {
      console.log('Error creating category: ' + e);
      toast.error('Error creating category');
    }
  };

  useEffect(() => {
    if (isCreated) {
      return navigate('/categories');
    }
  }, [isCreated, navigate]);

  const handleBack = () => {
    return navigate('/categories');
  };

  return (
    <div>
      <h2>Edit Category</h2>
      <Form>
        <FormGroup>
          <FormLabel>Code:</FormLabel>
          <FormControl type="text" value={code} onChange={(e) => setCode(e.target.value)} />
          {codeError && <p style={{ color: 'red' }}>{codeError}</p>}
        </FormGroup>
        <FormGroup>
          <FormLabel>Name:</FormLabel>
          <FormControl type="text" value={name} onChange={(e) => setName(e.target.value)} />
          {nameError && <p style={{ color: 'red' }}>{nameError}</p>}
        </FormGroup>
        <FormGroup>
          <FormLabel>Description:</FormLabel>
          <Form.Control as="textarea" value={description} onChange={(e) => setDescription(e.target.value)} />
          {descriptionError && <p style={{ color: 'red' }}>{descriptionError}</p>}
        </FormGroup>
        <div style={{ marginTop: '20px' }}>
          <Button variant="primary" onClick={handleCreate} style={{ marginRight: '10px' }}>
            Create
          </Button>
          <Button variant="success" onClick={handleBack}>
            Back to category
          </Button>
        </div>
      </Form>
    </div>
  );
};
