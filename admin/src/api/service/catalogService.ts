import {CategoryResponseDto, ComponentResponseDto} from "../models/response/catalog.ts";
import {CategoryRequestDto, ComponentRequestDto} from "../models/request/catalog.ts";

export async function getListComponents(
    page: number = 0,
    limit: number = 10,
    isDescending: boolean = true,
    keySort: string = "createdAt",
    searchQuery: string | null = null,
    showOnlyInStock: boolean = false
): Promise<ComponentResponseDto[]> {

    let params = `page=${page}&limit=${limit}&isDescending=${isDescending}&keySort=${keySort}&showOnlyInStock=${showOnlyInStock}`;

    if (searchQuery !== null) {
        params += `&searchQuery=${searchQuery}`;
    }

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/components?` + params, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при GET запросе списка компонентов: ' + response.status);
    }
}


export async function deleteComponent(componentName: string) {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/components/${componentName}`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        }
    })

    if (response.status === 401) {
        throw new Error('Unauthorized');
    } else if (!response.ok) {
        throw new Error('Ошибка при DELETE запросе удаления компонента: ' + response.status)
    }
}

export async function updateComponent(componentName: string, data: ComponentRequestDto): Promise<ComponentResponseDto> {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/components/${componentName}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при PUT запросе изменения компонента: ' + response.status);
    }
}

export async function createComponent(data: ComponentRequestDto): Promise<ComponentResponseDto> {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/components/add`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при POST запросе создания компонента: ' + response.status);
    }
}

export async function getListCategories(
    page: number,
    limit: number,
    directionSort: string,
    keySort: string,
    searchQuery: string,
): Promise<CategoryResponseDto[]> {
    const params = `page=${page}&limit=${limit}&directionSort=${directionSort}&keySort=${keySort}&searchQuery=${searchQuery}`

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/category?` + params, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при GET запросе списка категорий: ' + response.status);
    }
}

export async function deleteCategory(urlName: string) {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/category/${urlName}`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        }
    });

    if (response.status === 401) {
        throw new Error('Unauthorized');
    } else if (!response.ok) {
        throw new Error('Ошибка при DELETE запросе удаления категории: ' + response.status)
    }
}

export async function updateCategory(urlName: string, data: CategoryRequestDto): Promise<CategoryResponseDto> {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/category/${urlName}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при PUT запросе изменения компонента: ' + response.status);
    }
}

export async function createCategory(data: CategoryRequestDto): Promise<CategoryResponseDto> {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/category/add`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
        body: JSON.stringify(data)
    })


    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при POST запросе создания категории: ' + response.status);
    }
}
