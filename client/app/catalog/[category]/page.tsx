// app/catalog/[category]/page.tsx
"use client";

import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import ProductList from "@/src/components/product-list";
import Pagination from "@/src/components/ui/pagination";
import styles from "@/src/styles/catalog.module.css";
import styles_1 from "@/src/styles/home.module.css"
import Breadcrumbs from "@/src/components/ui/breadcrumbs";
import { useParams } from "next/navigation";

export default function CategoryPage() {
    const params = useParams();
    const category = params.category as string; // Получаем динамический параметр

    // Динамическое определение заголовка
    const categoryTitles: { [key: string]: string } = {
        novelties: "Новинки",
        seasonal: "Сезонные",
        monobouquets: "Монобукеты",
        formen: "Для мужчин",
        forwomen: "Для женщин",
        classic: "Классические",
    };

    const title = categoryTitles[category] || "Категория";

    const breadcrumbs = [
        { label: "Главная", href: "/" },
        { label: title, href: "#" },
    ];

    return (
        <div className={styles_1.home}>
            <Header logo="/assets/placeholder/logo.svg" />
            <div className={styles.breadcrumbs}>
                <Breadcrumbs breadcrumbs={breadcrumbs} />
            </div>
            <ProductList category={category}/>
            <Pagination />
            <Footer
                logo="/assets/placeholder/logo.svg"
                vk_link="/#"
                tg_link="/#"
            />
        </div>
    );
}
