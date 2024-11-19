import ProductDetails from "./product-details";
import Breadcrumbs from "./ui/breadcrumbs";

const ProductMain = () => {
    const breadcrumbs = [
        { label: "Главная", href: "/" },
        { label: "Каталог", href: "/" },
        { label: "Монобукеты", href: "/" },
        { label: "Букет роз", href: "#"},
      ];

    return (
        <div>
          <Breadcrumbs breadcrumbs={breadcrumbs}/>
          <ProductDetails name="Букет роз" 
            description="Погрузитесь в мир красоты с нашим изыскающим букетом роз! Каждая роза в этом букете выбрана вручную, чтобы создать неповторимый акцент для вашего особенного момента. Яркие и насыщенные оттенки в сочетании с свежестью цветов подарят вам и вашим близким настоящее наслаждение. Идеален для признаний в любви, юбилеев и романтических сюрпризов. Сделайте свой день особенным с нашим букетом роз!"
            composition="Розы белые - 7 шт, розы розовые - 7 шт."
            price={1000}
            img="/assets/placeholder/bouqette.jpg" />
        </div>
    );
}

export default ProductMain;