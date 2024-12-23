import MenuComponent from "../components/MenuComponent.tsx";
import CatalogComponentComponent from "../components/CatalogComponentComponent.tsx";

export default function CatalogComponent() {
    return (
        <div style={{display: 'flex'}}>
            <MenuComponent/>
            <CatalogComponentComponent/>
        </div>
    )
}