import { useEffect, useState } from "react";
import { useAppContext } from "../../util/AppContext";
import backendUrl from "../../util/Config";
import { UserResponseDTO } from "../../util/Models";
import axios, { AxiosResponse } from "axios";
import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {

    const { email, token, setEmail, setToken } = useAppContext();

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
        console.log(email)
        console.log(token)
        let url = backendUrl + `/auth/user/${email}`;

        /*const headers = {
            'Authorization': 'Bearer ' + token,
            'Connection': 'keep-alive',
            'Accept-Encoding': 'gzip, deflate, br',
            'Accept': '*',
            /*'Access-Control-Allow-Origin': '*',
        }*/
        // Add a request interceptor
        axios.interceptors.request.use(
            function (config) {
                console.log('Request Headers:', config.headers); // Log the headers
                return config;
            },
            function (error) {
                return Promise.reject(error);
            }
        );

        axios.interceptors.response.use(
            function (response) {
                console.log('Response Headers:', response.headers); // Log the headers
                return response;
            },
            function (error) {
                return Promise.reject(error);
            }   
        );


        let response: AxiosResponse<UserResponseDTO> = {} as AxiosResponse<UserResponseDTO>;
        try {
            response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
        } catch (error) {
            console.log("PUTA MADRE!")
            console.log(error)
        }

        console.log(response.data.firstName)

        if (response.data) {
            console.log("Entré!")
            setUser(response.data);
            console.log(user)
        }


    }

    useEffect(() => {
        if (email && token) {
            getUser();
        }
    }, [email])


    const handleLogOut = () => {
        setEmail('');
        setToken('');
        navigate('/login');
    }



    return (
        <>
            {
                user.firstName == '' ? <h1>Cargando...</h1> :
                    <>
                    <div className="d-flex flex-column justify-content-center align-items-center">
                        <Button variant="primary" onClick={handleLogOut}>Cerrar sesión</Button>
                        <h1>Hola, {user.firstName + " " + user.lastName}</h1>
                        <h2>Correo: {user.email}</h2>
                        <h2>Último login: {user.lastLogin.toString()}</h2>
                        <Button variant="primary" onClick={() => { navigate(`/user/${email}`)}}>Ver perfil</Button>
                    </div>
                    </>
            }
        </>
    )
}

export default Home;