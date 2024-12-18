import styles from "@/src/styles/pagination.module.css"
import React from "react";

interface Props {
  onClick?: () => void;
}

const Pagination: React.FC<Props> = ({ onClick }) => {
  return (
    <div className={styles.pagination}>
      <button className={styles.paginationButton}>&lt;</button>
      <button className={styles.paginationButton}>1</button>
      <button className={styles.paginationButton}>2</button>
      <button className={styles.paginationButton}>3</button>
      <button className={styles.paginationButton}>4</button>
      <button className={styles.paginationButton}>&gt;</button>
    </div>
  );
}

export default Pagination;