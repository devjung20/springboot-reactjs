import { Route, Routes } from 'react-router-dom';
import Layout from './components/shared/Layout';
import Home from './components/pages/Home';
import Login from './components/pages/Login';
import { AuthContextProvider } from './components/shared/AuthContextProvider';
import Product from './components/pages/Product';
import { ProtectedRoute } from './components/shared/ProtectedRoute';
import Category from './components/pages/Category';
import { CategoryDetail } from './components/pages/CategoryDetail';
import { CategoryUpdate } from './components/pages/CategoryUpdate';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { CategoryAdd } from './components/pages/CategoryAdd';

function App() {
  return (
    <>
      <AuthContextProvider>
        <ToastContainer />
        <Layout>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route
              path="/login"
              element={
                <ProtectedRoute accessBy="non-authenticated">
                  <Login />
                </ProtectedRoute>
              }
            />
            <Route
              path="/products"
              element={
                <ProtectedRoute accessBy="authenticated">
                  <Product />
                </ProtectedRoute>
              }
            />
            <Route
              path="/categories"
              element={
                <ProtectedRoute accessBy="authenticated">
                  <Category />
                </ProtectedRoute>
              }
            />
            <Route
              path="/categories/:categoryId"
              element={
                <ProtectedRoute accessBy="authenticated">
                  <CategoryDetail />
                </ProtectedRoute>
              }
            />
            <Route
              path="/categories/edit/:categoryId"
              element={
                <ProtectedRoute accessBy="authenticated">
                  <CategoryUpdate />
                </ProtectedRoute>
              }
            />
            <Route
              path="/categories/new"
              element={
                <ProtectedRoute accessBy="authenticated">
                  <CategoryAdd />
                </ProtectedRoute>
              }
            />
          </Routes>
        </Layout>
      </AuthContextProvider>
    </>
  );
}

export default App;
