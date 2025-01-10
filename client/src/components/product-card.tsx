import Image from "next/image";
import styles from '../styles/product-card.module.css';
import React from "react";
import Link from "next/link"; // Импортируем Link из next/link

interface Props {
    price: number;
    oldPrice: number;
    name: string;
    img: string;
    urlName: string;
}

const ProductCard: React.FC<Props> = ({price, oldPrice, name, img, urlName}) => {
    return (
        <div className={styles.card}>
            <Image src={img} alt='изображение букета' width='256' height='256'/>
            <div className={styles.cardBottom}>
                <div className={styles.cardText}>
                    <Link href={`/product/${urlName}`} passHref>
                        <p className={styles.name}>{name}</p>
                    </Link>
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
    );
}

export default ProductCard;
