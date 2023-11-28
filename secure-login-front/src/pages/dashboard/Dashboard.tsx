import { Container } from 'react-bootstrap';
import UserListView from './UsersList/UserListView';
import CardBootstrap from '../../components/CardBootstrap';
import { useEffect, useState } from 'react';
import backendUrl from '../../util/Config';
import { useAppContext } from '../../util/AppContext';
import axios, { AxiosResponse } from 'axios';
import containerbg from '../../assets/liquid-cheese2.png';
import { getCookie } from '../../util/Methods';
import NavbarBootstrap from '../../components/layout/NavbarBootstrap';


const Dashboard: React.FC = () => {

    const token = getCookie('token') || useAppContext().token;
    const [numberOfUsers, setNumberOfUsers] = useState<number>(0);

    const getNumberOfUsers = async () => {
        let url = backendUrl + '/auth/count';
        
        let response: AxiosResponse<number> = {} as AxiosResponse<number>;
    
        try {
            response= await axios.get(url, {
                headers: {
                    Authorization: 'Bearer ' + token
                }});

        } catch (error) {
            alert("Hubo un error al obtener el número de usuarios")
        }
        
        if (response.status == 200) {
            setNumberOfUsers(response.data);
        }
    }   

    useEffect(() => {
        if (token != '') {
            getNumberOfUsers();
        }
    },[token])

    return (
        <>
            <Container fluid className=' w-100 h-100 bg-image overflow-auto mt-4' style={{backgroundImage: `url(${containerbg})`, backgroundRepeat: 'no-repeat', backgroundSize: 'cover'}}>
                <NavbarBootstrap/>
                <div className='w-100  my-3 rounded-3 '>
                    <h3>Dashboard</h3>
                </div>
                <div className=' w-100s'>
                    <div className='align-self-center my-3 rounded-3'>
                        <h3>Hola, Admin. Bienvenido al Dashboard</h3>
                    </div>
                    <div className='align-self-center d-flex my-2 rounded-3 justify-content-center align-items-center'>
                        {/* Here goes the number of users and a timeline of numbers of people in each last login */}
                        {numberOfUsers != 0? <CardBootstrap numberOfUsers={numberOfUsers} /> : <></>}
                    </div>
                </div>
                <div className='p-4 border shadow w-100 my-3 rounded-3 bg-white'>
                    <div>
                        <UserListView />
                    </div>
                </div>
            </Container>
        </>
    )
}

export default Dashboard;