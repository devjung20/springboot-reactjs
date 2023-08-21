import { useEffect } from 'react';
import { useState } from 'react';
import axios from 'axios';
import { useMemo } from 'react';
import { Pagination, Stack } from '@mui/material';
import { Link } from 'react-router-dom';

export const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPage = 10;

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get(`http://localhost:2000/category/get-all`);
        setCategories(response.data);
      } catch (error) {
        console.error('Error fetching categories: ', error);
      }
    };
    fetchCategories();
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

  return (
    <div>
      <h1>Category List</h1>
      <ul>
        {paginatedCategories.map((category) => (
          <li key={category.id} style={{ margin: '20px' }}>
            <Link to={`/categories/${category.id}`}>{category.name}</Link>
          </li>
        ))}
      </ul>
      <Stack spacing={2} className="pagination">
        <Pagination
          count={totalPages}
          color="primary"
          page={currentPage}
          onChange={handlePageChange}
          showFirstButton
          showLastButton
        />
      </Stack>
    </div>
  );
};
