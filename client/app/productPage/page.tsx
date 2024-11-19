import Header from "@/src/components/header";
import ProductMain from "@/src/components/product-main";
import styles from "@/src/styles/home.module.css"
import SimilarProd from "@/src/components/similar-products";
import Pagination from "@/src/components/ui/pagination";
import Footer from "@/src/components/footer";

export default function ProductPage() {
    return (
        <div className={styles.home}>
            <Header logo="/assets/placeholder/logo.svg"/>
            <ProductMain/>
            <SimilarProd/>
            <Pagination/>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    );
}