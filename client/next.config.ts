import type {NextConfig} from "next";

const nextConfig: NextConfig = {
    /* config options here */
    // async rewrites() {
    //     return [
    //         {
    //             source: "/api/:path*",
    //             destination: "http://localhost:80/api/:path*", // Настройка прокси
    //         },
    //     ];
    // },

    images: {
        remotePatterns: [
            {
                hostname: process.env.IMAGES_HOSTNAME || "" // нужно указывать хосты, с которых можно загружать изображение
            }
        ]
    }
};

export default nextConfig;
