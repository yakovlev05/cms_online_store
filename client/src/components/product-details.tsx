import Image from 'next/image';
import styles from '../styles/product-page.module.css'
import React from "react";

interface ProductProps {
    name: string;
    description: string;
    composition: string;
    price: number;
    img: string;
}
  
const ProductDetails: React.FC<ProductProps> = ({name, description, composition, price, img}) => {
    return ( 
        <div className={styles.productDetails}>
            <Image src={img} alt="изображение букета" width={480} height={640} style={{borderRadius:12}}/>
            <div className={styles.productInfo}>
                <div>
                    <h1 className={styles.h1}>{name}</h1>
                    <span className={styles.price}>{price}₽</span>
                </div>
                <button className={styles.addToCart}>В корзину</button>
                <p className={styles.desc}>{description}</p>
                <p className={styles.comp}><b>Состав:</b> {composition}</p>
            </div>
        </div>
    );
}
  
export default ProductDetails;