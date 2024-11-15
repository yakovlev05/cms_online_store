import Image from "next/image";
import React from "react";

interface Props {
    flipVertical?: boolean;
}

const Arrow: React.FC<Props> = ({flipVertical}) => {
    return (
        <div
            style={{
                transform: `scale(${flipVertical ? -1 : 1})`
            }}
        >
            <Image src='/assets/icon/arrow.svg' alt='стрелка' width='16' height='27'/>
        </div>
    )
}

export default Arrow;