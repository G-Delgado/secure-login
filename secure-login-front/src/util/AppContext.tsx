import React, { createContext, useContext, useState } from 'react';

// Definir el tipo para los datos que se compartirán en el contexto
export type AppContextType = {
    token: string;
    setToken: React.Dispatch<React.SetStateAction<string>>;
    email: string;
    setEmail: React.Dispatch<React.SetStateAction<string>>;
};

// Crear el contexto
export const AppContext = createContext<AppContextType>({} as AppContextType);

// Proveedor de contexto personalizado
export const AppProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [token, setToken] = useState<string>(''); // Agregar un estado para almacenar el token
    const [email, setEmail] = useState<string>('');

    // Empaquetar los datos y funciones que quieres compartir
    const datosContextValue: AppContextType = {
        token, setToken,
        email, setEmail
    };

    return (
        <AppContext.Provider value={datosContextValue}>
            {children}
        </AppContext.Provider>
    );
};

// Función de utilidad para usar el contexto en componentes
export const useAppContext = () => {
    const context = useContext(AppContext);
    if (!context) {
        throw new Error('useDatosContext debe usarse dentro de un AppProvider');
    }
    return context;
};
