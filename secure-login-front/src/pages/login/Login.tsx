import { useState } from 'react';
import { Container, Row, Col, Form, Button, InputGroup } from 'react-bootstrap';
import bg from '../../assets/mountains.avif'
import containerbg from '../../assets/liquid-cheese3.png'
import react from '../../assets/react.svg';
import { FaEye, FaEyeSlash } from 'react-icons/fa'; // Importa los iconos para mostrar y ocultar
import { LoginDTO, TokenDTO, restrictedCharsRegex } from '../../util/Models';
import { useNavigate } from 'react-router-dom';
import backendUrl from '../../util/Config';
import axios, { AxiosResponse } from 'axios';
import { useAppContext } from '../../util/AppContext';
import { createCookie } from '../../util/Methods';

interface FormErrors {
  email: boolean,
  password: boolean
}

const Login: React.FC = () => {

  const navigate = useNavigate()

    const { setToken, setEmail } = useAppContext();
    const [errors, setErrors] = useState<FormErrors>({
      email: false,
      password: false
    });
    const [showPassword, setShowPassword] = useState(false);
    const [user, setUser] = useState<LoginDTO>({
        email: '',
        password: ''
    })

    const handleShowPassword = () => {
        setShowPassword(!showPassword);
    }

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
      if (!errors.email && !errors.password) {
        setToken("");
        setEmail("");
          event.preventDefault();
          //console.log(user);
  
          let url = backendUrl + "/auth/login"
          let response: AxiosResponse<TokenDTO> = {} as AxiosResponse<TokenDTO>;
          try {
            response = await axios.post(url, user);
          } catch (error) {
            alert("Hubo un error al iniciar sesión, por favor intente de nuevo o cambie las credenciales")
            return;
          }
  
          //console.log(response.data)
  
          setToken(response.data.token);
          setEmail(response.data.email);
  
          createCookie("token", response.data.token, 1);
          createCookie("email", response.data.email, 1);
  
          let Roleurl = backendUrl + "/auth/role"
          //console.log(response.data.token)
          let responseRole : AxiosResponse<boolean> = {} as AxiosResponse<boolean>;
          try {
            responseRole= await axios.get(Roleurl, {headers: {
              Authorization: "Bearer " + response.data.token
            }})
          } catch (error) {
            alert("Hubo un error al verificar tu rol")
            return;
          }
          
  
          if (responseRole.data == true) {
            navigate("/dashboard");
          } else if (response.data.token && response.data.email) {
            navigate("/");
          }
      } else {
        alert("Caracteres prohibidos en alguno de los campos")
        return;
      }
    }

    const handleChangeUser = (event: string, field: keyof LoginDTO) => {
        if (restrictedCharsRegex.test(event)) {
          setErrors({...errors, [field]: true});
        } else {
          setErrors({...errors, [field]: false});
        }
        setUser({
            ...user,
            [field]: event
        })
    }

    return (
        <Container fluid className="bg-light bg-image" style={{ backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover'}}>
      <Row className="h-100">
        <Col xs={12} sm={4} md={7} className=" bg-image d-flex flex-column justify-content-center align-items-center" style={{ backgroundImage: `url(${bg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover' }}>
          {/* Estilo personalizado para la imagen de fondo */}
          <h1>Bienvenido</h1>
          <h4>Aquí podrás probar nuestro login seguro 🔒</h4>
        </Col>
        <Col xs={12} sm={8} md={5} className="align-self-center pt-3">
          <Container className='border border-light  rounded-3 pt-4 pb-4 shadow-lg  my-0 mx-auto" w-75 bg-white'>
            <Row className="justify-content-center">
              <Col xs={12} className="text-center">
                <img src={react} alt="Logo Secure" width="50" className='mb-3' />
                <h3 className="my-3 mb-4" style={{ color: '#0464ac' }}>Secure Login</h3>
              </Col>
            </Row>
            <Row className="justify-content-center">
              <Col xs={12} md={8}>
                <Form onSubmit={handleSubmit}>
                  <Form.Group className="mb-3" controlId="email">
                    <Form.Control type="text" placeholder="Correo" isInvalid={errors.email} autoComplete="email" required value={user.email} onChange={(event) => handleChangeUser(event.target.value, "email")} />
                    <Form.Control.Feedback type="invalid">
                      Input contains restricted characters
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="password">
                    <InputGroup>

                    <Form.Control type={showPassword ? "text" : "password"} isInvalid={errors.password} placeholder="Contraseña" autoComplete="current-password" required value={user.password} onChange={(event) => handleChangeUser(event.target.value, "password")}/>
                    <Button variant="outline-secondary" onClick={handleShowPassword} style={{borderColor: '#ced4da'}}>
                      {showPassword ? <FaEyeSlash/> : <FaEye/>}
                    </Button>
                    <Form.Control.Feedback type="invalid">
                      Input contains restricted characters
                    </Form.Control.Feedback>
                    </InputGroup>
                  </Form.Group> 
                  <Button type="submit" variant="primary" className="mb-3 w-100">
                    Iniciar sesión
                  </Button>
                </Form>
              </Col>
            </Row>
            <Row className="justify-content-center">
              <Col xs={12} md={8} className="text-center mb-3">
                <a href="" onClick={() => navigate("/signup")}>No tienes un cuenta? Crea una</a>
              </Col>
            </Row>
            <Row className="justify-content-center">
              <Col xs={12} md={8} className="text-center">
                <a href="" onClick={() => navigate("/forgotPassword")}>¿Olvidó su contraseña?</a>
              </Col>
            </Row>
            {/* Footer o información adicional */}
          </Container>
        </Col>
      </Row>
    </Container>
  );
}

export default Login;