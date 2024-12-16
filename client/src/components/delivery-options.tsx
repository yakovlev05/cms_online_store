'use client';

import { useState } from 'react';

export default function DeliveryOptions() {
    const [deliveryType, setDeliveryType] = useState<'pickup' | 'courier'>(
        'pickup'
    );

    return (
        <div>
            <h2 className="text-2xl font-semibold mb-4">Способ доставки</h2>
            <div className="flex space-x-4 mb-4">
                <button
                    onClick={() => setDeliveryType('pickup')}
                    className={`px-4 py-2 ${deliveryType === 'pickup' ? 'bg-green-500 text-white' : 'bg-gray-200'
                        }`}
                >
                    Самовывоз
                </button>
                <button
                    onClick={() => setDeliveryType('courier')}
                    className={`px-4 py-2 ${deliveryType === 'courier'
                            ? 'bg-green-500 text-white'
                            : 'bg-gray-200'
                        }`}
                >
                    Курьером
                </button>
            </div>
            <div>
                <label>
                    Дата получения:
                    <input type="date" className="border p-2 ml-2" />
                </label>
                <label>
                    Время получения:
                    <input type="time" className="border p-2 ml-2" />
                </label>
            </div>
        </div>
    );
}
