import { Button, Card } from "react-bootstrap";

interface CardProps {
  numberOfUsers: number;
}

const CardBootstrap: React.FC<CardProps> = ({numberOfUsers} : CardProps) => {
    return (
      <Card className="my-2 py-3 rounded-3 border-light shadow bg-dark" style={{ width: '18rem', color: '#fff' }}>
        <Card.Title className="text-primary" style={{}}>{numberOfUsers? numberOfUsers : 0}</Card.Title>
        <Card.Body>
          <Card.Title>Usuarios</Card.Title>
          <Card.Text>
            Actualmente se encuentran {numberOfUsers? numberOfUsers : 0} usuarios registrados en el sistema.
          </Card.Text>
        </Card.Body>
      </Card>
    );
  }
  
  export default CardBootstrap;