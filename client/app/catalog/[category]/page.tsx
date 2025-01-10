// app/catalog/[category]/page.tsx
"use client";

import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import ProductList from "@/src/components/product-list";
import Pagination from "@/src/components/ui/pagination";
import styles from "@/src/styles/catalog.module.css";
import styles_1 from "@/src/styles/home.module.css"
import Breadcrumbs from "@/src/components/ui/breadcrumbs";
import {useParams} from "next/navigation";
import {CategoryResponseDto} from "@/src/api/models/response/catalog";
import {useEffect, useState} from "react";
import {getCategoryInfo} from "@/src/api/service/catalogService";
import {toast, Toaster} from "react-hot-toast";

export default function CategoryPage() {
    const params = useParams();
    const categoryUrlName = params.category as string; // Получаем динамический параметр
    const [category, setCategory] = useState<CategoryResponseDto>({
        name: 'загрузка...',
        urlName: '',
        id: 0
    });

    useEffect(() => {
        getCategoryInfo(categoryUrlName)
            .then(r => setCategory(r))
            .catch(err => toast.error(err.message));
    }, [categoryUrlName])

    const breadcrumbs = [
        {label: "Главная", href: "/"},
        {label: category.name, href: "#"},
    ];

    return (
        <div className={styles_1.home}>
            <Toaster/>
            <Header logo="/assets/placeholder/logo.svg"/>
            <div className={styles.breadcrumbs}>
                <Breadcrumbs breadcrumbs={breadcrumbs}/>
            </div>
            <ProductList category={category.urlName}/>
            <Pagination/>
            <Footer
                logo="/assets/placeholder/logo.svg"
                vk_link="/#"
                tg_link="/#"
            />
        </div>
    );
}
