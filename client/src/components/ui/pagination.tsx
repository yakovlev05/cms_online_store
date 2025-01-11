import styles from "@/src/styles/pagination.module.css"
import React from "react";

interface Props {
    onClick?: (num: number) => void;
    page?: number;
}

const Pagination: React.FC<Props> = ({onClick, page}) => {
    return (
        <div className={styles.pagination}>
            <button className={styles.paginationButton}>&lt;</button>
            {
                [0, 1, 2, 3].map((num, i) =>
                    <button className={
                        page === num
                            ? `${styles.paginationButton} ${styles.activeButton}`
                            : styles.paginationButton
                    }
                            onClick={() => onClick && onClick(num)}
                            key={i}
                    >
                        {num + 1}
                    </button>
                )
            }
            <button className={styles.paginationButton}>&gt;</button>
        </div>
    );
}

export default Pagination;