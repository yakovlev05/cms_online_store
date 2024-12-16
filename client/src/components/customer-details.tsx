export default function CustomerDetails() {
    return (
        <div>
            <h2 className="text-2xl font-semibold mb-4">Данные заказчика</h2>
            <div className="space-y-4">
                <div>
                    <label>ФИО заказчика:</label>
                    <input type="text" className="border p-2 w-full" />
                </div>
                <div>
                    <label>Номер телефона заказчика:</label>
                    <input type="tel" className="border p-2 w-full" />
                </div>
                <div>
                    <label>Комментарий к доставке:</label>
                    <textarea className="border p-2 w-full" rows={3}></textarea>
                </div>
            </div>
        </div>
    );
}
