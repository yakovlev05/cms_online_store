import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import Login from "./pages/Login.tsx";
import {Toaster} from "react-hot-toast";

// Использую для роутинга https://reactrouter.com/start/library/installation
createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Toaster
        position='bottom-right'/> {/*Уведомления от https://react-hot-toast.com/*/}
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Login/>}/>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
)

