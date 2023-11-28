import { useEffect, useState } from "react";
import { useAppContext } from "../../util/AppContext";
import backendUrl from "../../util/Config";
import { UserResponseDTO } from "../../util/Models";
import axios, { AxiosResponse } from "axios";
import { Button, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { getCookie } from "../../util/Methods";
import NavbarBootstrap from "../../components/layout/NavbarBootstrap";
import containerbg from '../../assets/liquid-cheese.png';
import usrImg from '../../assets/user.png';


const Home: React.FC = () => {

    const token = getCookie('token') || useAppContext().token;
    const email = getCookie('email') || useAppContext().email;

    const navigate = useNavigate();

    const [user, setUser] = useState<UserResponseDTO>({
        userId: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        lastLogin: new Date(Date.now())
    });

    const getUser = async () => {
        //console.log(email)
        //console.log(token)
        let url = backendUrl + `/auth/user/${email}`;

        /*const headers = {
            'Authorization': 'Bearer ' + token,
            'Connection': 'keep-alive',
            'Accept-Encoding': 'gzip, deflate, br',
            'Accept': '*',
            /*'Access-Control-Allow-Origin': '*',
        }*/
        // Add a request interceptor
        /*axios.interceptors.request.use(
            function (config) {
                console.log('Request Headers:', config.headers); // Log the headers
                return config;
            },
            function (error) {
                return Promise.reject(error);
            }
        );*/

        /*axios.interceptors.response.use(
            function (response) {
                console.log('Response Headers:', response.headers); // Log the headers
                return response;
            },
            function (error) {
                return Promise.reject(error);
            }
        );*/


        let response: AxiosResponse<UserResponseDTO> = {} as AxiosResponse<UserResponseDTO>;
        try {
            response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
        } catch (error) {
            alert("Hubo un error al obtener tu información")
        }

        if (response.data) {
            //console.log("Entré!")
            setUser(response.data);
            //console.log(user)
        }


    }

    useEffect(() => {
        if (email && token) {
            getUser();
        }
    }, [email])



    return (
        <>
            {
                user.firstName == '' ? <h1>Cargando...</h1> :
                    <>
                        <div className="align-self-center d-flex flex-column justify-content-center align-items-center w-100 h-100 bg-image" style={{ backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover' }}>
                            <NavbarBootstrap/>
                            <Card className="bg-dark text-white shadow mt-4" style={{ width: '18rem' }}>
                                <Card.Img variant="top" src={usrImg} alt="User Icon" className="rounded-circle align-self-center" style={{ height: '5em', width: '5em' }} />
                                <Card.Body>
                                    <Card.Title>{user.firstName.toUpperCase()} {user.lastName.toUpperCase()}</Card.Title>
                                    <Card.Text>
                                        Actualmente estamos trabajando en el Home! Próximamente estará listo
                                    </Card.Text>
                                </Card.Body>
                                <Card.Body>
                                    <Button variant="primary" onClick={() => { navigate(`/user`) }}>Ver perfil</Button>
                                </Card.Body>
                            </Card>
                        </div>
                    </>
            }
        </>
    )
}

export default Home;