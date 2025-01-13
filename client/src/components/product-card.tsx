'use client'
import Image from "next/image";
import styles from '../styles/product-card.module.css';
import React, {useEffect, useState} from "react";
import Link from "next/link";
import {addCart, checkIsInCart} from "@/src/api/service/cartService";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {toast, Toaster} from "react-hot-toast";
import MiniLoader from "@/src/components/ui/mini-loader";

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
    const [isAllComponentsInStock, setIsAllComponentsInStock] = useState(true);

    useEffect(() => {
        // Проверка наличия всех компонентов
        const allComponentsInStock = product.components.every(component => component.inStock);
        setIsAllComponentsInStock(allComponentsInStock);

        checkIsInCart(product.urlName)
            .then(r => {
                setIsInCart(r)
                setTimeout(() => setIsChecking(false), 500)
            })
            .catch(err => toast.error(err.message));
    }, [product.urlName, product.components]);

    const handleClickAddToCart = () => {
        if (!isAllComponentsInStock) return;

        addCart({productUrlName: urlName}, product)
            .then(() => {
                setIsInCart(true)
                toast.success('Добавлено в корзину')
            })
            .catch(err => toast.error(err.message));
    }

    return (
        <div className={styles.card}>
            <Toaster/>
            {!isAllComponentsInStock && !isChecking && (
                <div className={styles.outOfStockOverlay}>
                    Уточните наличие в магазине
                </div>
            )}
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
                    !isInCart && !isChecking && isAllComponentsInStock &&
                    <button
                        className={styles.buttonCart}
                        onClick={handleClickAddToCart}
                    >
                        <Image
                            src='/assets/icon/cart.svg'
                            alt='добавление в корзину'
                            width='28'
                            height='29'
                        />
                    </button>
                }
                {
                    isInCart && !isChecking &&
                    <button className={`${styles.buttonCart} ${styles.buttonCartInactive}`}>
                        <Image
                            src='/assets/icon/in_cart.svg'
                            alt='добавление в корзину'
                            width='28'
                            height='29'
                        />
                    </button>
                }

                {
                    (!isInCart && !isChecking && !isAllComponentsInStock) &&
                    <button
                        className={`${styles.buttonCart} ${styles.buttonCartDisabled}`}
                        disabled
                    >
                        <Image
                            src='/assets/icon/cart.svg'
                            alt='добавление в корзину'
                            width='28'
                            height='29'
                        />
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
