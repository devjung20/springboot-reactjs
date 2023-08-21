import axios from 'axios';
import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import moment from 'moment';
import ListGroup from 'react-bootstrap/ListGroup';
import Button from 'react-bootstrap/Button';

export const CategoryDetail = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState(null);

  useEffect(() => {
    const fetchCatgory = async () => {
      try {
        const response = await axios.get(`http://localhost:2000/category/get-all/${categoryId}`);
        setCategory(response.data);
      } catch (error) {
        console.error('Error getting category:' + error);
      }
    };
    fetchCatgory();
  }, [categoryId]);

  if (!category) {
    return <p>Loading...</p>;
  }

  // Định dạng ngày thời gian theo định dạng mong muốn
  const formattedDateCreate = moment(category.dateCreate).format('HH:mm - DD/MM/YYYY');
  const formattedDateUpdate = moment(category.dateFix).format('HH:mm - DD/MM/YYYY');

  return (
    <div>
      <h1>Category Detail</h1>
      <ListGroup>
        <ListGroup.Item>Code: {category.categoriesCode}</ListGroup.Item>
        <ListGroup.Item>Name: {category.name}</ListGroup.Item>
        <ListGroup.Item>Description: {category.description}</ListGroup.Item>
        <ListGroup.Item>Date create: {formattedDateCreate}</ListGroup.Item>
        <ListGroup.Item>Date update: {formattedDateUpdate}</ListGroup.Item>
      </ListGroup>

      <Button variant="success" style={{ marginTop: '20px' }}>
        <Link
          to="/categories"
          style={{
            color: 'white',
            textDecoration: 'none',
          }}
        >
          Back
        </Link>
      </Button>
    </div>
  );
};
