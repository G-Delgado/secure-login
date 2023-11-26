import { Route, Navigate } from 'react-router-dom';
import { useAppContext } from '../../util/AppContext';

interface PrivateRouteProps {
    element: React.ReactElement;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ element : Element, ...rest }: PrivateRouteProps) => {

    const { token } = useAppContext();

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
