import { useState } from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import containerbg from '../../assets/liquid-cheese3.png'
import Swal from 'sweetalert2';

const ForgotPassword: React.FC = () => {

    const [email, setEmail] = useState<string>('');
    const navigate = useNavigate();

    const handleEmailSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        Swal.fire({
            title: '¿Estás seguro?',
            text: 'Se enviará un correo a la dirección indicada',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, enviar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {

                //TODO: Send email to backend

                Swal.fire({
                    title: '¡Correo enviado!',
                    text: 'Se ha enviado un correo a la dirección indicada',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                })
            }
        })
    }

    return (
        <Container fluid className="bg-image" style={{ backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover'}}>
            <Row className='d-flex justify-content-center align-items-center h-100'>
                <Col col='12'>
                    <Card className='bg-dark text-white my-5 mx-auto shadow-lg' style={{ borderRadius: '1rem', maxWidth: '400px' }}>
                        <Card.Body className='p-5 d-flex flex-column align-items-center mx-auto w-100'>
                            <h2 className="fw-bold mb-2">Olvidé mi contraseña</h2>
                            <p className="text-white-50 mb-5">Por favor, escribe tu correo para enviarte la confirmación!</p>
                            <Form className="d-flex flex-column align-items-center mx-auto w-100" onSubmit={handleEmailSubmit}>
                                <Form.Control required className='mb-4 mx-5 w-100 py-2 rounded-3 border-0 border-bottom border-primary bg-dark text-white' type='email' placeholder='Correo electrónico' size="sm" />

                                <Button variant='outline-primary' type='submit' className='m-2 mb-4 py-2 rounded-3' size='sm'>
                                    Enviar confirmación
                                </Button>
                            </Form>

                            <div>
                                <p className="mb-0">No tienes una cuenta? <a href="" className="text-primary fw-bold" onClick={() => navigate("/signup")}>Crea una</a></p>
                                <p className="mb-0"><a href="" className="text-primary fw-bold" onClick={() => navigate("/login")}>Regresar</a></p>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}

export default ForgotPassword;