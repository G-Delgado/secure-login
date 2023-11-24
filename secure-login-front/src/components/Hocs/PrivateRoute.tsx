import { Route, Navigate, RouteProps } from 'react-router-dom';
import { useAppContext } from '../../util/AppContext';

const PrivateRoute: React.FC<RouteProps> = ({ element, ...rest }: RouteProps) => {

    const { token } = useAppContext();

    const isAuthenticated = !!token /* Función para verificar si el usuario está autenticado */;

    return isAuthenticated ? (
        <Route {...rest} element={element} />
    ) : (
        <Navigate to="/login" replace />
    );
};

export default PrivateRoute;
