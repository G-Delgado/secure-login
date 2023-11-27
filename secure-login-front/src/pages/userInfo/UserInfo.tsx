import { useEffect, useState } from "react";
import { useAppContext } from "../../util/AppContext";
import backendUrl from "../../util/Config";
import { UserResponseDTO } from "../../util/Models";
import axios, { AxiosResponse } from "axios";
import UserInfoCard from "../../components/UserInfoCard";
import containerbg from '../../assets/liquid-cheese.png';
import { getCookie } from "../../util/Methods";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import NavbarBootstrap from "../../components/layout/NavbarBootstrap";

const UserInfo: React.FC = () => {

    const navigate = useNavigate();
    const token = getCookie('token') || useAppContext().token;
    const email = getCookie('email') || useAppContext().email;

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
            //console.log("PUTA MADRE!")
            //console.log(error)
        }

        //console.log(response.data.firstName)

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

    const handleChangePassword = () => {
        navigate('/changePassword');
    }

    const handleNoMoreContent = () => {
        alert("Estamos trabajando en agregar más contenido!")
    }




    return (
        <>
            {
                user.firstName == '' ? <h1>Cargando...</h1> :
                    <>
                    <div className="align-self-center d-flex flex-column justify-content-center align-items-center w-100 h-100 bg-image" style={{backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover'}}>
                        <NavbarBootstrap/>
                        <UserInfoCard user={user} token={token} onPasswordChange={handleChangePassword} onHandleContent={handleNoMoreContent}/>
                    </div>
                    </>
            }
        </>
    )
}

export default UserInfo;