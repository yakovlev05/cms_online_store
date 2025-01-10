import styles from '@/src/styles/new.module.css';
import ProductCard from "@/src/components/product-card";
import React from "react";
import {getAllProducts} from "@/src/api/service/catalogService";

interface Props {
    categoryUrl: string;
}

const SimilarProd: React.FC<Props> = async ({categoryUrl}) => {
    const products = await getAllProducts(0, 8, 'desc', 'createdAt', '', categoryUrl);
    return (
        <div className={styles.new}>
            <h2 className={styles.title}>Вам также может понравиться:</h2>
            <div className={styles.cardsContainer}>
                {
                    products.map((product, index) => (
                        <ProductCard price={product.price} oldPrice={product.priceDiscount} name={product.name}
                                     img={product.mainPhotoUrl} urlName={product.urlName} key={index}/>
                    ))
                }
            </div>
        </div>
    )
}

export default SimilarProd;
