'use client'
import styles from '@/src/styles/new.module.css';
import ProductCard from "@/src/components/product-card";
import {getAllProducts} from "@/src/api/service/catalogService";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {useEffect, useState} from "react";
import {toast} from "react-hot-toast";
import {Toast} from "next/dist/client/components/react-dev-overlay/internal/components/Toast";

const New = () => {
    const [products, setProducts] = useState<ProductResponseDto[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAllProducts(0, 8, 'desc', 'createdAt', '', '')
            .then((r) => {
                setLoading(false);
                setProducts(r);
            })
            .catch(err => toast.error(err.message));
    }, []);


    return (
        <div className={styles.new}>
            <Toast/>
            <h2 className={styles.title}>Новинки</h2>
            <div className={styles.cardsContainer}>
                {
                    !loading && products.map((product) =>
                        <ProductCard price={product.price} oldPrice={product.priceDiscount} name={product.name}
                                     img={product.mainPhotoUrl} urlName={product.urlName}
                                     product={product} key={product.id}/>
                    )
                }
                {
                    loading && <p>Загрузка...</p>
                }
            </div>
        </div>
    )
}

export default New;
