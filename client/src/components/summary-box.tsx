export default function SummaryBox() {
    return (
        <div className="border p-4 rounded-lg">
            <h2 className="text-xl font-semibold mb-4">Итого</h2>
            <div className="flex justify-between">
                <span>Товары:</span>
                <span>4000₽</span>
            </div>
            <div className="flex justify-between">
                <span>Скидка:</span>
                <span>-2000₽</span>
            </div>
            <div className="flex justify-between">
                <span>Доставка:</span>
                <span>бесплатно</span>
            </div>
            <hr className="my-2" />
            <div className="flex justify-between font-bold">
                <span>Итого:</span>
                <span>2000₽</span>
            </div>
            <button className="mt-4 bg-blue-500 text-white w-full py-2 rounded">
                Заказать
            </button>
        </div>
    );
}
