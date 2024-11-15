import Tile from "@/src/components/tile";
import Slider from "@/src/components/slider";

export default function Home() {
    return (
        <Slider sliderWidth={1156} sliderHeight={229} countShow={3}>
            <Tile name='Монобукеты' img='/assets/placeholder/tile1.jpg'/>
            <Tile name='Для мужчин' img='/assets/placeholder/tile2.jpg'/>
            <Tile name='Для женщин' img='/assets/placeholder/tile3.jpg'/>
            <Tile name='Сезонные' img='/assets/placeholder/tile4.jpg'/>
            <Tile name='Классические' img='/assets/placeholder/tile5.jpg'/>
        </Slider>
        
    )
}
