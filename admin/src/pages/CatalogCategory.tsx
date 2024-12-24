import MenuComponent from "../components/MenuComponent.tsx";
import CatalogCategoryComponent from "../components/CatalogCategoryComponent.tsx";

export default function CatalogCategory() {
    return (
        <div style={{display: 'flex'}}>
            <MenuComponent/>
            <CatalogCategoryComponent/>
        </div>
    )
}