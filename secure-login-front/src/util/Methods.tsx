
export function createCookie(name: string, value: string, days: number) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + days);
    const cookieValue = `${name}=${value};expires=${expirationDate.toUTCString()};path=/;SameSite=Strict`;
    document.cookie = cookieValue;
}

export function getCookie(name: string){ 
    const cookies = document.cookie.split('; ');
    for (let cookie of cookies) {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName === name) {
        return cookieValue;
        }
    }
    return null;
}

export function deleteCookie(name: string) {
    document.cookie = `${name}=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;SameSite=Strict`;
}