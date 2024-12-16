import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import ProductList from "@/src/components/product-list";
import styles from "@/src/styles/catalog.module.css";
import { useRouter } from "next/router";

export default function CategoryPage() {
  const router = useRouter();
  const { category } = router.query;

  return (
    <div className={styles.catalogPage}>
      <Header logo="/assets/placeholder/logo.svg" />
      <ProductList category={category as string} />
      <Footer
        logo="/assets/placeholder/logo.svg"
        vk_link="/#"
        tg_link="/#"
      />
    </div>
  );
}
