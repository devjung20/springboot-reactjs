import { CategoryDetail } from './components/CategoryDetail';
import { CategoryList } from './components/CategoryList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/categories" element={<CategoryList />} />
          <Route path="/categories/:categoryId" element={<CategoryDetail />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
