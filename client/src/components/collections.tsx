'use client'
import styles from '@/src/styles/collections.module.css';
import Slider from "@/src/components/slider";
import Tile from "@/src/components/tile";
import {useEffect, useState} from "react";
import {CollectionResponseDto} from "@/src/api/models/response/catalog";
import {getAllCollections} from "@/src/api/service/catalogService";
import {toast, Toaster} from "react-hot-toast";

const Collections = () => {
    const [collections, setCollections] = useState<CollectionResponseDto[]>([]);

    useEffect(() => {
        getAllCollections()
            .then(r => setCollections(r))
            .catch(e => toast.error(e));
    }, []);

    return (
        <div className={styles.collections}>
            <Toaster/>
            <h2 className={styles.title}>Коллекции</h2>
            <Slider sliderWidth={1156}
                    sliderHeight={229}
                    countShow={5}
                    gap={24}
            >
                {
                    collections.map(collection =>
                        <Tile 
                            name={collection.category.name}
                            img={collection.photo.url} 
                            urlName={collection.category.urlName}
                            key={collection.id}
                        />
                    )
                }
            </Slider>
        </div>
    )
}

export default Collections;