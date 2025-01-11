import Header from "@/src/components/header";
import ProductMain from "@/src/components/product-main";
import styles from "@/src/styles/home.module.css"
import SimilarProd from "@/src/components/similar-products";
import Footer from "@/src/components/footer";
import {getProductInfo} from "@/src/api/service/catalogService";
// import Pagination from "@/src/components/ui/pagination"

export default async function ProductPage({params}: { params: Promise<{ product: string }> }) {
    const productUrl = (await params).product;
    const product = await getProductInfo(productUrl);

    return (
        <div className={styles.home}>
            <Header logo="/assets/placeholder/logo.svg"/>
            <ProductMain product={product}/>
            <SimilarProd categoryUrl={product.categories[0].urlName}/>
            {/*<Pagination />*/}
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    );
}
