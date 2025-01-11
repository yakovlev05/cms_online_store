import {Spin} from "antd";
import {LoadingOutlined} from "@ant-design/icons";

const MiniLoader= ({fontSize = 50, color = 'white'}) =>{
    return(
        <Spin indicator={<LoadingOutlined style={{ fontSize: fontSize, color: color}} spin />} />
    )
}

export default MiniLoader;