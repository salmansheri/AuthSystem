import { defineConfig } from "@hey-api/openapi-ts";


export default defineConfig({
    input: "./openApi.json",
    output: "src/generated",
    plugins: [{
       name: "@hey-api/client-axios",
        runtimeConfigPath: "./src/lib/apiClient.ts"
    },   '@hey-api/typescript',   '@tanstack/react-query' ]
})