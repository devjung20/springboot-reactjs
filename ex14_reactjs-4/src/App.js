import { Route, Routes } from 'react-router-dom';
import { CategoryList } from './components/category/CategoryList';
import { CategoryDetail } from './components/category/CategoryDetail';
import { Login } from './components/login/Login';
import { CreateCategory } from './components/category/add/CreateCategory';
import { EditCategory } from './components/category/update/EditCategory';
import { useAuth } from './components/auth/AuthProvider';

function App() {
  const { isLoggedIn } = useAuth();
  return (
    <div className="App">
      {!isLoggedIn && (
        <Routes>
          <Route path="*" element={<Login />} />
        </Routes>
      )}
      {isLoggedIn && (
        <Routes>
          <Route path="/categories" element={<CategoryList />} />
          <Route path="/categories/:categoryId" element={<CategoryDetail />} />
          <Route path="/categories/new" element={<CreateCategory />} />
          <Route path="/categories/edit/:categoryId" element={<EditCategory />} />
        </Routes>
      )}
    </div>
  );
}

export default App;
