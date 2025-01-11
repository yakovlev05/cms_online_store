'use client'
import Header from "@/src/components/header";
import ProductMain from "@/src/components/product-main";
import styles from "@/src/styles/home.module.css"
import SimilarProd from "@/src/components/similar-products";
import Footer from "@/src/components/footer";
import {getProductInfo} from "@/src/api/service/catalogService";
import {useEffect, useState} from "react";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {useParams} from "next/navigation";
import {toast, Toaster} from "react-hot-toast";
// import Pagination from "@/src/components/ui/pagination"

export default function ProductPage() {
    const [loading, setLoading] = useState(true);
    const [product, setProduct] = useState<ProductResponseDto>();
    const params = useParams();
    const productUrl = params.product as string;

    useEffect(() => {
        getProductInfo(productUrl)
            .then(r => {
                setLoading(false);
                setProduct(r)
            })
            .catch(err => toast.error(err.message));
    }, [productUrl]);

    return (
        <div className={styles.home}>
            <Toaster/>
            <Header logo="/assets/placeholder/logo.svg"/>
            {
                !loading && product && <ProductMain product={product}/>
            }
            {
                !loading && product && <SimilarProd categoryUrl={product.categories[0].urlName}/>
            }

            {
                loading && <p className={styles.load}>Загрузка...</p>
            }

            {/*<Pagination />*/}
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    );
}
