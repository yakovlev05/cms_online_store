import React, {useState} from "react";
import {BranchesOutlined, EditOutlined} from '@ant-design/icons';
import {CategoryResponseDto} from "../api/models/response/catalog.ts";
import {Button, Card, Input, Modal} from "antd";
import {updateCategory} from "../api/service/catalogService.ts";
import {errorAlert, successAlert} from "../util/notification.ts";
import {CategoryRequestDto} from "../api/models/request/catalog.ts";

const CategoryCardComponent: React.FC<{
    item: CategoryResponseDto,
    onDelete: (item: CategoryResponseDto) => void,
    onUpdate: (item: CategoryResponseDto) => void,
}> = ({item, onDelete, onUpdate}) => {
    const [isEditMode, setIsEditMode] = useState(false);
    const [itemNew, setItemNew] = useState<CategoryRequestDto>(item);


    const handleUpdate = async () => {
        updateCategory(item.urlName, itemNew)
            .then(r => {
                setIsEditMode(false);
                successAlert('Категория обновлена')
                onUpdate(r);
            })
            .catch((err) => {
                errorAlert(err.message)
            })
    }

    return (
        <Card style={{maxWidth: '500px', width: '100%'}}>
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <div>
                    <p style={{paddingLeft: '15px'}}>Название: {item.name}</p>
                    <Button type='link' icon={<BranchesOutlined/>}>
                        Связанные продукты
                    </Button>
                </div>
                <div style={{display: 'flex', flexDirection: 'column', gap: '10px'}}>
                    <Button type='dashed' icon={<EditOutlined/>} onClick={() => setIsEditMode(true)}>
                        Редактировать
                    </Button>
                    <Button type="link" danger icon={<EditOutlined/>} onClick={() => onDelete(item)}>Удалить</Button>

                    <Modal
                        title='Редактирование категории'
                        open={isEditMode}
                        onCancel={() => setIsEditMode(false)}
                        okText='Сохранить'
                        cancelText='Отмена'
                        onOk={handleUpdate}
                    >
                        <div>
                            <Input
                                placeholder='Название'
                                value={itemNew.name}
                                onChange={e => setItemNew({...itemNew, name: e.target.value})}
                            ></Input>
                        </div>
                    </Modal>
                </div>
            </div>
        </Card>
    )
}

export default CategoryCardComponent