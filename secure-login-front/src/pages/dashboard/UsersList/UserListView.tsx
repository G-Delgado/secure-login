import { useState, useEffect } from "react";
import { ChangePasswordDTO, UserResponseDTO } from "../../../util/Models";
import { Table, Button } from 'react-bootstrap';
import Swal from 'sweetalert2';
import backendUrl from "../../../util/Config";
import axios, { AxiosResponse } from "axios";
import { useAppContext } from "../../../util/AppContext";
import { getCookie } from "../../../util/Methods";


const UserListView: React.FC = () => {

    const token = getCookie('token') || useAppContext().token;
    const email = getCookie('email') || useAppContext().email;
    const [usuarios, setUsuarios] = useState<UserResponseDTO[]>([]);

    const [selectedUser, setSelectedUser] = useState<UserResponseDTO>({
        userId: '', // UUID
        firstName: '',
        lastName: '',
        email: ',',
        password: ',',
        lastLogin: new Date(Date.now())

    });

    const loadUsers = async () => {
        let url = backendUrl + '/auth/users';

        let response: AxiosResponse<UserResponseDTO[]> = {} as AxiosResponse<UserResponseDTO[]>;
        try {
             response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
        } catch(error) {
            alert("Hubo un error al obtener la lista de usuarios")
        }

        if (response.status == 200) {
            //console.log(response.data);
            setUsuarios(response.data);
        }
    }

    useEffect(() => {
        if (token != '' ) {
            loadUsers();
        }
    }, [token])
    

    const handleRowClick = (user: UserResponseDTO) => {
        setSelectedUser(user);
        // Aquí puedes realizar acciones con el usuario seleccionado, como abrir un modal para editar
        //console.log('Usuario seleccionado:', user);
        Swal.fire({
            title: 'Ingrese los datos para cambiar la contraseña',
            text: 'Por favor, ingrese la contraseña nueva:',
            html:
            `<input id="swal-input1" class="swal2-input" placeholder="Correo" value=${user.email} readonly>` +
              '<input id="swal-input2" class="swal2-input" placeholder="Contraseña nueva">',
            confirmButtonText: 'Cambiar contraseña',
            focusConfirm: false,
            preConfirm: () => {
              const email = (document.getElementById('swal-input1') as HTMLInputElement).value;
              const newPassword = (document.getElementById('swal-input2') as HTMLInputElement).value;
              return { email, newPassword };
            }
          }).then(async result => {
            if (result.isConfirmed) {
              const { email, newPassword } = result.value;
              //console.log('Correo: ', email);
              //console.log('Contraseña nueva:', newPassword);

              let url = backendUrl + `/auth/user/changePassword`;

              let changePasswordDto: ChangePasswordDTO = {
                    email: email,
                    oldPassword: user.password,    
                    newPassword: newPassword
              }

              let response: AxiosResponse<UserResponseDTO> = await axios.patch(url, changePasswordDto, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }

              })

              if (response.status == 200) {
                Swal.fire(
                    'Contraseña cambiada!',
                    'La contraseña ha sido cambiada.',
                    'success'
                )
              } else {
                alert("Error al cambiar la contraseña")
              }
            }
          });
    };

    const handleDeleteUser = async (user: UserResponseDTO) => {
        Swal.fire({
            title: '¿Estás seguro?',
            text: "No podrás revertir esta acción!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, bórralo!',
            cancelButtonText: 'No, cancelar!',
            reverseButtons: true
        }).then(async (result) => {
            if (result.isConfirmed) {

                let url = backendUrl + `/auth/user/${user.email}`

                let response: AxiosResponse<string>= await axios.delete(url, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })

                if (response.status == 200) {
                    Swal.fire(
                        'Borrado!',
                        'El usuario ha sido borrado.',
                        'success'
                    )
                    loadUsers();
                }

            } else if (
                result.dismiss === Swal.DismissReason.cancel
            ) {
                Swal.fire(
                    'Cancelado',
                    'El usuario no ha sido borrado.',
                    'error'
                )
            }
        })
    }

   

    return (
        <>
            <h3 className="mb-4">Lista de usuarios</h3>
            <Table striped bordered hover className="shadow">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Email</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    {usuarios.map((usuario) => (
                        <tr key={usuario.userId} onDoubleClick={() => handleRowClick(usuario)}>
                            <td>{usuario.userId}</td>
                            <td>{usuario.firstName}</td>
                            <td>{usuario.lastName}</td>
                            <td>{usuario.email}</td>
                            <td><Button variant="outline-danger" onClick={() => handleDeleteUser(usuario)}>Eliminar usuario</Button></td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </>
    )
}

export default UserListView;