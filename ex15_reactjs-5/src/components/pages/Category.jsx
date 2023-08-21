import { useState, useEffect, useMemo } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import jwtInterceptor from '../shared/jwtInterceptor';
import { Link } from 'react-router-dom';
import Stack from '@mui/material/Stack';
import { Pagination, Typography } from '@mui/material';
import { CategoryDelete } from './CategoryDelete';

const Category = () => {
  const [categories, setCategories] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPage = 3;

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        jwtInterceptor.get('http://localhost:2000/category/get-all').then((response) => {
          setCategories(response.data);
        });
      } catch (error) {
        console.error('Error getting all categories: ', error);
      }
    };
    fetchCategory();
  }, []);

  const handlePageChange = (event, value) => {
    setCurrentPage(value);
  };

  //tính toán tổng số trang cần để hiển thị danh sách category được phân trang (ceil: làm tròn lên)
  const totalPages = Math.ceil(categories.length / itemsPage);
  /**
   * useMemo(): được sử dụng để tối ưu hóa hiệu suất trong các trường hợp khi bạn muốn lưu trữ một giá trị được tính toán
   * và chỉ tính toán lại khi các yếu tố cụ thể thay đổi. Điều này giúp tránh việc tính toán không cần thiết và cải thiện hiệu suất ứng dụng.
   */
  const paginatedCategories = useMemo(() => {
    const offset = (currentPage - 1) * itemsPage;
    return categories.slice(offset, offset + itemsPage);
  }, [categories, currentPage, itemsPage]);

  const handleItemDeleted = (deletedItemId) => {
    setCategories(categories.filter((category) => category.id !== deletedItemId));
  };

  return (
    <>
      <div style={{ margin: '20px 0px' }}>
        <Link
          to="/categories/new"
          style={{ padding: '10px 25px', backgroundColor: 'green', color: 'white', textDecoration: 'none' }}
        >
          Create New Category
        </Link>
      </div>
      {categories.length === 0 ? (
        <p>No Category available.</p>
      ) : (
        <Row xs={1} md={3} className="g-4">
          {paginatedCategories.map((category) => (
            <Col key={category.id}>
              <Card>
                <Link to={`/categories/${category.id}`} style={{ textDecoration: 'none', color: 'black' }}>
                  <Card.Body>
                    <Card.Title>Code: {category.categoriesCode}</Card.Title>
                    <Card.Text>Name: {category.name}</Card.Text>
                    <Card.Text>Description: {category.description}</Card.Text>
                  </Card.Body>
                </Link>
                <div style={{ display: 'flex' }}>
                  <Link
                    to={`/categories/edit/${category.id}`}
                    style={{
                      color: 'white',
                      textDecoration: 'none',
                      backgroundColor: 'green',
                      width: '20%',
                      padding: '5px 20px',
                      display: 'flex',
                      justifyContent: 'center',
                      borderRadius: '5px',
                      alignItems: 'center',
                      marginRight: '10px',
                    }}
                  >
                    Update
                  </Link>
                  <CategoryDelete onDelete={handleItemDeleted} itemId={category.id} />
                </div>
              </Card>
            </Col>
          ))}
        </Row>
      )}
      <Stack spacing={2} className="pagination" style={{ marginTop: '20px' }}>
        <Typography>
          Page: {currentPage}/{totalPages}
        </Typography>
        <Pagination
          count={totalPages}
          color="primary"
          page={currentPage}
          onChange={handlePageChange}
          showFirstButton
          showLastButton
          variant="outlined"
          shape="rounded"
        />
      </Stack>
    </>
  );
};

export default Category;
