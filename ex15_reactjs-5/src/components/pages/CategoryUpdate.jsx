import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import jwtInterceptor from '../shared/jwtInterceptor';
import { Button, Form, FormControl, FormGroup, FormLabel } from 'react-bootstrap';
import { toast } from 'react-toastify';

export const CategoryUpdate = () => {
  const { categoryId } = useParams();
  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [isUpdate, setIsUpdated] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const response = await jwtInterceptor.get(`http://localhost:2000/category/get-all/${categoryId}`);
        const category = response.data;
        setCode(category.categoriesCode);
        setName(category.name);
        setDescription(category.description);
      } catch (error) {
        console.error('Error fetching categories: ', error);
      }
    };
    fetchCategory();
  }, [categoryId]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await jwtInterceptor.put(
        `http://localhost:2000/category/update/${categoryId}`,
        {
          categoriesCode: code,
          name,
          description,
        },
        { headers: { 'Content-Type': 'application/json' } }
      );
      setIsUpdated(true);
      toast.success('Update successful');
    } catch (error) {
      console.log('Error updating category: ' + error);
    }
  };

  useEffect(() => {
    if (isUpdate) {
      return navigate('/categories');
    }
  }, [isUpdate, navigate]);

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
        </FormGroup>
        <FormGroup>
          <FormLabel>Name:</FormLabel>
          <FormControl type="text" value={name} onChange={(e) => setName(e.target.value)} />
        </FormGroup>
        <FormGroup>
          <FormLabel>Description:</FormLabel>
          <Form.Control as="textarea" value={description} onChange={(e) => setDescription(e.target.value)} />
        </FormGroup>
        <div style={{ marginTop: '20px' }}>
          <Button variant="primary" onClick={handleUpdate} style={{ marginRight: '10px' }}>
            Update
          </Button>
          <Button variant="success" onClick={handleBack}>
            Back to category
          </Button>
        </div>
      </Form>
    </div>
  );
};
