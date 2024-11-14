import Image from "next/image";
import styles from './styles.module.css'
import React from "react";

interface Props {
    price: number;
    oldPrice: number;
    name: string;
    img: string;
}

const ProductCard: React.FC<Props> = ({price, oldPrice, name, img}) => {
    return (
        <div className={styles.card}>
            <Image src={img} alt='изображение букета' width='256' height='256'/>
            <div className={styles.cardBottom}>
                <div className={styles.cardText}>
                    <p className={styles.name}>{name}</p>
                    <p className={styles.priceLabel}>Цена:</p>
                    <p>
                        <span>{price}₽</span>
                        <span className={styles.oldPrice}>{oldPrice}₽</span>
                    </p>
                </div>
                <button className={styles.buttonCart}>
                    <Image src='/assets/icon/cart.svg' alt='добавление в корзину' width='28' height='29'/>
                </button>
            </div>
        </div>
    )
}

export default ProductCard;