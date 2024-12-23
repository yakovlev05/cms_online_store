import {Card, Checkbox} from "antd";
import {ComponentResponseDto} from "../api/models/response/catalog.ts";
import * as React from "react";

const ComponentCardComponent: React.FC<{ item: ComponentResponseDto }> = ({item}) => {
    return (
        <Card style={{maxWidth: '500px', width: '100%'}}>
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <div>
                    <p>Название: {item.name}</p>
                    <p>Цена: {item.price} ₽</p>
                </div>
                <div>
                    <Checkbox checked={item.isInStock}
                    >В наличии</Checkbox>
                </div>
            </div>
        </Card>
    )
}

export default ComponentCardComponent