import { Navbar, Container, NavDropdown, Nav } from "react-bootstrap";
import { deleteCookie, getCookie } from "../../util/Methods";
import { useEffect, useState } from "react";
import backendUrl from "../../util/Config";
import axios, { AxiosResponse } from "axios";

const NavbarBootstrap: React.FC = () => {

  const [admin, setAdmin] = useState<boolean>(false);

  useEffect(() => {
    let getIsAdmin = async () => {
      let url = backendUrl + "/auth/role";
      let response: AxiosResponse<boolean> = {} as AxiosResponse<boolean>;
      try {
        response = await axios.get(url, {
          headers: {
            Authorization: 'Bearer ' + getCookie('token')
          }
        })
      } catch (error) {
        alert("Hubo un error al verificar tu rol")
      }

      
      if (response.status === 200) {
        setAdmin(response.data);
        //console.log(response.data)
      }
    }
    getIsAdmin();
  }, [])





  const handleLogout = () => {

    deleteCookie('token');
    deleteCookie('email');

    window.location.href = '/login';

  }

  const handleChangeLocation = (location: string) => {
    window.location.href = location;
  }

  return (<>
    <Navbar expand="lg" className="p-0 m-0 max bg-dark position-fixed fixed-top" style={{ height: '40px', minHeight: '40px' }}>
      <Container>
        <Navbar.Brand href="" className="text-white">Secure Login</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link onClick={() => handleChangeLocation('/')} className="text-white">Home</Nav.Link>
            <Nav.Link onClick={() => handleChangeLocation('/user')} className="text-white">User</Nav.Link>
            <Nav.Link onClick={handleLogout} className="text-white">Logout</Nav.Link>
            {admin ? <>
              <NavDropdown title={<span className="text-white m-0">Admin</span>} id="basic-nav-dropdown" className="text-white">
                <NavDropdown.Item onClick={() => handleChangeLocation('/dashboard')}>Dashboard</NavDropdown.Item>
              </NavDropdown>
            </> : <></>}

          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>

  </>)
}

export default NavbarBootstrap;