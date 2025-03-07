'use client'
import styles from '@/src/styles/collections.module.css';
import Slider from "@/src/components/slider";
import Tile from "@/src/components/tile";
import {getAllCollections} from "@/src/api/service/catalogService";
import {toast, Toaster} from "react-hot-toast";
import {useEffect, useState} from "react";
import {CollectionResponseDto} from "@/src/api/models/response/catalog";

const Collections = () => {
    const [collections, setCollections] = useState<CollectionResponseDto[]>([])
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAllCollections()
            .then((r) => {
                setCollections(r)
                setLoading(false)
            })
            .catch((err) => toast.error(err.message))
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
                    !loading && collections.map(collection =>
                        <Tile
                            name={collection.category.name}
                            img={collection.photo.url}
                            urlName={collection.category.urlName}
                            key={collection.id}
                        />
                    )
                }
                
            </Slider>
            {
                loading && <p>Загрузка...</p>
            }
        </div>
    )
}

export default Collections;
