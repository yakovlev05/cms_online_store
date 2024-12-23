import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import Login from "./pages/Login.tsx";
import {Toaster} from "react-hot-toast";
import 'normalize.css' // https://github.com/necolas/normalize.css сбрасываем встроенные стили
import CatalogCategory from "./pages/CatalogCategory.tsx";
import CatalogCollection from "./pages/CatalogCollection.tsx";
import CatalogComponent from "./pages/CatalogComponent.tsx";
import CatalogProduct from "./pages/CatalogProduct.tsx";
import './index.css'

// Использую для роутинга https://reactrouter.com/start/library/installation
createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Toaster
            position='bottom-right'/> {/*Уведомления от https://react-hot-toast.com/*/}
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Login/>}/>
                <Route path='/catalog/category' element={<CatalogCategory/>}/>
                <Route path='/catalog/collection' element={<CatalogCollection/>}/>
                <Route path='/catalog/component' element={<CatalogComponent/>}/>
                <Route path='/catalog/product' element={<CatalogProduct/>}/>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
)

