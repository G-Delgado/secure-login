
// Request:

export interface LoginDTO {
    email: string;
    password: string;
}

export interface SignUpDTO {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface ChangePasswordDTO {
    email: string;
    oldPassword: string;
    newPassword: string;
}

// Response:

enum Role {
    Admin = 'ADMIN',
    User = 'User'
}

export interface TokenDTO {
    token: string;
    email: string;
    role: Role;
}

export interface UserResponseDTO {
    userId: string; // UUID
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    lastLogin: Date; // Date
}