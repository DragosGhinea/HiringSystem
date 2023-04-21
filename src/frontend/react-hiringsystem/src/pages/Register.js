import { useContext, useRef } from "react";
import { Col, Container, Row } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import AuthContext from "../components/shared/AuthContext";

const Register = () => {
  const email = useRef("");
  const password = useRef("");
  const firstName = useRef("");
  const lastName = useRef("");
  const {register} = useContext(AuthContext)
 
  const registerSubmit = async () => {
    let payload = {
      email: email.current.value,
      password: password.current.value,
      firstName: firstName.current.value,
      lastName: lastName.current.value
    }
    await register(payload);
  };


  return (
    <>
      <Container className="mt-2">
        <Row>
          <Col className="col-md-8 offset-md-2">
            <legend>Register Form</legend>
            <form>
              <Form.Group className="mb-3" controlId="formEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="text" ref={email} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formFirstName">
                <Form.Label>First Name</Form.Label>
                <Form.Control type="text" ref={firstName} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formLastName">
                <Form.Label>Last Name</Form.Label>
                <Form.Control type="text" ref={lastName} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" ref={password} />
              </Form.Group>
              <Button variant="primary" type="button" onClick={registerSubmit}>
                Register
              </Button>
            </form>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default Register;