import Navbar from 'react-bootstrap/Navbar';
import { Button, Container, Nav } from 'react-bootstrap';
import { PropTypes } from 'prop-types';
import { useContext } from 'react';
import AuthContext from './AuthContextProvider';
import { Link } from 'react-router-dom';

const Layout = ({ children }) => {
  const { user, logout } = useContext(AuthContext);

  return (
    <>
      <Navbar bg="primary" variant="dark">
        <Navbar.Brand>
          <Nav.Link as={Link} to="/">
            Auth Demo
          </Nav.Link>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav>
            {user && (
              <Nav.Link as={Link} to="/products">
                Product
              </Nav.Link>
            )}
          </Nav>
          <Nav>
            {user && (
              <Nav.Link as={Link} to="/categories">
                Category
              </Nav.Link>
            )}
          </Nav>
          <Nav className="ms-auto">
            {!user && (
              <Nav.Link as={Link} to="/login">
                Login
              </Nav.Link>
            )}
            {user && <Nav.Link href="#">{user?.sub}</Nav.Link>}
          </Nav>
          {user && (
            <Button
              variant="success"
              type="button"
              onClick={() => {
                logout();
              }}
            >
              Logout
            </Button>
          )}
        </Navbar.Collapse>
      </Navbar>
      <Container>{children}</Container>
    </>
  );
};

Layout.propTypes = {
  children: PropTypes.any.isRequired,
};
export default Layout;
