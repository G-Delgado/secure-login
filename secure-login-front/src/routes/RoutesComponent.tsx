import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../pages/login/Login';
import UserListView from '../pages/UsersList/UserListView';
import UserInfo from '../pages/userInfo/UserInfo';
import ChangePassword from '../pages/changePassword/ChangePassword';
import AdmChangePassword from '../pages/changePassword/AdmChangePassword/AdmChangePassword';
import SignUp from '../pages/signUp/SignUp';
import Dashboard from '../pages/dashboard/Dashboard';
import PrivateRoute from '../components/Hocs/PrivateRoute';
import Home from '../pages/home/Home';
import ForgotPassword from '../pages/forgotPassword/ForgotPassword';

const RoutesComponent: React.FC = () => {
    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path="/signup" element={<SignUp/>} />
                <Route path="/login" element={<Login/>} />
                <Route path="/forgotPassword" element={<ForgotPassword/>} />

                {/* Private routes */}
                <Route path="/" element={<PrivateRoute element={<Home/>} />}/>
                <Route path='/user/:email' element={<PrivateRoute element={<UserInfo/>} />}/>
                <Route path='/changePassword' element={<PrivateRoute element={<ChangePassword/>}/>}/>

                {/* Protected routes 'Admin' */}
                <Route path="/dashboard" element={<PrivateRoute element={<Dashboard/>} />}/>
                <Route path='/users' element={<PrivateRoute element={<UserListView/>} />}/>
                <Route path='/changePassword/:email' element={<PrivateRoute element={<AdmChangePassword/>} />}/>

                {/* Add more routes as needed */}
            </Routes>
        </BrowserRouter>
    );
}

export default RoutesComponent;