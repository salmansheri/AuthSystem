import type {CreateClientConfig} from "#/generated/client.gen.ts";


export const createClientConfig: CreateClientConfig = (request) => ({
    ...request,
    withCredentials: true,

})




