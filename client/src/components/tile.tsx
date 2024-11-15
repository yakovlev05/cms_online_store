import styles from '@/src/styles/tile.module.css'
import Image from "next/image";
import React from "react";

interface Props {
    name: string,
    img: string,
}

const Tile: React.FC<Props> = ({name, img}) => {
    return (
        <div className={styles.tile}>
            <p className={styles.name}>{name}</p>
            <Image src={img} alt='букет' width='180' height='180' className={styles.img}/>
        </div>
    )
}

export default Tile;