import { Route, Navigate } from 'react-router-dom';
import { useAppContext } from '../../util/AppContext';
import { useEffect, useState } from 'react';
import backendUrl from '../../util/Config';
import axios, { AxiosResponse } from 'axios';

interface ProtectedRouteProps {
    element: React.ReactElement;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ element : Element, ...rest }: ProtectedRouteProps) => {
    const {token} = useAppContext();/* Obtener el token almacenado en el almacenamiento local */;
    const [isAdmin, setIsAdmin] = useState(false); /* Variable para verificar si el usuario es administrador */;
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const getRole = async () => {
            if (token) {
                let url = backendUrl + '/auth/role';

                const headers = {
                    'Authorization': 'Bearer ' + token,
                }

                try {
                    let response: AxiosResponse<boolean> = await axios.get(url, { headers });
                    setIsAdmin(response.data);
                } catch (error) {
                    // Manejar errores de solicitud
                    console.error(error);
                } finally {
                    setLoading(false);
                }
            }
        };

        getRole();
    }, [token]);

    if (loading) {
        return null;
    }
    

    return isAdmin ? (
        <>
        {Element}
        </>
    ) : (
        <Navigate to="/login" replace />
    );
};

export default ProtectedRoute;
