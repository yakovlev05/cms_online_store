import styles from '@/src/styles/new.module.css';
import ProductCard from "@/src/components/product-card";
import {getAllProducts} from "@/src/api/service/catalogService";

const New = async () => {

    const products = await getAllProducts(0, 8, 'desc', 'createdAt', '', '');

    return (
        <div className={styles.new}>
            <h2 className={styles.title}>Новинки</h2>
            <div className={styles.cardsContainer}>
                {
                    products.map((product) =>
                        <ProductCard price={product.price} oldPrice={product.priceDiscount} name={product.name}
                                     img={product.mainPhotoUrl} urlName={product.urlName}
                                     key={product.id}/>
                    )
                }
            </div>
        </div>
    )
}

export default New;
