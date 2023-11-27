import { Navigate } from 'react-router-dom';
import { useAppContext } from '../../util/AppContext';
import { getCookie } from '../../util/Methods';

interface PrivateRouteProps {
    element: React.ReactElement;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ element : Element, ...rest }: PrivateRouteProps) => {

    const token = getCookie("token") || useAppContext().token;

    const isAuthenticated = !!token /* Función para verificar si el usuario está autenticado */;

    return isAuthenticated ? (
        <>
        {Element}
        </>
    ) : (
        <Navigate to="/login" replace />
    );
};

export default PrivateRoute;
