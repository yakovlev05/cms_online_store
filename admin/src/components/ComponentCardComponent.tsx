import {Button, Card, Checkbox, Input, InputNumber, Modal} from "antd";
import {EditOutlined} from '@ant-design/icons';
import {ComponentResponseDto} from "../api/models/response/catalog.ts";
import * as React from "react";
import {useState} from "react";
import {updateComponent} from "../api/service/catalogService.ts";
import {errorAlert, successAlert} from "../util/notification.ts";

const ComponentCardComponent: React.FC<{
    item: ComponentResponseDto,
    onDelete: (item: ComponentResponseDto) => void,
    onUpdate: (item: ComponentResponseDto) => void,
}> = ({item, onDelete, onUpdate}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [itemNew, setItemNew] = useState<ComponentResponseDto>(item);

    const handleUpdate = async () => {
        updateComponent(item.name, itemNew)
            .then((r) => {
                setIsModalOpen(false);
                successAlert("Компонент обновлён")
                onUpdate(r)
            })
            .catch((e) => errorAlert(e.message))
    }

    return (
        <Card style={{maxWidth: '500px', width: '100%'}}>
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <div>
                    <p>Название: {item.name}</p>
                    <p>Цена: {item.price} ₽</p>
                    <Checkbox checked={item.inStock}>В наличии</Checkbox>
                </div>
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '10px'
                }}>
                    <Button type="dashed" icon={<EditOutlined/>}
                            onClick={() => setIsModalOpen(true)}>Редактировать</Button>
                    <Button type="link" danger icon={<EditOutlined/>} onClick={() => onDelete(item)}>Удалить</Button>

                    <Modal
                        title='Редактирование компонента'
                        open={isModalOpen}
                        onCancel={() => setIsModalOpen(false)}
                        okText='Сохранить'
                        cancelText='Отмена'
                        onOk={handleUpdate}
                        // width={700}
                    >
                        <div style={{display: 'flex', flexDirection: 'column', gap: '10px'}}>
                            <div>
                                <p style={{margin: '0px'}}>Название</p>
                                <Input placeholder='Название'
                                       value={itemNew.name}
                                       onChange={(e) => setItemNew({...itemNew, name: e.target.value})}/>
                            </div>

                            <div style={{display: 'flex', gap: '20px', alignItems: 'end'}}>
                                <div>
                                    <p style={{margin: '0px'}}>Цена ₽</p>
                                    <InputNumber min={1} defaultValue={10} value={itemNew.price}
                                                 onInput={(e) => setItemNew({...itemNew, price: Number(e)})}/>
                                </div>

                                <Checkbox.Group
                                    options={[{label: 'В наличии', value: 'inStock'}]}
                                    value={[itemNew.inStock ? 'inStock' : null]}
                                    onChange={(e) => setItemNew({...itemNew, inStock: e.includes('inStock')})}
                                />
                            </div>
                        </div>
                    </Modal>
                </div>
            </div>
        </Card>
    )
}

export default ComponentCardComponent