"use client";

import React, {useEffect, useState} from "react";
import ProductCard from "@/src/components/product-card";
import styles from "@/src/styles/new.module.css";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {getAllProducts} from "@/src/api/service/catalogService";
import {toast} from "react-hot-toast";
import Pagination from "@/src/components/ui/pagination";


interface ProductListProps {
    category: string;
}

const ProductList: React.FC<ProductListProps> = ({category}) => {
    const [products, setProducts] = useState<ProductResponseDto[] | undefined>(undefined);
    const [pagination, setPagination] = useState<{ page: number, keySort: 'name' | 'price' }>({
        page: 0,
        keySort: 'name',
    });

    useEffect(() => {
        console.log('changed')
        getAllProducts(pagination.page, 8, 'asc', pagination.keySort, '', category)
            .then(r => {
                console.log(r);
                setProducts(r)
            })
            .catch(err => toast.error(err.message));
    }, [pagination, category]);

    const onClickPage = (num: number) => {
        setPagination({...pagination, page: num});
    }

    return (
        <div className={styles.home}>
            <div>
                <div className={styles.sortContainer}>
                    <span>Сортировать по: </span>
                    <button onClick={() => setPagination({page: 0, keySort: 'name'})}> имени
                    </button>
                    <span> / </span>
                    <button onClick={() => setPagination({page: 0, keySort: 'price'})}>цене
                    </button>
                </div>

                <div className={styles.cardsContainer}>
                    {
                        products === undefined && <p>Загрузка...</p>
                    }
                    {
                        products && products.length === 0 && pagination.page === 0 && <p>Товары в данной категории отсутствуют.</p>
                    }
                    {
                        products && products.length === 0 && pagination.page !== 0 &&<p>Больше товаров нет.</p>
                    }
                    {
                        products && products.length > 0 && products.map(product =>
                            <ProductCard price={product.price} oldPrice={product.priceDiscount} name={product.name}
                                         img={product.mainPhotoUrl} urlName={product.urlName} key={product.id}/>
                        )
                    }
                </div>
            </div>
            <Pagination onClick={onClickPage} page={pagination.page}/>
        </div>
    );
};

export default ProductList;
