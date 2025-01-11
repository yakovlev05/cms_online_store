import ProductDetails from "./product-details";
import Breadcrumbs from "./ui/breadcrumbs";
import React from "react";
import {ComponentResponseDto, ProductResponseDto} from "@/src/api/models/response/catalog";

interface Props {
    product: ProductResponseDto;
}

const ProductMain: React.FC<Props> = ({product}) => {
    const category = product.categories[0]
    const breadcrumbs = [
        {label: "Главная", href: "/"},
        {label: category.name, href: `/catalog/${category.urlName}`},
        {label: product.name, href: `/product/${product.urlName}`},
    ];

    const getComposition = (components: ComponentResponseDto[]) => {
        return components.map((component) => component.name).join(', ')
    }

    return (
        <div>
            <Breadcrumbs breadcrumbs={breadcrumbs}/>
            <ProductDetails name={product.name}
                            description={product.description}
                            composition={getComposition(product.components)}
                            price={product.price}
                            img={product.mainPhotoUrl}/>
        </div>
    );
}

export default ProductMain;