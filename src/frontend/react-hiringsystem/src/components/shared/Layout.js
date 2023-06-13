import Navbar from "react-bootstrap/Navbar";
import { Container, Button } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "./AuthContext";


const Layout = ({ children }) => {
  const { user, logout } = useContext(AuthContext);
  return (
    <>
      <Navbar bg="dark" variant="dark" className="p-3">
        <Navbar.Brand>
          <Nav.Link as={Link} to="/">
            HiringSystem
          </Nav.Link>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
              <Nav.Link as={Link} to="/jobs">
                Jobs
              </Nav.Link>
              {user?.userType === "candidate" && (
                <Nav.Link as={Link} to="/my-applications">
                  My applications
                </Nav.Link>
              )}
              {user && (
                <Nav.Link as={Link} to="/my-interviews">
                  My interviews
                </Nav.Link>
              )}
              {user?.userType === "manager" && (
                <Nav.Link as={Link} to="/jobs/manage">
                  Manage jobs
                </Nav.Link>
              )}
              {user?.userType === "manager" && (
                <Nav.Link as={Link} to="/users/manage">
                  Manage users
                </Nav.Link>
              )}
              {(user?.userType === "manager" || user?.userType === "interviewer") && (
                <Nav.Link as={Link} to="/interviews/manage">
                  Manage interviews
                </Nav.Link>
              )}
          </Nav>
          <Nav className="ms-auto">
            {!user && (
              <Nav.Link as={Link} to="/login">
                Login
              </Nav.Link>
            )}
            {!user && (
              <Nav.Link as={Link} to="/register">
                Register
              </Nav.Link>
            )}
            {user && (
              <Nav.Link as={Link} to="/profile">
                Your Profile ({user?.email})
              </Nav.Link>
            )}
          </Nav>
          {user && (
            <Button
              variant="outline-success"
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
      <Container fluid>{children}</Container>
    </>
  );
};
 
export default Layout;