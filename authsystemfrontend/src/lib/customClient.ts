import {client} from "#/generated/client.gen.ts";
import {useAuth} from "#/lib/store.ts";

client.instance.interceptors.request.use((request) => {
    const token = useAuth.getState().accessToken;

    if (!token) {
        console.error("Token not Provided")

    } else {
        console.log(`Token  ${token}`)
        request.headers.set("Authorization", `Bearer ${token}`);
    }

    return request;


})