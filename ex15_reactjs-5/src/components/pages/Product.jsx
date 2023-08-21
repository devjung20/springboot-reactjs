import { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import jwtInterceptor from '../shared/jwtInterceptor';

const Product = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    jwtInterceptor.get('http://localhost:2000/admin/product/get-all').then((response) => {
      setProducts(response.data);
    });
  }, []);
  return (
    <>
      <Row xs={1} md={2} className="g-4">
        {products.map((product) => (
          <Col key={product.id}>
            <Card>
              <Card.Body>
                <Card.Title>Code: {product.productCode}</Card.Title>
                <Card.Text>Name: {product.name}</Card.Text>
                <Card.Text>Description: {product.description}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </>
  );
};

export default Product;
