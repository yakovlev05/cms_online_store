'use client'
import Image from 'next/image';
import styles from '../styles/product-page.module.css'
import React, {useEffect, useState} from "react";
import {addCart, checkIsInCart} from "@/src/api/service/cartService";
import {toast} from "react-hot-toast";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import MiniLoader from "@/src/components/ui/mini-loader";

interface ProductProps {
    name: string;
    description: string;
    composition: string;
    price: number;
    img: string;
    urlName: string;
    product: ProductResponseDto;
}

const ProductDetails: React.FC<ProductProps> = ({name, description, composition, price, img, urlName, product}) => {
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
        <div className={styles.productDetails}>
            <div className={styles.imageContainer}>
                {!isAllComponentsInStock && !isChecking && (
                    <div className={styles.outOfStockOverlay}>
                        Уточните наличие в магазине
                    </div>
                )}
                <Image
                    className={styles.img}
                    src={img}
                    alt="изображение букета"
                    width={480}
                    height={640}
                    style={{
                        opacity: !isAllComponentsInStock && !isChecking ? 0.5 : 1
                    }}
                />
            </div>
            <div className={styles.productInfo}>
                <div>
                    <h1 className={styles.h1}>{name}</h1>
                    <span className={styles.price}>{price}₽</span>
                </div>
                {
                    !isChecking && !isInCart && isAllComponentsInStock &&
                    <button className={styles.addToCart} onClick={handleClickAddToCart}>В корзину</button>
                }

                {
                    !isChecking && isInCart &&
                    <button className={styles.addToCart}>Уже в корзине</button>
                }

                {
                    (!isChecking && !isAllComponentsInStock) &&
                    <button
                        className={styles.addToCart}
                        disabled
                        style={{
                            backgroundColor: '#ccc',
                            color: '#666',
                            cursor: 'not-allowed'
                        }}
                    >
                        Товара нет в наличии
                    </button>
                }

                {
                    isChecking &&
                    <MiniLoader color='#798648' fontSize={44}/>
                }
                <p className={styles.desc}>{description}</p>
                <p className={styles.comp}><b>Состав:</b> {composition}</p>
            </div>
        </div>
    );
}

export default ProductDetails;
