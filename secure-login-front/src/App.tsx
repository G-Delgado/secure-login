import './App.css'
import { AppProvider } from './util/AppContext'
import RoutesComponent from './routes/RoutesComponent'

import 'bootstrap/dist/css/bootstrap.min.css';
import NavbarBootstrap from './components/layout/NavbarBootstrap';


function App() {

  
  return (
    <>
      
      <div className="w-100 d-flex justify-content-center h-100 " /*style={{minHeight: "100vh", height: '2500px'}}*/>
      <AppProvider>
        <RoutesComponent/>
      </AppProvider>
    </div>
    </>
  )
}

export default App
