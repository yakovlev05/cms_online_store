'use client'
import Image from "next/image";
import styles from '../styles/product-card.module.css';
import React, {useEffect, useState} from "react";
import Link from "next/link";
import {addCart, checkIsInCart} from "@/src/api/service/cartService";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {toast, Toaster} from "react-hot-toast";
import MiniLoader from "@/src/components/ui/mini-loader"; // Импортируем Link из next/link

interface Props {
    price: number;
    oldPrice: number;
    name: string;
    img: string;
    urlName: string;
    product: ProductResponseDto
}

const ProductCard: React.FC<Props> = ({price, oldPrice, name, img, urlName, product}) => {
    const [isInCart, setIsInCart] = useState(false);
    const [isChecking, setIsChecking] = useState(true);

    const handleClickAddToCart = () => {
        addCart({productUrlName: urlName}, product)
            .then(() => {
                setIsInCart(true)
                toast.success('Добавлено в корзину')
            })
            .catch(err => toast.error(err.message));
    }

    useEffect(() => {
        checkIsInCart(product.urlName)
            .then(r => {
                setIsInCart(r)
                setTimeout(() => setIsChecking(false), 500)
            })
            .catch(err => toast.error(err.message));
    }, [product.urlName]);

    return (
        <div className={styles.card}>
            <Toaster/>
            <Image src={img} className={styles.img} alt='изображение букета' width={256} height={256}/>
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

                {
                    !isInCart && !isChecking &&
                    <button className={styles.buttonCart}>
                        <Image src='/assets/icon/cart.svg' alt='добавление в корзину' width='28' height='29'
                               onClick={handleClickAddToCart}/>
                    </button>

                }
                {
                    isInCart && !isChecking &&
                    <button className={`${styles.buttonCart} ${styles.buttonCartInactive}`}>
                        <Image src='/assets/icon/in_cart.svg' alt='добавление в корзину' width='28' height='29'/>
                    </button>
                }

                {
                    isChecking &&
                    <MiniLoader color='#798648' fontSize={28}/>
                }
            </div>
        </div>
    );
}

export default ProductCard;
