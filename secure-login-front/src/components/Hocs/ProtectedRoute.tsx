import { Route, Navigate } from 'react-router-dom';
import { RouteProps } from 'react-router-dom';
import { useAppContext } from '../../util/AppContext';
import { useEffect, useState } from 'react';
import backendUrl from '../../util/Config';
import axios, { AxiosResponse } from 'axios';

const ProtectedRoute: React.FC<RouteProps> = ({ element, ...rest }: RouteProps) => {
    const {token} = useAppContext();/* Obtener el token almacenado en el almacenamiento local */;
    const [isAdmin, setIsAdmin] = useState(false); /* Variable para verificar si el usuario es administrador */;
    const [loading, setLoading] = useState(true);

    const getRole = async () => {
        let url = backendUrl + '/auth/role';

        const headers = {
            'Authorization': 'Bearer ' + token,
        }

        let response: AxiosResponse<boolean> = await axios.get(url, {headers: headers});

        console.log(response.data)

        setIsAdmin(response.data);
        setLoading(false);
    
    }
    

    useEffect(() => {
        if(token){
            getRole();
        }
    }, [token])
    

    return isAdmin && !loading ? (
        <Route {...rest} element={element} />
    ) : (
        <Navigate to="/login" replace />
    );
};

export default ProtectedRoute;
