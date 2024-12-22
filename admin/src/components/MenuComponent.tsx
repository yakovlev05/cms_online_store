import {useState} from 'react';
import {MenuFoldOutlined, MenuUnfoldOutlined, ShoppingCartOutlined} from '@ant-design/icons';
import {Button, Menu} from 'antd';
import * as React from "react";
import {SelectInfo} from "rc-menu/lib/interface";
import {useNavigate} from "react-router";


const MenuComponent: React.FC = () => {
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleSelect = (info: SelectInfo) => {
        const url = '/' + info.keyPath.reverse().join('/')
        navigate(url)
    }

    return (
        <div style={{width: '256px', minHeight: '100vh', display: 'flex', flexDirection: 'column'}}>
            <Button
                type="primary"
                onClick={() => setCollapsed(!collapsed)}
                style={{width: '100%'}}>
                {collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                {collapsed ? "Развернуть" : "Свернуть"}
            </Button>
            <Menu
                style={{flex: '1'}}
                defaultSelectedKeys={window.location.pathname.split('/')}
                defaultOpenKeys={window.location.pathname.split('/')}
                mode="inline"
                inlineCollapsed={collapsed}
                items={[
                    {
                        key: 'catalog', label: 'Каталог', icon: <ShoppingCartOutlined/>,
                        children: [
                            {key: 'category', label: 'Категории'},
                            {key: 'collection', label: 'Коллекции'},
                            {key: 'component', label: 'Компоненты'},
                            {key: 'product', label: 'Товары'}
                        ]
                    },
                ]}
                onSelect={handleSelect}
            />
        </div>
    )
}

export default MenuComponent;