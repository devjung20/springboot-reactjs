import { useContext, useRef } from 'react';
import AuthContext from '../shared/AuthContextProvider';
import { Col, Container, Row } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

const Login = () => {
  const username = useRef('');
  const password = useRef('');
  const { login } = useContext(AuthContext);

  const loginSubmit = async () => {
    let payload = {
      username: username.current.value,
      password: password.current.value,
    };
    await login(payload);
  };
  return (
    <div>
      <Container className="mt-2">
        <Row>
          <Col className="col-md-8 offset-md-2">
            <legend>Login Form</legend>
            <form>
              <Form.Group className="mb-3" controlId="formUserName">
                <Form.Label>User Name</Form.Label>
                <Form.Control type="text" ref={username} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" ref={password} />
              </Form.Group>
              <Button variant="primary" type="button" onClick={loginSubmit}>
                Login
              </Button>
            </form>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Login;
