import { Button, Card, ListGroup } from "react-bootstrap";
import { ChangePasswordDTO, UserResponseDTO } from "../util/Models";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';
import usrImg from '../assets/user.png';

interface UserInfoCardProps {
    user: UserResponseDTO;
    token: string;
    onPasswordChange: () => void;
    onHandleContent: () => void;

}

const UserInfoCard: React.FC<UserInfoCardProps> = ({ user, token, onPasswordChange, onHandleContent }  : UserInfoCardProps) => {

    const navigate = useNavigate();

    const handleNoMoreContent = () => {
        onHandleContent();
    }

    const handleChangePassword = () => {
        onPasswordChange();
    }

    return (
        <>  
            <Card className="bg-dark text-white shadow mt-4" style={{ width: '18rem' }}>
                <Card.Img variant="top" src={usrImg} alt="User Icon" className="rounded-circle align-self-center" style={{ height: '5em', width: '5em'}}/>
                <Card.Body>
                    <Card.Title>{user.firstName.toUpperCase()} {user.lastName.toUpperCase()}</Card.Title>
                    <Card.Text>
                        A continuación podrás ver tu información básica
                    </Card.Text>
                </Card.Body>
                <ListGroup className="list-group-flush border-secondary">
                    <ListGroup.Item className="bg-dark text-white border-secondary">{user.userId}</ListGroup.Item>
                    <ListGroup.Item className="bg-dark text-white border-secondary">{user.email}</ListGroup.Item>
                    <ListGroup.Item className="bg-dark text-white border-secondary">{user.lastLogin.toString()}</ListGroup.Item>
                </ListGroup>
                <Card.Body>
                    <Button onClick={handleNoMoreContent} className="btn-primary mb-3">Más contenido</Button>
                    <Button onClick={() => navigate("/")} className="btn-primary mb-3">Regresar</Button>
                    <Button onClick={handleChangePassword} className="btn-primary">Cambiar contraseña</Button>
                </Card.Body>
            </Card>
        </>
    )
}

export default UserInfoCard;