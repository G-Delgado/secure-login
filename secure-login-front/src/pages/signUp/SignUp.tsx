import { useState } from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import containerbg from '../../assets/liquid-cheese.svg'
import { SignUpDTO } from '../../util/Models';
import { useNavigate } from 'react-router-dom';

const SignUp: React.FC = () => {

    const navigate = useNavigate();


    const [user, setUser] = useState<SignUpDTO>({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
    })

    const [confirmPassword, setConfirmPassword] = useState<string>('');

    const handleChangeUser = (event: string, field: keyof SignUpDTO) => {
        setUser({
            ...user,
            [field]: event
        })
    }

    const handleConfirmPassword = (event: string) => {
        setConfirmPassword(event);
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(user);
    }

    return (
        <Container fluid className='p-4 bg-light bg-image' style={{ backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover'}}>
            <Row>

                <Col md={6} className='text-center text-md-start d-flex flex-column justify-content-center'>

                    <h1 className="my-5 display-3 fw-bold ls-tight px-3 text-white">
                        La mejor opción <br />
                        <span className="text-primary">para tu negocio</span>
                    </h1>

                    <p className='px-3' style={{ color: 'white' }}>
                        Nuestra aplicación cuenta con las mejores medidas de seguridad
                        para manejar la autenticación, login, signup, la privacidad de tus datos
                        y, lo más importante, la de tus clientes. Confía en nosotros, somos los mejores.
                    </p>

                </Col>

                <Col md={5} className='my-0 mx-auto'>


                    <Card className='my-5 shadow-lg border-white bg-light'>
                        <h2 style={{color: '#0464ac'}}>Crea tu cuenta</h2>
                        <Card.Body className='p-3'>

                            <Form onSubmit={handleSubmit}>
                                <Row>
                                    <Col xs={6}>
                                        <Form.Group className='mb-4'>
                                            <Form.Label>Nombre</Form.Label>
                                            <Form.Control className='' type='text' placeholder='Nombre' autoComplete='name' required value={user.firstName} onChange={(ev) => handleChangeUser(ev.target.value, 'firstName')} />
                                        </Form.Group>
                                    </Col>

                                    <Col xs={6}>
                                        <Form.Group className='mb-2'>
                                            <Form.Label>Apellido</Form.Label>
                                            <Form.Control type='text' placeholder='Apellido' autoComplete='name' required value={user.lastName} onChange={(ev) => handleChangeUser(ev.target.value, 'lastName')} />
                                        </Form.Group>
                                    </Col>
                                </Row>

                                <Form.Group className='mb-2'>
                                    <Form.Label>Correo</Form.Label>
                                    <Form.Control type='email' placeholder='Correo' autoComplete='name' required value={user.email} onChange={(ev) => handleChangeUser(ev.target.value, 'email')} />
                                </Form.Group>

                                <Form.Group className='mb-2'>
                                    <Form.Label>Contraseña</Form.Label>
                                    <Form.Control type='password' placeholder='Contraseña' autoComplete='name' required value={user.password} onChange={(ev) => handleChangeUser(ev.target.value, 'password')} />
                                </Form.Group>

                                <Form.Group className='mb-4'>
                                    <Form.Label>Confirmar contraseña</Form.Label>
                                    <Form.Control type='confirmPassword' placeholder='Confirmar contraseña' autoComplete='name' required value={confirmPassword} onChange={(event) => handleConfirmPassword(event.target.value)} />
                                </Form.Group>


                                <Button className='w-100 mb-2' type='submit'>
                                    Crear cuenta
                                </Button>

                                <Row className="justify-content-center">
                                    <Col xs={12} md={8} className="text-center">
                                        <a href="" onClick={() => navigate("/login")}>Regresar al Login</a>
                                    </Col>
                                </Row>
                            </Form>


                        </Card.Body>
                    </Card>

                </Col>

            </Row>

        </Container>
    );
}

export default SignUp;
