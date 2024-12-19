import Link from "next/link";
import styles from "@/src/styles/breadcrumbs.module.css"

interface BreadcrumbsProps {
  breadcrumbs: {
    label: string;
    href: string;
  }[];
}

const Breadcrumbs: React.FC<BreadcrumbsProps> = ({ breadcrumbs }) => {
  const lastCrumb = breadcrumbs[breadcrumbs.length - 1];

  return (
    <div>
      <nav className={styles.breadcrumbs}>
        {breadcrumbs.map((crumb, index) => (
          <span key={index}>
            <Link href={crumb.href}>{crumb.label}</Link>
            {index < breadcrumbs.length - 1 && " › "}
          </span>
        ))}
      </nav>
      <h1 className={styles.title}>{lastCrumb.label}</h1>
    </div>
  );
};

export default Breadcrumbs;