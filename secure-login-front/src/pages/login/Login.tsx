import { Container, Row, Col, Form, Button } from 'react-bootstrap';


const Login: React.FC = () => {

    return (
        <Container fluid className=''>
            <Row>
                {/* Left column - 75% */}
                <Col sm={7} className="bg-primary text-white d-flex flex-column justify-content-center">
                    <h1>Welcome</h1>
                    <p>Some content here</p>
                </Col>

                {/* Right column - 25% */}
                <Col sm={5} className="bg-light">
                    <div className="p-4">
                        <h2 className="mb-4">Login</h2>
                        <Form>
                            <Form.Group controlId="formBasicEmail">
                                <Form.Label>Email address</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" />
                                <Form.Text className="text-muted">
                                    We'll never share your email with anyone else.
                                </Form.Text>
                            </Form.Group>

                            <Form.Group controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" />
                            </Form.Group>

                            <Button variant="primary" type="submit" className="w-100 mt-3">
                                Login
                            </Button>
                        </Form>
                    </div>
                </Col>
            </Row>
        </Container>
    );
}

export default Login;