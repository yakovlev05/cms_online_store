'use client'
import styles from '@/src/styles/new.module.css';
import ProductCard from "@/src/components/product-card";
import React, {useEffect, useState} from "react";
import {getAllProducts} from "@/src/api/service/catalogService";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {toast} from "react-hot-toast";

interface Props {
    categoryUrl: string;
}

const SimilarProd: React.FC<Props> = ({categoryUrl}) => {
    const [products, setProducts] = useState<ProductResponseDto[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAllProducts(0, 8, 'desc', 'createdAt', '', categoryUrl)
            .then((r) => {
                setLoading(false)
                setProducts(r);
            })
            .catch((err) => toast.error(err.message));
    }, [categoryUrl]);


    return (
        <div className={styles.new}>
            <h2 className={styles.title}>Вам также может понравиться:</h2>
            <div className={styles.cardsContainer}>
                {
                    !loading && products.map((product, index) => (
                        <ProductCard price={product.price} oldPrice={product.priceDiscount} name={product.name}
                                     img={product.mainPhotoUrl} urlName={product.urlName}
                                     product={product} key={index}/>
                    ))
                }
                {
                    loading && <p>Загрузка...</p>
                }
            </div>
        </div>
    )
}

export default SimilarProd;
