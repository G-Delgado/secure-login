import { Button, Card, Form, Navbar } from "react-bootstrap";
import containerbg from '../../assets/liquid-cheese3.png';
import { getCookie } from "../../util/Methods";
import { useAppContext } from "../../util/AppContext";
import { useNavigate } from "react-router-dom";
import { ChangePasswordDTO, UserResponseDTO, restrictedCharsRegex } from "../../util/Models";
import { useEffect, useState } from "react";
import backendUrl from "../../util/Config";
import axios, { AxiosResponse } from "axios";
import usrImg from '../../assets/user.png'
import Swal from "sweetalert2";
import NavbarBootstrap from "../../components/layout/NavbarBootstrap";

interface FormErrors {
    email: boolean,
    newPassword: boolean
  }

const ChangePassword: React.FC = () => {

    const navigate = useNavigate();

    const token = getCookie('token') || useAppContext().token;
    const email = getCookie('email') || useAppContext().email;

    const [changePassword, setChangePassword] = useState<ChangePasswordDTO>({
        email: '',
        newPassword: '',
        oldPassword: 'NOT EMPTY'
    });

    const [errors, setErrors] = useState<FormErrors>({
        email: false,
        newPassword: false
      });

    const [user, setUser] = useState<UserResponseDTO>({
        userId: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        lastLogin: new Date(Date.now())
    });

    const getUser = async () => {
        console.log(email)
        console.log(token)
        let url = backendUrl + `/auth/user/${email}`;

        let response: AxiosResponse<UserResponseDTO> = {} as AxiosResponse<UserResponseDTO>;
        try {
            response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
        } catch (error) {
            //console.log("PUTA MADRE!")
            //console.log(error)
        }

        //console.log(response.data.firstName)

        if (response.data) {
            //console.log("Entré!")
            setUser(response.data);
            setChangePassword({ ...changePassword, oldPassword: user.password });
            //console.log(user)
        }


    }

    useEffect(() => {
        if (email && token) {
            getUser();
        }
    }, [email])

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!errors.email && !errors.newPassword) {
            let url = backendUrl + `/auth/user/changePassword`;
    
            let response: AxiosResponse<UserResponseDTO> = await axios.patch(url, changePassword, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
    
            if (response.status === 200) {
                Swal.fire({
                    title: 'Contraseña cambiada con éxito',
                    text: 'Por favor, inicia sesión nuevamente',
                    icon: 'success',
                    confirmButtonText: 'Ok'
                }).then((result) => {
                    if (result.isConfirmed) {
                        navigate('/user');
                    }
                
                })
            }
        } else {
            alert(" Error al cambiar la contraseña. Caracteres equivocados en el formulario!")
        }

    }

    const handleChangeType = (event: string, type: keyof ChangePasswordDTO) => {
        if (restrictedCharsRegex.test(event)) {
            setErrors({...errors, [type]: true});
        } else {
            setErrors({...errors, [type]: false});
        }
        setChangePassword({ ...changePassword, [type]: event });
        changeOldPassword()
    }

    const changeOldPassword = () => {
    }

    return (
        <>
            <div className="align-self-center d-flex flex-column justify-content-center align-items-center w-100 h-100 bg-image" style={{ backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover' }}>
            <NavbarBootstrap/>
            <Card className="bg-dark text-white shadow" style={{ width: '18rem' }}>
                <Card.Img variant="top" src={usrImg} alt="User Icon" className="rounded-circle align-self-center" style={{ height: '5em', width: '5em'}}/>
                <Card.Body>
                    <Card.Title>{user.firstName.toUpperCase()} {user.lastName.toUpperCase()}</Card.Title>
                    <Card.Text>
                        A continuación podrás cambiar tu contraseña
                    </Card.Text>
                </Card.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>Correo</Form.Label>
                        <Form.Control type="email" placeholder="Correo" isInvalid={errors.email} value={changePassword.email} onChange={(e) => handleChangeType(e.target.value, 'email')}/>
                        <Form.Control.Feedback type="invalid">
                        Input contains restricted characters
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formNewPassword">
                        <Form.Label>Contraseña nueva</Form.Label>
                        <Form.Control type="password" placeholder="Contraseña nueva" isInvalid={errors.newPassword} value={changePassword.newPassword} onChange={(e) => handleChangeType(e.target.value, 'newPassword')}/>
                        <Form.Control.Feedback type="invalid">
                        Input contains restricted characters
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Cambiar contraseña
                    </Button>
                </Form>
                <Button variant="outline-primary" className="mt-4" onClick={() => navigate("/user")}>
                        Regresar
                    </Button>
            </Card>
            </div>
        </>
    )
}

export default ChangePassword;