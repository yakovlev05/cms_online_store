import type {Metadata} from "next";
import {Open_Sans, Cormorant_SC} from 'next/font/google';
import "./globals.css";
import React from "react";

const openSans = Open_Sans({
    weight: ['400'],
    variable: '--font-open-sans',
    subsets: ['latin',  'cyrillic'],
    display: 'swap'
})

const cormorantSc = Cormorant_SC({
    weight: ['400'],
    variable: '--font-cormorant-sc',
    subsets: ['latin',  'cyrillic'],
    display: 'swap'
})

export const metadata: Metadata = {
    title: "Create Next App",
    description: "Generated by create next app",
};

export default function RootLayout({children,}: Readonly<{ children: React.ReactNode; }>) {
    return (
        <html lang="ru">
        <body className={`${openSans.variable} ${cormorantSc.variable}`}>
        {children}
        </body>
        </html>
    );
}